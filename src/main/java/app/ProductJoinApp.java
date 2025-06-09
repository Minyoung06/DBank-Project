package app;

import service.product.ProductJoinService;
import service.product.ProductJoinServiceImpl;

import java.util.Scanner;

public class ProductJoinApp {
    private final ProductJoinService productJoinService = new ProductJoinServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            // 1. 상품 목록 출력
            productJoinService.displayProductList();

            // 2. 사용자 입력 안내
            System.out.print("가입할 상품 번호를 입력하세요 (종료: q): ");
            String input = scanner.nextLine().trim();

            // 3. 종료 조건
            if (input.equalsIgnoreCase("q")) {
                System.out.println("상품 가입을 종료합니다.");
                break;  // 루프 종료
            }

            // 4. 숫자인 경우 상품 가입 시도
            try {
                int productId = Integer.parseInt(input);
                productJoinService.JoinToProduct(productId);  // 상품 가입 처리
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 상품 번호 또는 'q'를 입력해주세요.");
            }

            System.out.println(); // 줄바꿈으로 구분
        }
    }
}
