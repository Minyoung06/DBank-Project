package user_product.dao;

import user_product.domain.user_productVO;

import java.util.List;
import java.util.Optional;

public interface user_productDao {
    // 1. 가입 정보 등록
    void insert(user_productVO product);

    // 2. 가입 정보 수정 (ex. 상태 변경, 종료일 수정 등)
    void update(user_productVO product);

    // 3. 특정 가입 내역 삭제
    void deleteById(int userProductId);

    // 4. 특정 유저가 가입한 모든 상품 조회
    List<user_productVO> findByUserId(int userId);

    // 5. 상품명 오름차순 정렬
    List<user_productVO> findAllOrderByProductNameAsc();

    // 6. 상품명 내림차순 정렬
    List<user_productVO> findAllOrderByProductNameDesc();

    // 7. 만기일 오름차순 정렬
    List<user_productVO> findAllOrderByEndDateAsc();

    // 8. 만기일 내림차순 정렬
    List<user_productVO> findAllOrderByEndDateDesc();

    // user가 가입한 상품들 조회
    List<user_productVO> findJoinedProductByUserId(int userId);
}