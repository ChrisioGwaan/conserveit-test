package com.conserveit.controlbg.service;

import com.conserveit.controlbg.utils.AuthenticationRequest;
import com.conserveit.controlbg.utils.AuthenticationResponse;
import com.conserveit.controlbg.utils.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
