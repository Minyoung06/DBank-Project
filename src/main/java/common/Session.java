package common;

import user.domain.UserVO;

public class Session {
    private static UserVO loggedInUser;

    public static void login(UserVO user){
        loggedInUser = user;
    }

    public static UserVO getUser(){
        return loggedInUser;
    }

    public static int getUserId(){
        if(loggedInUser!=null){
            return loggedInUser.getUserId();
        }
        return -1;
    }

    public static String getUserName(){
        if(loggedInUser!=null){
            return loggedInUser.getName();
        }
        return null;
    }

    public static void logout() {
        loggedInUser = null;
    }
}
