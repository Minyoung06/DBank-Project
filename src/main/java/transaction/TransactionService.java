package transaction;

import dao.TransactionDao;
import domain.TransactionVO;
import dao.AccountDao;
import domain.AccountVO;
import common.Session;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TransactionService {
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    public TransactionService(AccountDao accountDao,
                              TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }
    /**
     * 계좌번호 + 사용자 이름을 검증하고 이체를 수행합니다.
     * @param toAccountNumber - 받는 사람 계좌번호
     * @param amount          - 이체 금액
     * @param memo            - 메모
     * @return true: 성공, false: 실패
     */


    public boolean transferByAccountNumber(String toAccountNumber,
                                            BigDecimal amount,
                                            String memo){
        // 로그인 여부 확인
        if(!Session.isLoggedIn()){
            System.err.println("[오류] 로그인 후 이용해주세요.");
            return false;
        }

        try{
            // 보내는 사람 계좌 조회
            int userId = Session.getUserId();
            AccountVO fromAcc = accountDao.getAccountByUserId(userId);
            if(fromAcc==null){
                System.err.println("[오류] 내 계좌를 찾을 수 없습니다");
                return false;
            }

            // 받는 사람 계좌 조회 및 이름 검증

            AccountVO toAcc = accountDao.getAccountByNumber(toAccountNumber);
            if(toAcc==null){
                System.err.println("[오류] 받는 사람 계좌를 찾을 수 없습니다");
                return false;
            }
            TransactionVO transaction = TransactionVO.builder()
                    .send_account_id(fromAcc.getAccountId())
                    .receiver_account_id(toAcc  .getAccountId())
                    .amount            (amount)
                    .memo              (memo)
                    .build();

            transactionDao.insert(transaction);
            return true;
        }catch (SQLException e){
            System.err.println("[이체 실패] : " + e.getMessage());
            return false;
        }

    }
}
