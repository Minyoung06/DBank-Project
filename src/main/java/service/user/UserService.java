package service.user;

import domain.AccountVO;

public interface UserService {
    boolean register(String name, String loginId, String password, String phone, String address, String ssn);
    boolean login(String loginId, String password);
    AccountVO getAccountByUserId(int userId);
}
