package com.yaouguoji.platform.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/12/3
 */
public class ShopTokenUtil {

    private static final String SECRET = "xianbester";
    private static final String COOKIE_NAME = "shop_id";

    /**
     * 生成新token并设置cookie
     *
     * @param response
     * @param dataMap
     * @throws UnsupportedEncodingException
     */
    public static void updateToken2Cookie(HttpServletResponse response,
                                          Map<String, String> dataMap) throws UnsupportedEncodingException {
        Date expireTime = new DateTime().plusHours(1).toDate();
        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        String token = builder.withExpiresAt(expireTime).withIssuedAt(new Date()).sign(Algorithm.HMAC256(SECRET));
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setPath("/");
        cookie.setMaxAge(15 * 24 * 3600);
        response.addCookie(cookie);
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT verify = verifier.verify(token);
        return verify.getClaims();
    }

    /**
     * 从cookie中获取商户id
     *
     * @param request
     * @return
     */
    public static String getShopToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
