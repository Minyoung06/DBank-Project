package service.transactionList;

import dao.TransactionDao;
import domain.TransactionVO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionListServiceImpl implements TransactionListService {

    private final TransactionDao transactionDao;

    public TransactionListServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public List<TransactionVO> getAllTransactionsByUserId(int userId) {
        // 날짜 역순 정렬
        List<TransactionVO> transactions = transactionDao.getDetailedByUserId(userId);
        // timestamp 내림차순 정렬
        transactions.sort(Comparator.comparing(TransactionVO::getTimestamp).reversed());
        return transactions;
    }

    @Override
    public List<TransactionVO> getTransactionsByUserIdSorted(int userId, String sortBy, boolean asc) {
        List<TransactionVO> transactions = transactionDao.getDetailedByUserId(userId);

        Comparator<TransactionVO> comparator;

        switch (sortBy.toLowerCase()) {
            case "amount":
                comparator = Comparator.comparing(TransactionVO::getAmount);
                break;
            case "memo":
                comparator = Comparator.comparing(
                        tx -> tx.getMemo() == null ? "" : tx.getMemo(),
                        String.CASE_INSENSITIVE_ORDER
                );
                break;

            case "timestamp":
                comparator = Comparator.comparing(TransactionVO::getTimestamp);
                break;
            case "counterpartyname":
                comparator = Comparator.comparing(tx -> tx.getCounterpartyName() == null ? "" : tx.getCounterpartyName());
                break;
            default:
                // 기본 정렬: timestamp 내림차순
                comparator = Comparator.comparing(TransactionVO::getTimestamp).reversed();
        }

        if (!asc) {
            comparator = comparator.reversed();
        }

        transactions.sort(comparator);
        return transactions;
    }



    @Override
    public List<TransactionVO> getRecentTransactionsByUserId(int userId, int limit) {
        List<TransactionVO> transactions = transactionDao.getDetailedByUserId(userId);

        // timestamp 내림차순 정렬 후 limit 개수만큼 반환
        return transactions.stream()
                .sorted(Comparator.comparing(TransactionVO::getTimestamp).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
