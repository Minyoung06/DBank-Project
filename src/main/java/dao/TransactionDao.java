package dao;

import domain.TransactionVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    List<TransactionVO> getList();
    Optional<TransactionVO> get(int id);
    List<TransactionVO> getByAccountId(int accountId);
    void insert(TransactionVO transaction) throws SQLException;
    void update(TransactionVO transaction);
    void delete(int id);

    // 로그인한 유저의 거래내역 조회
//    List<TransactionVO> getByUserId(int userId);
    List<TransactionVO> getDetailedByUserId(int userId);

}
