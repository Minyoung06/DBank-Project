package service.productList;

import common.Session;
import lombok.RequiredArgsConstructor;
import dao.UserProductDao;
import dao.UserProductDaoImpl;
import domain.UserProductVO;

import java.util.List;


@RequiredArgsConstructor
public class ProductListServiceImpl implements ProductListService {
    private final UserProductDao dao = new UserProductDaoImpl();

    private boolean sortByName = false; // true: 상품명, false: 만기일
    private boolean ascending = true;   // true: asc, false: desc


    public List<UserProductVO> getSortedProductList(String sortBy, boolean ascending) {
        int userId = Session.getUserId();
        if (userId == -1) return List.of();

        return switch (sortBy.toLowerCase()){
            case "productname" -> ascending
                    ? dao.findAllOrderByProductNameAsc(userId)
                    : dao.findAllOrderByProductNameDesc(userId);
            case "enddate" -> ascending
                    ? dao.findAllOrderByEndDateAsc(userId)
                    : dao.findAllOrderByEndDateDesc(userId);
            default -> dao.findAllOrderByEndDateDesc(userId);
        };
    }

}
