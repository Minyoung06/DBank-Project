package Transaction.dao;

import Transaction.domain.TransactionVO;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    List<TransactionVO> getList();
    Optional<TransactionVO> get(int id);
    List<TransactionVO> getByAccountId(int accountId);
    void insert(TransactionVO transaction);
    void update(TransactionVO transaction);
    void delete(int id);
}
