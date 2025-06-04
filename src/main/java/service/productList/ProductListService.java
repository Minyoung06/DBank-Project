package service.productList;

import domain.user_productVO;

import java.util.List;

public interface ProductListService {
    List<user_productVO> getSortedProductList();
    void toggleSortByName();
    void toggleSortByEndDate();
    String getCurrentSortStatus();
}
