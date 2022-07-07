package com.programmers.java.poly;

public class UserService implements Login{

    //Login에 의존한다
    //추상체와 결합하면 결합도 낮아짐
    //의존성 외부로부터 전달받음->의존성 주입 dependency injection
    private Login login;

    //의존성을 외부에 맡기면 의존도를 낮춘다
    public UserService(Login login) {
        this.login = login;
    }

    @Override
    public void login() {
        login.login();
    }
}
