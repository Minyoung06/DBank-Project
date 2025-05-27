package transaction.dao;

import transaction.domain.TransactionVO;
import account.dao.AccountDaoImpl;

import java.util.List;

public class TransactionDaoMainTest {
    public static void main(String[] args) {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        TransactionDao dao = new TransactionDaoImpl(accountDao);

        int userId = 1;

        List<TransactionVO> transactions = dao.getDetailedByUserId(userId);

        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("거래내역 개수: " + transactions.size());
            for (TransactionVO tx : transactions) {
                System.out.println("거래 ID: " + tx.getTransaction_id());
                System.out.println("거래 종류: " + tx.getTransactionType());
                System.out.println("보낸 계좌 ID: " + tx.getSend_account_id());
                System.out.println("받은 계좌 ID: " + tx.getReciver_account_id());
                System.out.println("거래 금액: " + tx.getAmount());
                System.out.println("메모: " + tx.getMemo());
                System.out.println("거래 시간: " + tx.getTimestamp());
                System.out.println("상대방 이름: " + tx.getCounterpartyName());
                System.out.println("상대방 계좌번호: " + tx.getCounterpartyAccountNumber());
                System.out.println("----------");
            }
        } else {
            System.out.println("해당 유저의 거래내역이 없습니다.");
        }
    }
}
