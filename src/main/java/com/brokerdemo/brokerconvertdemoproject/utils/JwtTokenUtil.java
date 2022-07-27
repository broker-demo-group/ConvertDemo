package com.brokerdemo.brokerconvertdemoproject.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: bowen
 * @description:
 * @date: 2022/7/11  9:18 AM
 **/
public class JwtTokenUtil {
    private static final String SECRET_KEY = "brokerDemo";
    private static final String PAYLOAD_USERNAME = "username";


    public static String getToken(String username) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        return JWT.create()
                .withClaim(PAYLOAD_USERNAME, username)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static Boolean isTokenExpired(String token) {

        Date expiresAt = getVerify(token).getExpiresAt();

        return expiresAt.before(new Date());
    }

    public static String getUsername(String token) {
        return getVerify(token).getClaim(PAYLOAD_USERNAME).asString();
    }

    private static DecodedJWT getVerify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }
}
