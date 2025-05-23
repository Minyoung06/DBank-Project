package account.dao;

import account.domain.AccountVO;

import java.util.List;

public interface AccountDao {
    int insertAccount(AccountVO account);              // 계좌 등록
    AccountVO getAccountById(int accountId);        // 계좌 ID로 조회
    AccountVO getAccountByUserId(int userId); // 유저 ID로 조회
    int updateBalance(int accountId, double balance);  // 잔액 업데이트
    int deleteAccount(int accountId);         // 계좌 삭제
}
