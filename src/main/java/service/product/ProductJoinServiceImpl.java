package service.product;

import dao.ProductDao;
import dao.ProductDaoImpl;
import dao.UserProductDao;
import dao.UserProductDaoImpl;
import domain.ProductVO;
import common.Session;
import domain.user_productVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProductJoinServiceImpl implements ProductJoinService {
    private final ProductDao productDAO = new ProductDaoImpl();
    private final UserProductDao userProductDAO = new UserProductDaoImpl();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayProductList() {
        List<ProductVO> products = productDAO.getAllProducts();

        System.out.println("\n===== 상품 목록 =====");
        for (ProductVO p : products) {
            System.out.printf("상품번호: %d | 이름: %s | 유형: %s | 기간: %d개월 | 금리: %.2f%%\n",
                    p.getProductId(), p.getName(), p.getType(),
                    p.getDurationMonth(), p.getInterestRate());
        }
        System.out.println("=====================");
    }

    @Override
    public void JoinToProduct(int productId) {
        if (!Session.isLoggedIn()) {
            System.out.println("로그인 후 이용 가능한 서비스입니다.");
            return;
        }

        ProductVO product = productDAO.getProductById(productId);

        if (product == null) {
            System.out.println("해당 상품은 존재하지 않습니다.");
            return;
        }

        user_productVO userProduct = new user_productVO();
        userProduct.setUser_id(Session.getUserId());
        userProduct.setProduct_id(product.getProductId());

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(product.getDurationMonth());

        userProduct.setStart_date(startDate);
        userProduct.setEnd_date(endDate);
        userProduct.setStatus("가입");

        int result = userProductDAO.insert(userProduct);

        if (result > 0) {
            System.out.println("상품 가입이 완료되었습니다.");
        } else {
            System.out.println("상품 가입에 실패했습니다.");
        }
    }
}
