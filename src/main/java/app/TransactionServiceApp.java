package app;

import service.transaction.TransactionService;
import dao.TransactionDaoImpl;
import dao.AccountDaoImpl;
import common.Session;
import dao.UserDao;
import dao.UserDaoImpl;
import domain.UserVO;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransactionServiceApp {
    private final TransactionService transactionService;

    public TransactionServiceApp() {
        // Service에 DAO 구현체 주입
        this.transactionService = new TransactionService(
                new AccountDaoImpl(),
                new TransactionDaoImpl(new AccountDaoImpl())
        );
    }

    public void start(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("\n=== 계좌 이체 ===");
            System.out.print("- 받는 사람 계좌번호(’-’ 없이, q: 메뉴로) : ");
            String toAccountNumber = sc.nextLine().trim();
            if (toAccountNumber.equalsIgnoreCase("q")) {
                // 로그인 성공 메뉴 로직 호출
                break;
            }
            // 금액 입력 및 포맷 체크
            System.out.print("- 금액: ");
            BigDecimal amount;
            try {
                amount = new BigDecimal(sc.nextLine().trim());
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("[입력 오류] 금액은 0보다 커야 합니다.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("[입력 오류] 금액은 숫자만 입력해주세요.");
                continue;
            }

            // 메모
            System.out.print("- 메모: ");
            String memo = sc.nextLine().trim();

            // 실제 이체 수행
            boolean success = transactionService.transferByAccountNumber(
                    toAccountNumber, amount, memo
            );
            System.out.println(success ? "[이체 성공]" : "[이체 실패]");

        }
    }


}
