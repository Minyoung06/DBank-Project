package product;

import database.JDBCUtil;
import product.dao.ProductDao;
import product.domain.ProductVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    // 연결
    Connection conn = JDBCUtil.getConnection();

    @Override
    public void insertProduct(ProductVO product) {
        String sql = "INSERT INTO product (name, type, duration_month, interest_rate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getType());
            pstmt.setInt(3, product.getDurationMonth());
            pstmt.setBigDecimal(4, product.getInterestRate());

            int count = pstmt.executeUpdate();

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

            int count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteProduct(int productId) {
        String sql= "DELETE FROM product WHERE product_id = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql))  {
            pstmt.setInt(1, productId);
            int count = pstmt.executeUpdate();
        }catch (SQLException e){
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
                    return ProductVO.builder()
                            .productId(rs.getInt("product_id"))
                            .name(rs.getString("name"))
                            .type(rs.getString("type"))
                            .durationMonth(rs.getInt("duration_month"))
                            .interestRate(rs.getBigDecimal("interest_rate"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ProductVO> getAllProducts() {
        List<ProductVO> products = new ArrayList<>();
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(ProductVO.builder()
                        .productId(rs.getInt("product_id"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .durationMonth(rs.getInt("duration_month"))
                        .interestRate(rs.getBigDecimal("interest_rate"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public List<ProductVO> getProductsOrderByDurationDesc() {
        List<ProductVO> products = new ArrayList<>();
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY duration_month DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(ProductVO.builder()
                        .productId(rs.getInt("product_id"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .durationMonth(rs.getInt("duration_month"))
                        .interestRate(rs.getBigDecimal("interest_rate"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public List<ProductVO> getProductsOrderByDurationAsc() {
        List<ProductVO> products = new ArrayList<>();
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY duration_month ASC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(ProductVO.builder()
                        .productId(rs.getInt("product_id"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .durationMonth(rs.getInt("duration_month"))
                        .interestRate(rs.getBigDecimal("interest_rate"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public List<ProductVO> getProductsOrderByInterestDesc() {
        List<ProductVO> products = new ArrayList<>();
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY interest_rate DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(ProductVO.builder()
                        .productId(rs.getInt("product_id"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .durationMonth(rs.getInt("duration_month"))
                        .interestRate(rs.getBigDecimal("interest_rate"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }


    @Override
    public List<ProductVO> getProductsOrderByInterestAsc() {
        List<ProductVO> products = new ArrayList<>();
        String sql = "SELECT product_id, name, type, duration_month, interest_rate FROM product ORDER BY interest_rate ASC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(ProductVO.builder()
                        .productId(rs.getInt("product_id"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .durationMonth(rs.getInt("duration_month"))
                        .interestRate(rs.getBigDecimal("interest_rate"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

}