package user;

import dao.UserDao;
import dao.UserDaoImpl;
import database.JDBCUtil;
import domain.UserVO;

import java.sql.Connection;
import java.util.List;

public class UserDaoImplTestManual {
    private UserDao userDao;
    private Connection conn = JDBCUtil.getConnection();
    public UserDaoImplTestManual() {
        userDao = new UserDaoImpl();
    }

    public void testInsertAndFindId() {
        UserVO user = UserVO.builder()
                .loginId("testUser")
                .password("testPass")
                .name("테스트유저")
                .phone("01012345678")
                .address("테스트주소")
                .ssn("900101-12345")
                .build();

        userDao.insert(user, conn);
        System.out.println("Inserted user: " + user);

        List<UserVO> allUsers = userDao.findAll();
        UserVO insertedUser = allUsers.stream()
                .filter(u -> u.getLoginId().equals("testUser"))
                .findFirst()
                .orElse(null);

        if (insertedUser == null) {
            System.out.println("Insert or findAll failed.");
            return;
        }

        UserVO foundUser = userDao.findById(insertedUser.getUserId());

        if (foundUser != null && "testUser".equals(foundUser.getLoginId())) {
            System.out.println("testInsertAndFindId passed");
        } else {
            System.out.println("testInsertAndFindId failed");
        }
    }

    public void testLogin() {
        UserVO user = userDao.login("testUser", "testPass");
        if (user != null && "testUser".equals(user.getLoginId())) {
            System.out.println("testLogin passed");
        } else {
            System.out.println("testLogin failed");
        }
    }

    public void testExistsByLoginId() {
        boolean exists = userDao.existsByLoginId("testUser");
        boolean notExists = userDao.existsByLoginId("nonexistent");

        if (exists && !notExists) {
            System.out.println("testExistsByLoginId passed");
        } else {
            System.out.println("testExistsByLoginId failed");
        }
    }

    public void testUpdate() {
        List<UserVO> allUsers = userDao.findAll();
        UserVO user = allUsers.stream()
                .filter(u -> u.getLoginId().equals("testUser"))
                .findFirst()
                .orElse(null);

        if (user == null) {
            System.out.println("User not found for update test");
            return;
        }

        user.setName("변경된 이름");
        user.setPhone("01099998888");
        userDao.update(user);

        UserVO updatedUser = userDao.findById(user.getUserId());

        if ("변경된 이름".equals(updatedUser.getName()) && "01099998888".equals(updatedUser.getPhone())) {
            System.out.println("testUpdate passed");
        } else {
            System.out.println("testUpdate failed");
        }
    }

    public void testDelete() {
        List<UserVO> allUsers = userDao.findAll();
        UserVO user = allUsers.stream()
                .filter(u -> u.getLoginId().equals("testUser"))
                .findFirst()
                .orElse(null);

        if (user == null) {
            System.out.println("User not found for delete test");
            return;
        }

        userDao.delete(user.getUserId());

        if (userDao.findById(user.getUserId()) == null) {
            System.out.println("testDelete passed");
        } else {
            System.out.println("testDelete failed");
        }
    }

    public static void main(String[] args) {
        UserDaoImplTestManual test = new UserDaoImplTestManual();

        test.testInsertAndFindId();
        test.testLogin();
        test.testExistsByLoginId();
        test.testUpdate();
        test.testDelete();
    }
}
