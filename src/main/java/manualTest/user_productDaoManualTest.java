package manualTest;

import dao.UserProductDao;
import dao.UserProductDaoImpl;
import domain.UserProductVO;

import java.time.LocalDate;
import java.util.List;

public class user_productDaoManualTest {
    public static void main(String[] args) {
        UserProductDao dao = new UserProductDaoImpl();

        //insert test
        UserProductVO newUserProduct = UserProductVO.builder()
                .user_id(1)
                .product_id(2)
                .start_date(LocalDate.now())
                .end_date(LocalDate.now().plusMonths(6))
                .status("가입")
                .build();

        dao.insert(newUserProduct);
        System.out.println("✅ 상품 가입 등록 완료");
        System.out.println("======================================");

        //update test
        UserProductVO vo = UserProductVO.builder()
                .user_product_id(1)
                .user_id(1)
                .product_id(2)
                .start_date(LocalDate.of(2024, 10, 1))  // 24.05.01 -> 24.10.01
                .end_date(LocalDate.of(2024, 11, 30))   // 25.05.01 -> 24.11.30
                .status("해지")     // '가입' → '해지'
                .build();

        dao.update(vo);
        System.out.println("✅ 가입 정보 업데이트 완료");
        System.out.println("======================================");

        //delete test
        dao.deleteById(2); // user_product_id = 2인 가입 정보 삭제
        System.out.println("✅ 가입 정보 삭제 완료");
        System.out.println("======================================");

        //findByUserId test
        int userId = 1;
        List<UserProductVO> list = dao.findByUserId(userId);

        System.out.println("가입 상품 조회 결과:");
        if (list.isEmpty()) {
            System.out.println("해당 유저의 가입 내역이 없습니다.");
        } else {
            for (UserProductVO up : list) {
                System.out.printf("가입번호: %d | 상품ID: %d | 시작일: %s | 만기일: %s | 상태: %s\n",
                        up.getUser_product_id(),
                        up.getProduct_id(),
                        up.getStart_date(),
                        up.getEnd_date(),
                        up.getStatus());
            }
        }
        System.out.println("======================================");

        // findAllOrderByProductNameAsc test
        List<UserProductVO> sortedList = dao.findAllOrderByProductNameAsc(userId);

        System.out.println("상품명 오름차순 정렬 결과:");
        if (sortedList.isEmpty()) {
            System.out.println("조회 결과가 없습니다.");
        } else {
            for (UserProductVO up : sortedList) {
                System.out.printf("상품명: %s | 가입일: %s | 만기일: %s | 상태: %s\n",
                        up.getProduct_name(),
                        up.getStart_date(),
                        up.getEnd_date(),
                        up.getStatus());
            }
        }
        System.out.println("======================================");

        //findAllOrderByProductNameDesc test
        List<UserProductVO> descList = dao.findAllOrderByProductNameDesc(userId);

        System.out.println("상품명 내림차순 정렬 결과:");
        if (descList.isEmpty()) {
            System.out.println("조회 결과가 없습니다.");
        } else {
            for (UserProductVO up : descList) {
                System.out.printf("상품명: %s | 가입일: %s | 만기일: %s | 상태: %s\n",
                        up.getProduct_name(),
                        up.getStart_date(),
                        up.getEnd_date(),
                        up.getStatus());
            }
        }
        System.out.println("======================================");

        //findAllOrderByEndDateAsc test
        List<UserProductVO> dateList = dao.findAllOrderByEndDateAsc(userId);

        System.out.println("만기일 오름차순 정렬 결과:");
        for (UserProductVO up : dateList) {
            System.out.printf("상품명: %s | 만기일: %s | 상태: %s\n",
                    up.getProduct_name(),
                    up.getEnd_date(),
                    up.getStatus());
        }
        System.out.println("======================================");

        // findAllOrderByEndDateDesc test
        List<UserProductVO> dateDescList = dao.findAllOrderByEndDateDesc(userId);

        System.out.println("만기일 내림차순 정렬 결과:");
        for (UserProductVO up : dateDescList) {
            System.out.printf("상품명: %s | 만기일: %s | 상태: %s\n",
                    up.getProduct_name(),
                    up.getEnd_date(),
                    up.getStatus());
        }
        System.out.println("======================================");

        // findJoinedProductByUserId test, id:1
        List<UserProductVO> joinedList = dao.findJoinedProductByUserId(1);

        System.out.println("가입한 상품 목록:");
        for (UserProductVO up : joinedList) {
            System.out.printf("상품명: %s | 가입일: %s | 만기일: %s | 상태: %s\n",
                    up.getProduct_name(),
                    up.getStart_date(),
                    up.getEnd_date(),
                    up.getStatus());
        }
    }
}
