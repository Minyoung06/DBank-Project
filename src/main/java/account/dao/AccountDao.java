package account.dao;

import account.domain.AccountVO;

import java.sql.Connection;
import java.util.List;

public interface AccountDao {
    int insertAccount(AccountVO account);              // 계좌 등록
    AccountVO getAccountByUserId(int userId); // 유저 ID로 조회
    boolean verifyReceiver(String accountNumber, String receiverName);
    AccountVO getAccountById(Connection conn, int accountId);    // 계좌 ID로 조회

    int updateBalance(Connection conn, int accountId, double balance);

    int deleteAccount(int accountId);         // 계좌 삭제
}