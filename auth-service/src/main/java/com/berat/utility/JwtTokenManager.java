package com.berat.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.berat.exception.AuthManagerException;
import com.berat.exception.EErrorType;
import com.berat.model.enums.ERole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    @Value("${jwt.secretkey}")
    String secretKey;
    @Value("${jwt.issuer}")
    String issuer;
    @Value("${jwt.audience}")
    String audience;

    public Optional<String> createToken(Long id){
        String token=null;
        try {
            token= JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*5)))
                    .withClaim("id",id)
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }
    }
    public Optional<String> createToken(Long id, ERole role){
        String token=null;
        try {
            token= JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*5)))
                    .withClaim("id",id)
                    .withClaim("role",role.toString())
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch (Exception exception){
            return Optional.empty();
        }
    }
    public Boolean validateToken(String token){

        try {
            Algorithm algorithm=Algorithm.HMAC512(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT= verifier.verify(token);

            if (decodedJWT==null)
                return false;

        }catch (Exception exception){
                throw new AuthManagerException(EErrorType.INVALID_TOKEN);
        }
        return true;
    }
    public Optional<Long> getIdFromToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC512(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT= verifier.verify(token);

            if (decodedJWT==null)
                throw new AuthManagerException(EErrorType.INVALID_TOKEN);
            Long id=decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception exception){
            throw new AuthManagerException(EErrorType.INVALID_TOKEN);
        }
    }
    public Optional<String> getRoleFromToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC512(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT= verifier.verify(token);

            if (decodedJWT==null)
                throw new AuthManagerException(EErrorType.INVALID_TOKEN);
            String role=decodedJWT.getClaim("role").asString();
            return Optional.of(role);
        }catch (Exception exception){
            throw new AuthManagerException(EErrorType.INVALID_TOKEN);
        }
    }
}
