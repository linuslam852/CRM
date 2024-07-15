package com.linus.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTUtils {

    public static  final String SECRET = "123456";
    public static String createJWT(String userJSON) {
        //组装头数据
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        //链式编程
        return JWT.create()
                //头部
                .withHeader(header)

                //负载(可以有多个)
                .withClaim("user", userJSON)

                //签名
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 驗證jwt
     *
     */
    public static Boolean verifyJWT(String jwt) {
        try {
            // 使用密鑰創建一個jwt驗證器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            //jwt驗證器驗證jwt
            jwtVerifier.verify(jwt);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 解析jwt
     *
     */
    public static String parseJWT(String jwt) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 使用密鑰創建一個jwt驗證器對象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            //jwt驗證器對象驗證jwt，得到一個解碼後的的jwt對象
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            //驗證通過了，就開始解析負載裡面的數據
            Claim userJSON = decodedJWT.getClaim("user");

            return userJSON.asString();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
//
//    /**
//     * 解析jwt得到userId
//     *
//     * @param jwt
//     * @return
//     */
//    public static Integer parseJWTByUserId(String jwt) {
//        String userJSON = parseJWT(jwt);
//        TUser user = JSONUtils.toBean(userJSON, TUser.class);
//        return user.getId();
//    }
//
//    /**
//     * 解析jwt得到userRole
//     *
//     * @param jwt
//     * @return
//     */
//    public static List<String> parseJWTByUserRole(String jwt) {
//        String userJSON = parseJWT(jwt);
//        TUser user = JSONUtils.toBean(userJSON, TUser.class);
//        return user.getStringRoleList();
//    }
}
