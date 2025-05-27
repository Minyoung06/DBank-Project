package Transaction.dao;

import Transaction.domain.TransactionVO;
import account.dao.AccountDaoImpl;

import java.util.List;

public class TransactionDaoMainTest {
    public static void main(String[] args) {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        TransactionDao dao = new TransactionDaoImpl(accountDao);

        int userId = 1;

        List<TransactionVO> transactions = dao.getByUserId(userId);

        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("거래내역 개수: " + transactions.size());
            for (TransactionVO tx : transactions) {
                System.out.println(tx);
            }
        } else {
            System.out.println("해당 유저의 거래내역이 없습니다.");
        }
    }
}
