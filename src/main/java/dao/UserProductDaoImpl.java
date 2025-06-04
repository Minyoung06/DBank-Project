package dao;

import database.JDBCUtil;
import domain.UserProductVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserProductDaoImpl implements UserProductDao {

    Connection conn = JDBCUtil.getConnection();

    // 1. 가입 정보 등록
    @Override
    public int insert(UserProductVO product) {
        String sql = "INSERT INTO user_product (user_id, product_id, start_date, end_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getUser_id());
            pstmt.setInt(2, product.getProduct_id());
            pstmt.setDate(3, java.sql.Date.valueOf(product.getStart_date()));
            pstmt.setDate(4, java.sql.Date.valueOf(product.getEnd_date()));
            pstmt.setString(5, product.getStatus());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;// 예외 처리는 실제 프로젝트에선 로깅으로 대체
        }
    }

    // 2. 가입 정보 수정 (상태 or 종료일 수정 등)
    @Override
    public void update(UserProductVO product) {
        String sql = "UPDATE user_product SET user_id = ?, product_id = ?, start_date = ?, end_date = ?, status = ? " +
                "WHERE user_product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getUser_id());
            pstmt.setInt(2, product.getProduct_id());
            pstmt.setDate(3, java.sql.Date.valueOf(product.getStart_date()));
            pstmt.setDate(4, java.sql.Date.valueOf(product.getEnd_date()));
            pstmt.setString(5, product.getStatus());
            pstmt.setInt(6, product.getUser_product_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. 가입 내역 삭제
    @Override
    public void deleteById(int userProductId) {
        String sql = "DELETE FROM user_product WHERE user_product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userProductId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. 특정 유저가 가입한 모든 상품 조회
    // JOIN 없이 user_product 단독 조회
    @Override
    public List<UserProductVO> findByUserId(int userId) {
        List<UserProductVO> list = new ArrayList<>();
        String sql = "SELECT * FROM user_product WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserProductVO up = UserProductVO.builder()
                        .user_product_id(rs.getInt("user_product_id"))
                        .user_id(rs.getInt("user_id"))
                        .product_id(rs.getInt("product_id"))
                        .start_date(rs.getDate("start_date").toLocalDate())
                        .end_date(rs.getDate("end_date").toLocalDate())
                        .status(rs.getString("status"))
                        .build();
                list.add(up);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 5. 상품명 오름차순 정렬
    @Override
    public List<UserProductVO> findAllOrderByProductNameAsc(int userId) {
        List<UserProductVO> list = new ArrayList<>();
        String sql = """
            SELECT up.*, p.name AS product_name
            FROM user_product up
                JOIN product p ON up.product_id = p.product_id
            WHERE up.user_id = ?
            ORDER BY p.name ASC
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserProductVO up = UserProductVO.builder()
                        .user_product_id(rs.getInt("user_product_id"))
                        .user_id(rs.getInt("user_id"))
                        .product_id(rs.getInt("product_id"))
                        .start_date(rs.getDate("start_date").toLocalDate())
                        .end_date(rs.getDate("end_date").toLocalDate())
                        .status(rs.getString("status"))
                        .product_name(rs.getString("product_name")) //추가
                        .build();
                list.add(up);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 6. 상품명 내림차순 정렬
    @Override
    public List<UserProductVO> findAllOrderByProductNameDesc(int userId) {
        List<UserProductVO> list = new ArrayList<>();
        String sql = """
            SELECT up.*, p.name AS product_name
            FROM user_product up
                JOIN product p ON up.product_id = p.product_id
            WHERE up.user_id = ?
            ORDER BY p.name DESC
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserProductVO up = UserProductVO.builder()
                        .user_product_id(rs.getInt("user_product_id"))
                        .user_id(rs.getInt("user_id"))
                        .product_id(rs.getInt("product_id"))
                        .start_date(rs.getDate("start_date").toLocalDate())
                        .end_date(rs.getDate("end_date").toLocalDate())
                        .status(rs.getString("status"))
                        .product_name(rs.getString("product_name"))
                        .build();
                list.add(up);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7. 만기일 오름차순 정렬
    @Override
    public List<UserProductVO> findAllOrderByEndDateAsc(int userId) {
        List<UserProductVO> list = new ArrayList<>();
        String sql = """
        SELECT up.*, p.name AS product_name
        FROM user_product up
            JOIN product p ON up.product_id = p.product_id
        WHERE up.user_id = ?
        ORDER BY up.end_date ASC
    """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserProductVO up = UserProductVO.builder()
                        .user_product_id(rs.getInt("user_product_id"))
                        .user_id(rs.getInt("user_id"))
                        .product_id(rs.getInt("product_id"))
                        .start_date(rs.getDate("start_date").toLocalDate())
                        .end_date(rs.getDate("end_date").toLocalDate())
                        .status(rs.getString("status"))
                        .product_name(rs.getString("product_name"))
                        .build();
                list.add(up);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 8. 만기일 내림차순 정렬
    @Override
    public List<UserProductVO> findAllOrderByEndDateDesc(int userId) {
        List<UserProductVO> list = new ArrayList<>();
        String sql = """
        SELECT up.*, p.name AS product_name
        FROM user_product up
            JOIN product p ON up.product_id = p.product_id
        WHERE up.user_id = ?
        ORDER BY up.end_date DESC
    """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserProductVO up = UserProductVO.builder()
                        .user_product_id(rs.getInt("user_product_id"))
                        .user_id(rs.getInt("user_id"))
                        .product_id(rs.getInt("product_id"))
                        .start_date(rs.getDate("start_date").toLocalDate())
                        .end_date(rs.getDate("end_date").toLocalDate())
                        .status(rs.getString("status"))
                        .product_name(rs.getString("product_name"))
                        .build();
                list.add(up);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Join 사용. product_name 포함하는 결과
    @Override
    public List<UserProductVO> findJoinedProductByUserId(int userId) {
        String sql = """
        SELECT 
            up.user_product_id,
            up.user_id,
            up.product_id,
            up.start_date,
            up.end_date,
            up.status,
            p.name AS product_name
        FROM user_product up
        JOIN product p ON up.product_id = p.product_id
        WHERE up.user_id = ?
    """;
        List<UserProductVO> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserProductVO vo = UserProductVO.builder()
                        .user_product_id(rs.getInt("user_product_id"))
                        .user_id(rs.getInt("user_id"))
                        .product_id(rs.getInt("product_id"))
                        .start_date(rs.getDate("start_date").toLocalDate())
                        .end_date(rs.getDate("end_date").toLocalDate())
                        .status(rs.getString("status"))
                        .product_name(rs.getString("product_name")) // 추가
                        .build();

                list.add(vo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}