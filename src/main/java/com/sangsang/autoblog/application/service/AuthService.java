package com.sangsang.autoblog.application.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sangsang.autoblog.application.command.SignupCommand;
import com.sangsang.autoblog.domain.exception.DuplicateException;
import com.sangsang.autoblog.domain.model.CustomUserDetails;
import com.sangsang.autoblog.domain.model.User;

import com.sangsang.autoblog.domain.port.in.AuthUseCase;
import com.sangsang.autoblog.domain.port.out.UserHistoryRepositoryPort;
import com.sangsang.autoblog.domain.port.out.UserOAuthRepositoryPort;
import com.sangsang.autoblog.domain.port.out.UserOriginRepositoryPort;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService implements AuthUseCase, UserDetailsService{

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserHistoryRepositoryPort userRepositoryPort;
    private final UserOriginRepositoryPort userOriginRepositoryPort;
    private final UserOAuthRepositoryPort userOAuthRepositoryPort;

    public AuthService(UserHistoryRepositoryPort userRepositoryPort,
                        UserOriginRepositoryPort userOriginRepositoryPort,
                        UserOAuthRepositoryPort userOAuthRepositoryPort,
                        BCryptPasswordEncoder passwordEncoder) {
            this.userRepositoryPort = userRepositoryPort;
            this.userOriginRepositoryPort = userOriginRepositoryPort;
            this.userOAuthRepositoryPort = userOAuthRepositoryPort;
            this.passwordEncoder = passwordEncoder;
    }

    /*
     * Override for UserDetailsService : Srping security
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userOriginRepositoryPort.findByUsername(username);
        optUser.orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomUserDetails(optUser.get());
    }

    @Override
    public User signup(SignupCommand cmd) {

        if(userOriginRepositoryPort.existsByUsername(cmd.username())){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userOriginRepositoryPort.existsByEmail(cmd.email())){
            throw new IllegalArgumentException("Email already exists");
        }
        
        try {
            User newUser = User.signupOriginFrom(cmd);
            newUser.changePassword(passwordEncoder.encode(newUser.password));
            return userOriginRepositoryPort.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException("Duplicate info found during signup");
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }
}
