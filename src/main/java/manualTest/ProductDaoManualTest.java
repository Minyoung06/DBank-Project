package manualTest;

import dao.ProductDao;
import dao.ProductDaoImpl;
import domain.ProductVO;

import java.math.BigDecimal;
import java.util.List;

public class ProductDaoManualTest {

    public static void main(String[] args) {
        ProductDao dao = new ProductDaoImpl();

        // 1. 추가 테스트
        ProductVO newProduct = ProductVO.builder()
                .name("테스트 상품")
                .type("자유적금")
                .durationMonth(24)
                .interestRate(new BigDecimal("4.5"))
                .build();

        dao.insertProduct(newProduct);
        System.out.println("상품 추가 완료");

        // 2. 단건 조회 테스트
        int testId = 1;  // DB에 존재하는 상품ID로 바꿔줘
        ProductVO product = dao.getProductById(testId);
        if (product != null) {
            System.out.println("단건 조회 결과: " + product);
        } else {
            System.out.println("상품이 존재하지 않습니다.");
        }

        // 3. 전체 조회 테스트
        List<ProductVO> productList = dao.getAllProducts();
        System.out.println("전체 상품 리스트:");
        for (ProductVO p : productList) {
            System.out.println(p);
        }

        // 4. 만기 긴 순 정렬 테스트
        List<ProductVO> productsByDurationDesc = dao.getProductsOrderByDurationDesc();
        System.out.println("만기 긴 순 정렬:");
        for (ProductVO p : productsByDurationDesc) {
            System.out.println(p);
        }

        // 5. 이자율 낮은 순 정렬 테스트
        List<ProductVO> productsByInterestAsc = dao.getProductsOrderByInterestAsc();
        System.out.println("이자율 낮은 순 정렬:");
        for (ProductVO p : productsByInterestAsc) {
            System.out.println(p);
        }

        // 필요하면 update, delete도 직접 호출 후 확인 가능
    }
}
