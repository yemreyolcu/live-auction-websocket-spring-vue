package com.kartaca.kartacaoffer.services;

import com.kartaca.kartacaoffer.entities.Token;
import com.kartaca.kartacaoffer.repositories.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token tokenCreate(Token token, Long expiredTime) {
        return tokenRepository.tokenCreate(token, expiredTime);
    }

    public Token tokenReadByUsername(String username) {
        return this.tokenRepository.tokenReadByUsername(username);
    }

    public void tokenDeleteByUsername(String username) {
        tokenRepository.tokenDeleteByUsername(username);
    }
}
