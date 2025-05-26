package Transaction.dao;

import Transaction.domain.TransactionVO;
import account.dao.AccountDaoImpl;
import account.domain.AccountVO;
import database.JDBCUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDaoImpl implements TransactionDao {
    private final AccountDaoImpl accountDao;
    private final Connection conn;

    // AccountDaoImpl 주입
    public TransactionDaoImpl(AccountDaoImpl accountDao) {
        this.accountDao = accountDao;
        this.conn = JDBCUtil.getConnection();
    }

    private TransactionVO map(ResultSet rs) throws SQLException {
        TransactionVO Transaction = new TransactionVO();
        Transaction.setTransaction_id(rs.getInt("Transaction_id"));
        Transaction.setSend_account_id(rs.getInt("send_account_id"));
        Transaction.setReciver_account_id(rs.getInt("reciver_account_id"));
        Transaction.setAmount(rs.getBigDecimal("amount"));
        Transaction.setMemo(rs.getString("memo"));
        Transaction.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return Transaction;
    }
    @Override
    public List<TransactionVO> getList() {
        String sql = "select * from transaction";
        List<TransactionVO> list = new ArrayList<TransactionVO>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()) {
                list.add(map(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<TransactionVO> get(int id) {
        String sql ="select * from transaction where transaction_id=?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<TransactionVO> getByAccountId(int accountId){
        String sql = "SELECT * FROM transaction "
                + "WHERE send_account_id = ? OR reciver_account_id = ?";
        List<TransactionVO> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(TransactionVO transaction) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // 2) 보내는 사람 계좌 차감
            AccountVO fromAcc = accountDao.getAccountById(conn, transaction.getSend_account_id());
            double fromNew = fromAcc.getBalance() - transaction.getAmount().doubleValue();
            int affected1 = accountDao.updateBalance(conn, transaction.getSend_account_id(), fromNew);
            if (affected1 != 1) {
                throw new SQLException("출금 실패: 계좌 " + transaction.getSend_account_id());
            }


            // 3) 받는 사람 계좌 입금
            AccountVO toAcc = accountDao.getAccountById(conn, transaction.getReciver_account_id());
            double toNew = toAcc.getBalance() + transaction.getAmount().doubleValue();
            int affected2 = accountDao.updateBalance(conn, transaction.getReciver_account_id(), toNew);
            if (affected2 != 1) {
                throw new SQLException("입금 실패: 계좌 " + transaction.getReciver_account_id());
            }


            String sql = ""
                    + "INSERT INTO transaction "
                    + "(send_account_id, reciver_account_id, amount, memo, timestamp)"
                    + "VALUES (?, ?, ?, ?, NOW())";
            try (PreparedStatement pstmt =
                         conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                // 보내는 계좌
                pstmt.setInt(1, transaction.getSend_account_id());
                //받는 계좌
                pstmt.setInt(2, transaction.getReciver_account_id());
                pstmt.setBigDecimal(3, transaction.getAmount());
                pstmt.setString(4, transaction.getMemo());

                int affected = pstmt.executeUpdate();
                // 자동 생성된 PK를 VO에 담아두기
                if (affected > 0) {
                    try (ResultSet keys = pstmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            transaction.setTransaction_id(keys.getInt(1));
                        }
                    }
                }
            }
            conn.commit();
        }catch (SQLException e){
            conn.rollback();
            throw e;
        }finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void update(TransactionVO transaction) {
        String sql =
                "UPDATE transaction "
                        + "SET send_account_id    = ?, "
                        + "reciver_account_id = ?, "
                        + "amount= ?, "
                        +  "memo= ? "
                        + "WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getSend_account_id());
            pstmt.setInt(2, transaction.getReciver_account_id());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setString(4, transaction.getMemo());
            pstmt.setInt(5, transaction.getTransaction_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM transaction WHERE transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            System.out.println("삭제된 행 개수: " + affected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TransactionVO> getByUserId(int userId) {
        String sql = """
        SELECT t.* 
        FROM transaction t
        JOIN account a ON t.send_account_id = a.account_id OR t.reciver_account_id = a.account_id
        WHERE a.user_id = ?
        ORDER BY t.timestamp DESC
        """;

        List<TransactionVO> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
