package user.dao;

import user.domain.UserVO;

import java.util.List;

public interface UserDao {
    void insert(UserVO user);
    UserVO findById(int userId);
    List<UserVO> findAll();
    void update(UserVO user);
    void delete(int userId);
    UserVO login(String loginId, String password);
    boolean existsByLoginId(String loginId);

}
