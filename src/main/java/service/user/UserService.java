package service.user;

public interface UserService {
    boolean register(String name, String loginId, String password, String phone, String address, String ssn);
    boolean login(String loginId, String password);
}
