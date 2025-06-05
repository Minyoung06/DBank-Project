package app;

import common.Session;
import domain.UserVO;
import org.junit.jupiter.api.Test;
import service.productList.ProductListService;
import service.productList.ProductListServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class ProductListAppTest {

    public static void main(String[] args) {
        // 로그인 되어 있다고 가정 - 이후 삭제 예정
        UserVO testUser = UserVO.builder()
                .userId(1)
                .name("홍길동")
                .build();
        Session.login(testUser);

        System.out.println("현재 로그인된 user_id: " + Session.getUserId());

        ProductListService productService = new ProductListServiceImpl();
        ProductListApp productListApp = new ProductListApp(productService);
        productListApp.run();
    }
}