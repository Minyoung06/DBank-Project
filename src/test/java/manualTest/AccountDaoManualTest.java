package manualTest;

import dao.AccountDao;
import dao.AccountDaoImpl;
import domain.AccountVO;
import database.JDBCUtil;

import java.sql.Connection;

public class AccountDaoManualTest {
    public static void main(String[] args) {
        AccountDao dao = new AccountDaoImpl();
        Connection conn = JDBCUtil.getConnection();
        //insertAccount test
        AccountVO newAccount = AccountVO.builder()
                .userId(3)          // user 테이블에 존재하는 ID
                .balance(0.00)
                .build();

        int result = dao.insertAccount(newAccount);
        System.out.println(result > 0 ? "✅ 계좌 등록 성공!" : "❌ 계좌 등록 실패...");

        System.out.println("======================================");

        //getAccountById test
        AccountVO account = dao.getAccountById(conn,1); // 1번 계좌 조회
        //1,1,123456.78,1101234567890
        if (account != null) {
            System.out.println("계좌id: " + account.getAccountId());
            System.out.println("계좌번호: " + account.getAccountNumber());
            System.out.println("잔액: " + account.getBalance());
        } else {
            System.out.println("해당 ID의 계좌가 존재하지 않습니다.");
        }

        System.out.println("======================================");

        //getAccountByUserId test
        AccountVO myAccount = dao.getAccountByUserId(3);
        if (myAccount != null) {
            System.out.println("계좌id: " + myAccount.getAccountId());
            System.out.println("계좌번호: " + myAccount.getAccountNumber());
            System.out.println("잔액: " + myAccount.getBalance());
        } else {
            System.out.println("해당 유저의 계좌가 없습니다.");
        }

        System.out.println("======================================");

        //updateBalance test
        int updated = dao.updateBalance(conn,1, 123456.78);

        System.out.println(updated > 0 ? "✅ 잔액 업데이트 성공" : "❌ 실패");

        //delete test
        int deleted = dao.deleteAccount(3);
        System.out.println(deleted > 0 ? "✅ 계좌 삭제 성공" : "❌ 계좌 삭제 실패");
    }
}
