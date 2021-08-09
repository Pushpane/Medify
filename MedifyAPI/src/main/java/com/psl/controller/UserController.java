package com.psl.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.AuthenticationResponse;
import com.psl.dto.LoginRequest;
import com.psl.dto.PasswordChangerRequest;
import com.psl.dto.RefreshTokenRequest;
import com.psl.dto.RegisterUserRequest;

import com.psl.service.PasswordChangerService;
import com.psl.service.RefreshTokenService;
import com.psl.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
	
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	private final PasswordChangerService changerService;
	
	@PostMapping("/signup")
	public ResponseEntity<HttpStatus> signup(@RequestBody RegisterUserRequest registerUserRequest) {
		userService.registerUser(registerUserRequest);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
    }
	
	@PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
	
	@PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }
	
	@PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<HttpStatus> forgotPassword(@RequestBody PasswordChangerRequest request) {
		changerService.addVerificationToken(request.getEmail());
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PostMapping("/updatePassword")
	public void passwordChanger(@RequestBody PasswordChangerRequest request) {
		changerService.changePassword(request);
	}
}
