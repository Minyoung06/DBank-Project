package service.transaction;

import transaction.domain.TransactionVO;

import java.util.List;

public interface TransactionService {

    // 유저 ID로 모든 거래 내역 조회
    List<TransactionVO> getAllTransactionsByUserId(int userId);

    // 정렬 옵션으로 거래 내역 조회 (amount, memo, timestamp 등, asc/desc)
    List<TransactionVO> getTransactionsByUserIdSorted(int userId, String sortBy, boolean asc);


    // 거래 내역 N건 조회 (최신 N건)
    List<TransactionVO> getRecentTransactionsByUserId(int userId, int limit);


}
