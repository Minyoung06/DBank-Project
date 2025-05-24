package Transaction.dao;

import Transaction.domain.TransactionVO;
import database.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDaoImpl implements TransactionDao {
    Connection conn = JDBCUtil.getConnection();
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
    public void insert(TransactionVO transaction) {
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
        } catch (SQLException e) {
            e.printStackTrace();
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
}
