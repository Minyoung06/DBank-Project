package service.productList;

import common.Session;
import lombok.RequiredArgsConstructor;
import dao.user_productDao;
import dao.user_productDaoImpl;
import domain.user_productVO;

import java.util.List;


@RequiredArgsConstructor
public class ProductListServiceImpl implements ProductListService {
    private final user_productDao dao = new user_productDaoImpl();

    private boolean sortByName = false; // true: 상품명, false: 만기일
    private boolean ascending = true;   // true: asc, false: desc


    @Override
    public List<user_productVO> getSortedProductList() {
        int userId = Session.getUserId();
        if (userId == -1) return List.of();

        if (sortByName) {
            return ascending
                    ? dao.findAllOrderByProductNameAsc(userId)
                    : dao.findAllOrderByProductNameDesc(userId);
        } else {
            return ascending
                    ? dao.findAllOrderByEndDateAsc(userId)
                    : dao.findAllOrderByEndDateDesc(userId);
        }
    }

    @Override
    public void toggleSortByName() {
        if (sortByName) ascending = !ascending;
        else {
            sortByName = true;
            ascending = true;
        }
    }

    @Override
    public void toggleSortByEndDate() {
        if (!sortByName) ascending = !ascending;
        else {
            sortByName = false;
            ascending = true;
        }
    }

    @Override
    public String getCurrentSortStatus() {
        String type = sortByName ? "상품명순" : "만기일순";
        String order = ascending ? "오름차순" : "내림차순";
        return type + " (" + order + ")";
    }
}
