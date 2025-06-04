package dao;

import domain.UserProductVO;

import java.util.List;

public interface UserProductDao {
    // 1. 가입 정보 등록
    int insert(UserProductVO product);

    // 2. 가입 정보 수정 (ex. 상태 변경, 종료일 수정 등)
    void update(UserProductVO product);

    // 3. 특정 가입 내역 삭제
    void deleteById(int userProductId);

    // 4. 특정 유저가 가입한 모든 상품 조회
    List<UserProductVO> findByUserId(int userId);

    // 5. 상품명 오름차순 정렬
    List<UserProductVO> findAllOrderByProductNameAsc(int userId);

    // 6. 상품명 내림차순 정렬
    List<UserProductVO> findAllOrderByProductNameDesc(int userId);

    // 7. 만기일 오름차순 정렬
    List<UserProductVO> findAllOrderByEndDateAsc(int userId);

    // 8. 만기일 내림차순 정렬
    List<UserProductVO> findAllOrderByEndDateDesc(int userId);

    // user가 가입한 상품들 조회
    List<UserProductVO> findJoinedProductByUserId(int userId);
}