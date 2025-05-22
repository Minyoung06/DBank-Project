package product.dao;

import product.domain.ProductVO;

import java.util.List;

public interface ProductDao {

    // 추가
    void insertProduct(ProductVO product);

    // 수정
    void updateProduct(ProductVO product);

    // 삭제
    void deleteProduct(int productId);

    // 조회 단건
    ProductVO getProductById(int productId);

    // 조회 전체
    List<ProductVO> getAllProducts();

    // 만기 긴 순 (내림차순)
    List<ProductVO> getProductsOrderByDurationDesc();

    // 만기 짧은 순 (오름차순)
    List<ProductVO> getProductsOrderByDurationAsc();

    // 이자율 높은 순 (내림차순)
    List<ProductVO> getProductsOrderByInterestDesc();

    // 이자율 낮은 순 (오름차순)
    List<ProductVO> getProductsOrderByInterestAsc();


}
