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

    public void run() {
        if(!Session.isLoggedIn()){    // 비로그인
            System.out.println("로그인 후 이용 가능합니다.");
            return; // 현재는 그냥 종료. userService 완성 후 로그인으로 이동하도록 수정 예정
        }
        while(true){
            System.out.println("\n=== 내 금융 상품 목록 ===");
            List<UserProductVO> productList = service.getSortedProductList();

            if(productList.isEmpty()){
                System.out.println("가입한 상품이 없습니다.");
            }
            else {
                for(UserProductVO productVO : productList){
                    System.out.printf("- %s / 가입일: %s / 만기: %s\n",
                            productVO.getProduct_name(),
                            productVO.getStart_date(),
                            productVO.getEnd_date()
                    );
                }
            }
            System.out.println("\n[정렬 기준: " + service.getCurrentSortStatus() + "]");
            System.out.println("1. 상품명 정렬");
            System.out.println("2. 만기일 정렬");
            System.out.println("q. 종료");
            System.out.print("선택: ");
            String input = sc.nextLine().trim();

            switch(input){
                case "1" -> service.toggleSortByName();
                case "2"-> service.toggleSortByEndDate();
                case "q", "Q" -> {
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    return; // 현재는 그냥 종료. 메인 메뉴 완성 후 메인으로 이동하도록 수정 예정
                }
                default -> System.out.println("잘못된 입력입니다.\n메뉴 또는 q를 입력해주세요");
            }
        }
    }
}
