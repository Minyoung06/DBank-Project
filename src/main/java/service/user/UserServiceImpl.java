package service.user;

import account.dao.AccountDao;
import account.domain.AccountVO;
import common.Session;
import database.JDBCUtil;

import user.dao.UserDao;
import user.domain.UserVO;
import util.AccountUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final AccountDao accountDao;

    public UserServiceImpl(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @Override
    public boolean register(String name, String loginId, String password, String phone, String address, String ssn) {
        Connection conn = null;
        //계좌 생성 - 중복이면 다시 생성
        String accountNumber;
        do {
            accountNumber = AccountUtil.generateAccountNumber();
        } while (accountDao.isAccountNumberExists(accountNumber));

        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            UserVO user = UserVO.builder()
                    .name(name)
                    .loginId(loginId)
                    .password(password)
                    .phone(phone)
                    .address(address)
                    .ssn(ssn)
                    .build();
            int savedUserId = userDao.insert(user, conn);
            if (savedUserId == -1) {
                System.out.println("이미 사용 중인 로그인 ID입니다.");
                return false;
            }
            if (savedUserId == -2) {
                System.out.println("이미 가입한 사용자입니다.");
                return false;
            }


            AccountVO account = AccountVO.builder()
                    .balance(0.0)
                    .userId(savedUserId)
                    .accountNumber(accountNumber)
                    .build();
            int savedAccountId = accountDao.insertAccount(account, conn);


            if (savedAccountId == -1) throw new SQLException("계좌 생성 실패");

            conn.commit();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 실패 시 롤백
                    System.err.println("트랜잭션 롤백됨: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException(" 회원가입 실패", e);
        }finally {
            if (conn != null) {
                try {
                    conn.close(); //자원 반납
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public boolean login(String loginId, String password) {
        UserVO user = userDao.login(loginId,password);
        if(user!=null){
            Session.login(user);
            return true;
        }
        return false;
    }

    @Override
    public AccountVO getAccountByUserId(int userId) {
        return accountDao.getAccountByUserId(userId);
    }
}
