package app;

import dao.AccountDaoImpl;
import dao.TransactionDaoImpl;
import service.productList.ProductListService;
import service.productList.ProductListServiceImpl;
import service.transactionList.TransactionListService;
import service.transactionList.TransactionListServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import dao.UserDaoImpl;

public class MainAppTest {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserDaoImpl(),new AccountDaoImpl());

        ProductJoinApp productJoinApp = new ProductJoinApp();
        ProductListService productListService = new ProductListServiceImpl();
        ProductListApp productListApp = new ProductListApp(productListService);
        TransactionServiceApp transactionServiceApp = new TransactionServiceApp();
        TransactionListService transactionListService = new TransactionListServiceImpl(new TransactionDaoImpl(new AccountDaoImpl()));
        TransactionHistoryApp transactionHistoryApp = new TransactionHistoryApp(transactionListService);


        MainApp userApp = new MainApp(userService, productJoinApp, productListApp, transactionServiceApp, transactionHistoryApp);

        while(true){
            userApp.start();
        }

    }
}
