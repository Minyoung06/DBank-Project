package app;

import common.Session;
import lombok.RequiredArgsConstructor;
import service.productList.ProductListService;
import service.productList.ProductListServiceImpl;
import domain.UserVO;
import domain.UserProductVO;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class ProductListApp {
    private final ProductListService service;
    private final Scanner sc = new Scanner(System.in);

    private int limit = 10;
    private int totalSize = 0;
    private List<UserProductVO> currentList;

    private void printProductList(List<UserProductVO> list, int limit) {
        int count = 0;
        for (UserProductVO productVO : list) {
            System.out.printf("- %s / 가입일: %s / 만기: %s\n",
                    productVO.getProduct_name(),
                    productVO.getStart_date(),
                    productVO.getEnd_date());
            count++;
            if (count >= limit) break;
        }
        System.out.println();
    }

    private void handleMoreOption() {
        System.out.print("조회할 상품 개수 입력: ");
        try {
            int newLimit = Integer.parseInt(sc.nextLine().trim());
            if (newLimit <= 0) {
                System.out.println("양수만 입력 가능합니다.");
                return;
            }
            limit = Math.min(newLimit, totalSize); // 최대 totalSize 초과 금지
        } catch (NumberFormatException e) {
            System.out.println("숫자를 올바르게 입력하세요.");
        }
    }

    private void handleSortOption(String input) {
        String sortBy = input.equals("1") ? "productName" : "endDate";

        System.out.print("정렬 방향 선택 (1. 오름차순 / 2. 내림차순): ");
        String orderInput = sc.nextLine().trim();
        boolean asc = "1".equals(orderInput);

        currentList = service.getSortedProductList(sortBy, asc); // 최신 리스트 갱신
        totalSize = currentList.size();                          // 상품 총 개수 갱신
        limit = getInitialLimit(totalSize);                      // limit 초기화

        System.out.printf("\n=== 정렬: %s (%s) ===\n",
                sortBy.equals("productName") ? "상품명" : "만기일",
                asc ? "오름차순" : "내림차순");

        printProductList(currentList, limit);
    }

    private int getInitialLimit(int totalSize) {
        return Math.min(10, totalSize);
    }

    public void run() {
        if(!Session.isLoggedIn()){    // 비로그인
            System.out.println("로그인 후 이용 가능합니다.");
            return;
        }

        currentList = service.getSortedProductList("endDate", true);
        totalSize = currentList.size();
        limit = getInitialLimit(totalSize);

        System.out.println("\n=== 내 금융 상품 목록 ===");
        if (currentList.isEmpty()) {
            System.out.println("가입한 상품이 없습니다.");
            return;
        }
        printProductList(currentList, limit);

        while(true){
            System.out.println("1. 상품명 정렬");
            System.out.println("2. 만기일 정렬");
            System.out.println("m. 더 많은 항목 조회");
            System.out.println("q. 종료");
            System.out.print("선택: ");
            String input = sc.nextLine().trim();

            if("q".equalsIgnoreCase(input)){
                System.out.println("메인 메뉴로 돌아갑니다.");
                return;
            }

            if ("m".equalsIgnoreCase(input)) {
                handleMoreOption();
                printProductList(currentList, limit);
                continue;
            }

            if (input.equals("1") || input.equals("2")) {
                handleSortOption(input);
                continue;
            }

            System.out.println("잘못된 입력입니다. 다시 선택하세요.");
        }
    }
}
