package com.kartaca.kartacaoffer.repositories;

import com.kartaca.kartacaoffer.entities.Token;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {
    final RedisTemplate redisTemplate;

    public TokenRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Token tokenCreate(Token token, Long expiredTime) {
        redisTemplate.opsForValue().set(token.getUsername(), token.getJwt(), expiredTime, TimeUnit.HOURS);
        return token;
    }

    public Token tokenReadByUsername(String username) {
        String jwt_token = redisTemplate.opsForValue().get(username).toString();
        return new Token(username, jwt_token);
    }

    public void tokenDeleteByUsername(String username) {
        redisTemplate.opsForValue().getAndDelete(username);
    }
}
