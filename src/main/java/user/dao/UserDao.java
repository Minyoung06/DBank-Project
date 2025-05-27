package user.dao;

import user.domain.UserVO;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    int insert(UserVO user, Connection conn);
    UserVO findById(int userId);
    List<UserVO> findAll();
    void update(UserVO user);
    void delete(int userId);
    UserVO login(String loginId, String password);
    boolean existsByLoginId(String loginId);
    boolean isLoginIdDuplicated(String loginId, Connection conn);

}
