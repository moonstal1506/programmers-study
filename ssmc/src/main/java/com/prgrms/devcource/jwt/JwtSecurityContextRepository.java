package com.prgrms.devcource.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

public class JwtSecurityContextRepository implements SecurityContextRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String headerKey;
    private final Jwt jwt;

    public JwtSecurityContextRepository(String headerKey, Jwt jwt) {
        this.headerKey = headerKey;
        this.jwt = jwt;
    }

    private JwtAuthenticationToken authenticate(HttpServletRequest request) {
        String token = getToken(request);
        if (isNotEmpty(token)) {
            //JWTVerificationException 예외 발생 가능
            try {
                Jwt.Claims claims = jwt.verify(token);
                log.debug("Jwt parse result: {}", claims);

                String username = claims.username;
                List<GrantedAuthority> authorities = getAuthorities(claims);

                if (isNotEmpty(username) && authorities.size() > 0) {
                    JwtAuthenticationToken authentication
                            = new JwtAuthenticationToken(new JwtAuthentication(token, username), null, authorities);

                    // 필수는 아니고 클라이언트 아이피와 세션아이디(우린사용x-null) 들고있음
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    return authentication;
                }
            } catch (Exception e) {
                log.warn("Jwt processing failed: {}", e.getMessage());
            }
        }
        return null;
    }

    //http 헤더에서 토큰을 꺼내오는 메서드
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(headerKey);
        if (isNotEmpty(token)) {
            log.debug("Jwt token detected: {}", token);
            try {
                return URLDecoder.decode(token, "UTF-8");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private List<GrantedAuthority> getAuthorities(Jwt.Claims claims) {
        String[] roles = claims.roles;
        return roles == null || roles.length == 0
                ? emptyList()
                : Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        JwtAuthenticationToken authenticate = authenticate(request);
        if (authenticate != null) {
            context.setAuthentication(authenticate);
        }
        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        //세션을 사용하지 않기 때문에 저장필요 없음
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        //jwt만드는 과정에 문제가 없다면 true
        JwtAuthenticationToken authentication = authenticate(request);
        return authentication != null;
    }
}
