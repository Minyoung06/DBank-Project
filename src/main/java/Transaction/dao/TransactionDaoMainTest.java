package Transaction.dao;

import Transaction.domain.TransactionVO;

import java.util.List;

public class TransactionDaoMainTest {
    public static void main(String[] args) {
        TransactionDao dao = new TransactionDaoImpl();

        // 예: userId가 1인 사용자의 거래내역 조회 테스트
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
