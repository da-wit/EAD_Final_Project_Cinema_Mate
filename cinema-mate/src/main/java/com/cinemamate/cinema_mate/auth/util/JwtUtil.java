package com.cinemamate.cinema_mate.auth.util;

import com.cinemamate.cinema_mate.auth.exceptions.AuthExceptions;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.core.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Role extractRole(String token){
        String roleAsSting =  extractClaim(token,claims -> claims.get("role",String.class));
        return Role.valueOf(roleAsSting);
    }
    public String extractId(String token){
        return extractClaim(token, claims -> claims.get("id",String.class));
    }

    public Date extractExpirationDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        try {
            System.out.println("inside token expired");
            return  extractExpirationDate(token).before(new Date());
        }catch (ExpiredJwtException e){
            throw AuthExceptions.expiredToken();
        }
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        String username = extractUsername(token);
        String id = extractId(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        try{
            if(!id.equals(userDetails.getId())){
                throw AuthExceptions.invalidToken();
            }
//            if(!username.equals(userDetails.getUsername())){
//                throw AuthExceptions.invalidToken();
//            }
            if(isTokenExpired(token)){
                throw AuthExceptions.invalidToken();
            }
            return  true;

        } catch (JwtException e) {
            throw AuthExceptions.invalidToken();
        }
    }


    public <T>T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return  claimResolver.apply(claims);
    }

    private Claims extractAllClaims (String jwt){

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw  AuthExceptions.expiredToken();
        } catch (JwtException ex) {
            throw AuthExceptions.invalidToken();
        }
    }
    // token generator
    public String generateToken(CustomUserDetails userDetails){
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getAuthorities());
        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).iterator().next();
        System.out.println(userDetails.getUsername());
        System.out.println(role);
        System.out.println("inside generate token");
        return Jwts.builder()
                .claim("id",userDetails.getId())
                .claim("role",role)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // get sign key
    private Key getSignInKey() {
        byte [] keyByte = Decoders.BASE64URL.decode(secretKey);

        return Keys.hmacShaKeyFor(keyByte);
    }
}
