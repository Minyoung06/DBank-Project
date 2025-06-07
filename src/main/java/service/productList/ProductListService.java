package service.productList;

import domain.UserProductVO;

import java.util.List;

public interface ProductListService {
    List<UserProductVO> getSortedProductList(String sortBy, boolean asc);

}
