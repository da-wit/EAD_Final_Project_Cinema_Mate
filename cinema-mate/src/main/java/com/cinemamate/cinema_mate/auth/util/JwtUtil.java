package com.cinemamate.cinema_mate.auth.util;

import com.cinemamate.cinema_mate.core.constant.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Date extractExpirationDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
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

    // token generator
    public String generateToken(UserDetails  userDetails){
        String role = userDetails.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet()).iterator().next();

        return Jwts.builder()
                .claim("role",role)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // get sign key
    private Key getSignInKey() {
        byte [] keyByte = Decoders.BASE64URL.decode(secretKey);

        return Keys.hmacShaKeyFor(keyByte);
    }
}
