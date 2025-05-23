package product.dao;

import database.JDBCUtil;
import product.domain.ProductVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    Connection conn = JDBCUtil.getConnection();

    @Override
    public void insertProduct(ProductVO product) {
        String sql = "INSERT INTO product (name, type, duration_month, interest_rate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getType());
            pstmt.setInt(3, product.getDurationMonth());
            pstmt.setBigDecimal(4, product.getInterestRate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProduct(ProductVO product) {
        String sql = "UPDATE product SET name = ?, type = ?, duration_month = ?, interest_rate = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getType());
            pstmt.setInt(3, product.getDurationMonth());
            pstmt.setBigDecimal(4, product.getInterestRate());
            pstmt.setInt(5, product.getProductId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductVO getProductById(int productId) {
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ProductVO> getAllProducts() {
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product";
        return getProductsByQuery(sql);
    }

    @Override
    public List<ProductVO> getProductsOrderByDurationDesc() {
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY duration_month DESC";
        return getProductsByQuery(sql);
    }

    @Override
    public List<ProductVO> getProductsOrderByDurationAsc() {
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY duration_month ASC";
        return getProductsByQuery(sql);
    }

    @Override
    public List<ProductVO> getProductsOrderByInterestDesc() {
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY interest_rate DESC";
        return getProductsByQuery(sql);
    }

    @Override
    public List<ProductVO> getProductsOrderByInterestAsc() {
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY interest_rate ASC";
        return getProductsByQuery(sql);
    }

    // 공통 빌더 메소드
    private ProductVO mapResultSetToProduct(ResultSet rs) throws SQLException {
        return ProductVO.builder()
                .productId(rs.getInt("product_id"))
                .name(rs.getString("name"))
                .type(rs.getString("type"))
                .durationMonth(rs.getInt("duration_month"))
                .interestRate(rs.getBigDecimal("interest_rate"))
                .build();
    }

    // 공통 쿼리 처리 메소드
    private List<ProductVO> getProductsByQuery(String sql) {
        List<ProductVO> products = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
