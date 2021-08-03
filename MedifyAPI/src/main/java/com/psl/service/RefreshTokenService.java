package com.psl.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IRefreshTokenDAO;
import com.psl.entity.RefreshToken;
import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
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
                .orElseThrow(() -> new MedifyException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenDAO.deleteByToken(token);
    }
}
