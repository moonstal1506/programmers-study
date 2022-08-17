package com.prgrms.devcource.configures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEventHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Async //얘 오래걸릴 수 있음 -> 스레드 분리
    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        //Async 전: 5초 후 로그인
        //Async 후: 로그인 되고 5초 후 로그찍힘, 스레드 달라짐
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Authentication authentication = event.getAuthentication();
        log.info("Successful authentication result: {}", authentication.getPrincipal());
    }

    @EventListener
    public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
        Exception e = event.getException();
        Authentication authentication = event.getAuthentication();
        log.info("Unsuccessful authentication result: {}", authentication, e);
    }
}
