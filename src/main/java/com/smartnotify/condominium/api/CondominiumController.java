package com.smartnotify.condominium.api;

import com.smartnotify.condominium.api.json.CondominiumLoginRequest;
import com.smartnotify.condominium.api.json.CondominiumLoginResponse;
import com.smartnotify.condominium.api.json.CondominiumSignUpRequest;
import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.condominium.service.CondominiumService;
import com.smartnotify.config.security.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/condominium")
public class CondominiumController {

    private final CondominiumService condominiumService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody @Valid final CondominiumSignUpRequest request) {
        final Condominium condominium = CondominiumSignUpRequest.toCondominium(request);
        condominiumService.signUpCondominium(condominium);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<CondominiumLoginResponse> login(@RequestBody @Valid final CondominiumLoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(
                CondominiumLoginResponse.toCondominiumLoginResponse(
                        jwtService.generateJwtToken(authentication)
                )
        );
    }

}
