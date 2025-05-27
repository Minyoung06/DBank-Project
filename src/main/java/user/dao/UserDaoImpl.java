package user.dao;

import database.JDBCUtil;
import user.domain.UserVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{


    @Override
    public int insert(UserVO user, Connection conn) {
        // login_id 중복 검사

        if (isLoginIdDuplicated(user.getLoginId(), conn)) {
            return -1;
        }

        String sql = "INSERT INTO user (login_id, password, name, phone, address, ssn) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, user.getLoginId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4,user.getPhone());
            pstmt.setString(5,user.getAddress());
            pstmt.setString(6, user.getSsn());
            pstmt.executeUpdate();
            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1);
                }
                else{
                    throw new SQLException("user_id 생성 실패");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("회원 등록 실패",e);
        }
    }

    @Override
    public UserVO findById(int userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1,userId);
            try (ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) return map(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("회원 조회 실패",e);
        }
    }

    @Override
    public List<UserVO> findAll() {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM user ORDER BY user_id";
        List<UserVO> users = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while(rs.next()){
                users.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("전체 회원 조회 실패",e);
        }
        return users;
    }

    @Override
    public void update(UserVO user) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "UPDATE user SET login_id = ?, password = ?, name = ?, phone = ?, address = ?, ssn = ? WHERE user_id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, user.getLoginId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6,user.getSsn());
            pstmt.setInt(7,user.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 정보 수정 실패",e);
        }
    }

    @Override
    public void delete(int userId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "DELETE FROM user WHERE user_id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 삭제 실패",e);
        }
    }

    @Override
    public UserVO login(String loginId, String password) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM user WHERE login_id = ? AND password = ?";
        try (PreparedStatement pstmt =conn.prepareStatement(sql)){
            pstmt.setString(1,loginId);
            pstmt.setString(2, password);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return map(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("로그인 중 오류 발생",e);
        }
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT 1 FROM user WHERE login_id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,loginId);
            try (ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("아이디 중복 확인 실패",e);
        }
    }

    @Override
    public boolean isLoginIdDuplicated(String loginId, Connection conn) {
        String sql = "SELECT COUNT(*) FROM user WHERE login_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private UserVO map(ResultSet rs) throws SQLException {
        return UserVO.builder()
                .userId(rs.getInt("user_id"))
                .loginId(rs.getString("login_id"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .phone(rs.getString("phone"))
                .address(rs.getString("address"))
                .ssn(rs.getString("ssn"))
                .build();
    }
}
