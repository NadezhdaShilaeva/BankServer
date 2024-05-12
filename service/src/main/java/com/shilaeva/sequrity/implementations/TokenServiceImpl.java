package com.shilaeva.sequrity.implementations;

import com.shilaeva.exceptions.AuthException;
import com.shilaeva.sequrity.TokenService;
import com.shilaeva.utils.PropertiesLoader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class TokenServiceImpl implements TokenService {
    private final String secretKey;
    private final long tokenValidityMs = 3600000;

    public TokenServiceImpl() {
        this.secretKey = PropertiesLoader.getInstance().getProperty("jwt.secret");
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String login) {
        if (login == null)
            return null;

        return Jwts.builder()
                .subject(login)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenValidityMs))
                .signWith(getSignInKey())
                .compact();
    }

    public String getUserFromToken(String token) {
        Claims claims = extractAllClaims(token);
        validateTokenClaims(claims);

        return claims.getSubject();
    }

    private Claims extractAllClaims(String token){
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }  catch (Exception e) {
            throw AuthException.invalidToken();
        }
    }

    private void validateTokenClaims(Claims claims) {
        if (claims.getExpiration().before(new Date())) {
            throw AuthException.tokenIsExpired();
        }
    }
/*
    private TokenAuthentication processAuthentication(String token) {
        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.get("TOKEN_EXPIRATION_DATE", Long.class) == null)
            throw new AuthenticationServiceException("Invalid token");
        Date expiredDate = new Date(claims.get("TOKEN_EXPIRATION_DATE", Long.class));
        if (expiredDate.after(new Date()))
            return buildFullTokenAuthentication(authentication, claims);
        else
            throw new AuthenticationServiceException("Token expired date error");
    }

    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, DefaultClaims claims) {
        User user = (User) userDetailsService.loadUserByUsername(claims.get("USERNAME", String.class));
        if (user.isEnabled()) {
            Collection<GrantedAutority> authorities = user.getAuthorities();
            TokenAuthentication fullTokenAuthentication =
                    new TokenAuthentication(authentication.getToken(), authorities, true, user);
            return fullTokenAuthentication;
        } else {
            throw new AuthenticationServiceException("User disabled");;
        }
    }*/
}
