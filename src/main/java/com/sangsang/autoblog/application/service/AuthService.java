package com.sangsang.autoblog.application.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.sangsang.autoblog.application.command.SignupCommand;
import com.sangsang.autoblog.domain.exception.DuplicateException;
import com.sangsang.autoblog.domain.model.CustomUserDetails;
import com.sangsang.autoblog.domain.model.User;

import com.sangsang.autoblog.domain.port.in.AuthUseCase;
import com.sangsang.autoblog.domain.port.out.GithubOAuthPort;
import com.sangsang.autoblog.domain.port.out.UserHistoryRepositoryPort;
import com.sangsang.autoblog.domain.port.out.UserOAuthRepositoryPort;
import com.sangsang.autoblog.domain.port.out.UserOriginRepositoryPort;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService implements AuthUseCase, UserDetailsService{

    private final BCryptPasswordEncoder passwordEncoder;

    private final GithubOAuthPort githubOAuthPort;
    private final UserHistoryRepositoryPort userHistoryRepositoryPort;
    private final UserOriginRepositoryPort userOriginRepositoryPort;
    private final UserOAuthRepositoryPort userOAuthRepositoryPort;

    public AuthService(UserHistoryRepositoryPort userHistoryRepositoryPort,
                        UserOriginRepositoryPort userOriginRepositoryPort,
                        UserOAuthRepositoryPort userOAuthRepositoryPort,
                        GithubOAuthPort githubOAuthPort,
                        BCryptPasswordEncoder passwordEncoder
                        ) {
            this.userHistoryRepositoryPort = userHistoryRepositoryPort;
            this.userOriginRepositoryPort = userOriginRepositoryPort;
            this.userOAuthRepositoryPort = userOAuthRepositoryPort;
            this.githubOAuthPort = githubOAuthPort;
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

    @Override
    public void signinByGithub(String code, HttpServletRequest request) {
        String accessToken = githubOAuthPort.getGithubAccessToken(code);
        User user = githubOAuthPort.getGithubUserInfo(accessToken);
        Optional<Long> originUserId = userOAuthRepositoryPort.findByProviderAndProviderId(user.provider, user.providerId);
        if (originUserId.isPresent()) {
            authenticateSession(originUserId.get(), request);
        } else {
            if (userOriginRepositoryPort.existsByEmail(user.email)) {
                //TODO: block/origin 계정과 oauth 계정 통합 유도 - 비밀번호 요구 -> provider db 등록

            } else {
                Long savedUserId = userOriginRepositoryPort.save(user).id;
                userOAuthRepositoryPort.save(user, savedUserId);
                authenticateSession(savedUserId, request);
            }
        }
    }

    private void authenticateSession(Long userId, HttpServletRequest request) {

        User user = userOriginRepositoryPort.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CustomUserDetails userDetails = new CustomUserDetails(user);
            
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = request.getSession(true);
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            context
        );

    }
}
