package by.touchme.authservice.controller;

import by.touchme.authservice.dto.BaseLoginDto;
import by.touchme.authservice.dto.RefreshDto;
import by.touchme.authservice.dto.JwtDto;
import by.touchme.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Auth controller for user.
 */
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody BaseLoginDto baseLoginDto) {
        return new ResponseEntity<>(authService.login(baseLoginDto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@Valid @RequestBody RefreshDto requestRefreshDto) {
        return new ResponseEntity<>(authService.refresh(requestRefreshDto), HttpStatus.OK);
    }
}
