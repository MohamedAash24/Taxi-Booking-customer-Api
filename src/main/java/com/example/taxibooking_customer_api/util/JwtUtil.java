package com.example.taxibooking_customer_api.util;

import com.example.taxibooking_customer_api.entity.Admin;
import com.example.taxibooking_customer_api.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@Component
public class JwtUtil {

    private static String SECRET = "kzxm0Ng3+u943X8ntHQu4+vPXJbCEek9JSuQRnIxn6fGigSFsWVrXsF5bvVXDD70zlnhqBlH5WeWef+7tJ75qNCN3dMtOnCXP/+ThhT0Orw=";
    private static long expiryDuration = 60 * 30 ;

    public String generateAccessToken(Customer customer){
        long currentTimeMillis = System.currentTimeMillis();

        long expireTime = currentTimeMillis + expiryDuration * 1000;
        Date issuedAt = new Date(currentTimeMillis);
        Date expiredAt = new Date(expireTime);

        Claims claims = Jwts.claims()
                .setIssuer(String.valueOf(customer.getCustomerId()))
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt);
        claims.put("username",customer.getUsername());
        claims.put("mobile",customer.getMobile1());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,SECRET).compact();
    }

    public Claims verifyAccessToken(String authorization) throws Exception{
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authorization).getBody();
        }catch (Exception e){
            throw new AccessDeniedException("Access Denied! Please login again");
        }
    }
}
