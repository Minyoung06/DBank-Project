package app.user;

import account.dao.AccountDaoImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import user.dao.UserDaoImpl;

public class UserAppTest {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserDaoImpl(),new AccountDaoImpl());

        UserApp userApp = new UserApp(userService);

        while(true){
            userApp.start();
        }

    }
}
