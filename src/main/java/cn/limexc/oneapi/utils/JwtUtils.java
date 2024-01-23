/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import cn.limexc.oneapi.dto.UserDTO;
import cn.limexc.oneapi.security.constant.SecurityConstants;
import cn.limexc.oneapi.security.constant.UserRoleConstants;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.xml.bind.DatatypeConverter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Jwt 工具类，用于生成、解析与验证 token
 *
 * @author LIMEXC
 **/
@Slf4j
public final class JwtUtils {


    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private JwtUtils() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    /**
     * 根据用户名和用户角色生成 token
     *
     * @param userId   用户Id
     * @param roles      用户角色
     * @param isRemember 是否记住我
     * @return 返回生成的 token
     */
    public static String generateToken(String userId, Set<String> roles, boolean isRemember) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
        // 过期时间
        long expiration = isRemember ? SecurityConstants.EXPIRATION_REMEMBER_TIME : SecurityConstants.EXPIRATION_TIME;
        // 生成 token
        return Jwts.builder()
                // 生成签证信息
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .setSubject(userId)
                .claim(SecurityConstants.TOKEN_ROLE_CLAIM, roles)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                // 设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 验证 token 是否有效
     *
     * <p>
     * 如果解析失败，说明 token 是无效的
     *
     * @param token token 信息
     * @return 如果返回 true，说明 token 有效
     */
    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    /**
     * 根据 token 获取用户认证信息
     *
     * @param token token 信息
     * @return 返回用户认证信息
     */
    public static Authentication getAuthentication(String token) {
        // 这里重写 通过验证后的token 通过redis获取相应的 权限
        Claims claims = getTokenBody(token);
        // 获取用户角色字符串
        // List<String> roles = (List<String>)claims.get(SecurityConstants.TOKEN_ROLE_CLAIM);
        // 通过token从redis中获取权限相关信息
        String userDataJson = RedisUtils.StringOps.get("ONEAPI_ONLINE_USER:"+token);
        UserDTO user = JSONObject.parseObject(userDataJson,UserDTO.class);
        List<String> auths = new ArrayList<>(user.getAuths());
        List<SimpleGrantedAuthority> authorities =
                Objects.isNull(auths) ? Collections.singletonList(new SimpleGrantedAuthority(UserRoleConstants.ROLE_USER)) :
                        auths.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
        // 获取用户ID
        // String userId = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(user, token, authorities);

    }

    private static Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}