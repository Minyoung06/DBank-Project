package account.dao;

import account.domain.AccountVO;
import database.JDBCUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Date;


public class AccountDaoImpl implements AccountDao {
    //공통 빌더 메소드
    private AccountVO buildAccount(ResultSet rs) throws SQLException {
        return AccountVO.builder()
                .accountId(rs.getInt("account_id"))
                .userId(rs.getInt("user_id"))
                .balance(rs.getDouble("balance"))
                .accountNumber(rs.getString("account_number"))
                .build();
    }

    @Override
    public int insertAccount(AccountVO account) {
        // 중복 계좌 여부 확인
        String checkSql = "SELECT account_id FROM account WHERE user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, account.getUserId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("❗ 사용자에게 이미 계좌가 존재합니다. account_id: " + rs.getInt("account_id"));
                return 0; // 계좌 중복
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        //생성
        String sql = "INSERT INTO account (user_id, balance, account_number) VALUES (?, ?, ?)";
        String accountNumber = generateAccountNumber(account.getUserId()); // 내부 생성

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, account.getUserId());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setString(3, accountNumber);

            return pstmt.executeUpdate(); // 성공 시 1 반환
        } catch (SQLException e) {
            System.err.println("❌ 계좌 등록 실패: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private String generateAccountNumber(int userId) {
        String datePart = new SimpleDateFormat("yyMMdd").format(new Date());
        String userPart = String.valueOf(userId);
        int randomLength = 13 - datePart.length() - userPart.length();

        String accountNumber;
        Random random = new Random();
        do {
            StringBuilder randomPart = new StringBuilder();
            for (int i = 0; i < randomLength; i++) {
                randomPart.append(random.nextInt(10));
            }
            accountNumber = datePart + userPart + randomPart;
        } while (isAccountNumberExists(accountNumber));

        return accountNumber;
    }

    private boolean isAccountNumberExists(String accountNumber) {
        String sql = "SELECT COUNT(*) FROM account WHERE account_number = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public AccountVO getAccountByUserId(int userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return AccountVO.builder()
                            .accountId(rs.getInt("account_id"))
                            .userId(rs.getInt("user_id"))
                            .accountNumber(rs.getString("account_number"))
                            .balance(rs.getDouble("balance"))
                            .build();
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ 유저 계좌 조회 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // 존재하지 않을 경우 null 반환
    }


    @Override
    public AccountVO getAccountById(Connection conn, int accountId) {
        String sql = "SELECT * FROM account WHERE account_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return AccountVO.builder()
                        .accountId(rs.getInt("account_id"))
                        .userId(rs.getInt("user_id"))
                        .balance(rs.getDouble("balance"))
                        .accountNumber(rs.getString("account_number"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 조회 실패 시 null 반환
    }

    @Override
    public int updateBalance(Connection conn, int accountId, double balance) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, balance);     // 첫 번째 ? → 새 잔액
            pstmt.setInt(2, accountId);      // 두 번째 ? → 대상 계좌 ID

            return pstmt.executeUpdate();    // 1이면 성공
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // 실패 시 0 반환
    }

    @Override
    public int deleteAccount(int accountId) {
        String sql = "DELETE FROM account WHERE account_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountId);
            return pstmt.executeUpdate(); // 삭제 성공 시 1 반환
        } catch (SQLException e) {
            System.err.println("❌ 계좌 삭제 실패: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean verifyReceiver(String accountNumber, String receiverName) {
       String sql = """
               SELECT 1
                      FROM account a
                      JOIN user u ON a.user_id = u.user_id
                      WHERE a.account_number = ? AND u.name = ?
               """;
        try(Connection conn = JDBCUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, receiverName);
            try(ResultSet rs= pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
