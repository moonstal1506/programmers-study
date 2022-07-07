package com.programmers.java.poly;

public class Main {
    public static void main(String[] args) {
//        Login user = new KakaoLogin();
//        Login user = new NaverLogin();
//        new Main().run(LoginType.Naver);
        UserService service = new UserService(new KakaoLogin());
        service.login();
    }

//    void run(LoginType loginType) {
//        Login user =getLogin(loginType);
//        user.login();
//    }
//
//    private static Login getLogin(LoginType type) {
//        if(type == LoginType.Kakao) return new KakaoLogin();
//        return new NaverLogin();
//    }
}
