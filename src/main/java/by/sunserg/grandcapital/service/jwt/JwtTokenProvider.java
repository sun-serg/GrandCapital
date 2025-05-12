package by.sunserg.grandcapital.service.jwt;

import by.sunserg.grandcapital.service.dto.response.UserAuthenticationDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expired}")
    private long expired;

    private final JwtUserDetailsService userDetailsService;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(UserAuthenticationDto user) {
        Claims claims = Jwts.claims().setSubject(user.userId());

        Instant now = Instant.now();
        Instant validity = now.plusMillis(expired);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(validity))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        JwtUser jwtUser = this.userDetailsService.loadUserById(getUserId(token));
        return new UsernamePasswordAuthenticationToken(jwtUser, "", jwtUser.getAuthorities());
    }

    public Long getUserId(String token) {
        return Long.valueOf(Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(6);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            Long userId = Long.valueOf(claims.getBody().getSubject());

            if (claims.getBody().getExpiration().before(new Date())) {
                log.warn("JWT токен пользователя c id {} истек", userId);
                return false;
            }

            log.info("JWT токен пользователя c id {} валиден", userId);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Истекший или невалидный JWT токен пользователя");
            return false;
        }
    }
}
