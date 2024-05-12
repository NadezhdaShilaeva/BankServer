package com.shilaeva.sequrity;

public interface TokenService {
    String generateToken(String login);
    String getUserFromToken(String token);
}
