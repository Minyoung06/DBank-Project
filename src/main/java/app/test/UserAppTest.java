package app.test;

import dao.AccountDaoImpl;
import app.UserApp;
import service.user.UserService;
import service.user.UserServiceImpl;
import dao.UserDaoImpl;

public class UserAppTest {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserDaoImpl(),new AccountDaoImpl());

        UserApp userApp = new UserApp(userService);

        while(true){
            userApp.start();
        }

    }
}
