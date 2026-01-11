package com.sangsang.autoblog.adapter.in.web.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sangsang.autoblog.adapter.in.web.dto.SigninReqeustDTO;
import com.sangsang.autoblog.adapter.in.web.dto.SignupReqeustDTO;
import com.sangsang.autoblog.domain.port.in.AuthUseCase;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @GetMapping("/signin")
    public String getSigninView() {
        return "pages/auth/signin";
    }

    // @PostMapping("/signin")
    // public String postSignin(SigninReqeustDTO signinReqeustDTO) {
    //     authUseCase.signin(signinReqeustDTO.toSigninDomain());
    //     return "redirect:/home";
    // }

    @GetMapping("/signup")
    public String getSignupView() {
        return "pages/auth/signup";
    }

    @PostMapping("/signup")
    public String postSignup(SignupReqeustDTO signupReqeustDTO) {
        authUseCase.signup(signupReqeustDTO.toSignUpCommand());

        return "redirect:signin";
    }
    
}
