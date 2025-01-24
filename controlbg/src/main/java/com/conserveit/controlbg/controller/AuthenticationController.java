package com.conserveit.controlbg.controller;

import com.conserveit.controlbg.service.AuthenticationService;
import com.conserveit.controlbg.utils.AuthenticationRequest;
import com.conserveit.controlbg.utils.AuthenticationResponse;
import com.conserveit.controlbg.utils.R;
import com.conserveit.controlbg.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public R<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return R.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public R<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return R.ok(authenticationService.authenticate(authenticationRequest));
    }

}