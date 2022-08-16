package com.prgrms.devcource.configures;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

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
                .anyRequest().permitAll() //익명영역
                .and()
                .formLogin() //스프링 시큐리티가 로그인 폼 자동 생성
                .defaultSuccessUrl("/") //로그인 성공시 갈 곳
                .permitAll() //로그인은 권한 필요 없음
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//얘도 디폴트임
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)//생략해도 무관 이미 기본값에 설정
                .clearAuthentication(true)
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(300)
                .and()
                .requiresChannel()
//                .antMatchers("/api/**")
                .anyRequest().requiresSecure()  //모든 요청은 시큐어(https) 필요
        ;
    }
}
