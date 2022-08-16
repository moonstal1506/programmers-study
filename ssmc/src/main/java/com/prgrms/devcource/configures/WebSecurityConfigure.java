package com.prgrms.devcource.configures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user123").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin123").roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/assets/**"); //스프링 시큐리티 필터 적용하지 않겠다
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/me").hasAnyRole("USER", "ADMIN") //인증 영역
                .antMatchers("/admin").access("isFullyAuthenticated() and hasRole('ADMIN')") //isFullyAuthenticated 리멤머미로 인증되지 않은 사용자
                .anyRequest().permitAll() //익명영역
                .and()
                .formLogin() //스프링 시큐리티가 로그인 폼 자동 생성
                .defaultSuccessUrl("/") //로그인 성공시 갈 곳
                .permitAll() //로그인은 권한 필요 없음
                .and()
                /**
                 * 로그아웃 설정
                 */
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//얘도 디폴트임
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)//생략해도 무관 이미 기본값에 설정
                .clearAuthentication(true)
                .and()
                /**
                 * remember me 설정
                 */
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(300)
                .and()
                /**
                 * http요청을 https요청으로 리다이렉트
                 */
                .requiresChannel()
//                .antMatchers("/api/**")
                .anyRequest().requiresSecure()  //모든 요청은 시큐어(https) 필요
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
        ;
    }

    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication != null ? authentication.getPrincipal() : null;
            log.warn("{} is denied", principal, e);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain");
            response.getWriter().write("## ACCESS DENIED ##");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }
}
