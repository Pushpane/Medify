package com.psl.service;

import java.time.Instant;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IRefreshTokenDAO;
import com.psl.entity.RefreshToken;
import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenService {

	private final IRefreshTokenDAO refreshTokenDAO;

    RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenDAO.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenDAO.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Invalid refresh token" );
                    return new MedifyException("Invalid refresh Token");
                });
    }

    public void deleteRefreshToken(String token) {
        refreshTokenDAO.deleteByToken(token);
        log.info("Token deleted successfully" );
    }
}
