package app;

import transaction.TransactionService;
import transaction.dao.TransactionDao;
import transaction.dao.TransactionDaoImpl;
import transaction.domain.TransactionVO;
import account.dao.AccountDao;
import account.dao.AccountDaoImpl;
import account.domain.AccountVO;
import common.Session;
import user.dao.UserDao;
import user.dao.UserDaoImpl;
import user.domain.UserVO;

import java.math.BigDecimal;
import java.util.List;
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
            System.out.println("\n=== 6. 계좌 이체 ===");
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

            // 연속 이체 or 메뉴 복귀
            System.out.print("→ 계속 이체하려면 Enter, 메뉴로 돌아가려면 q 입력: ");
            String next = sc.nextLine().trim();
            if (next.equalsIgnoreCase("q")) {
                break;
            }
            // Enter 입력 시 루프가 다시 돌아가며 새 이체를 진행
        }
        // 메뉴로 돌아가면 여기에 로그인 성공 메뉴 로직 호출
    }

    public static void main(String[] args) throws Exception {
        // 1) 로그인
        UserDao userDao = new UserDaoImpl();
        UserVO user = userDao.login("Hong01", "pw1234!");
        if (user == null) {
            System.err.println("로그인 실패");
            return;
        }
        Session.login(user);
        System.out.println("▶ 로그인 성공: " + Session.getUserName());

        // 2) 이체 콘솔 시작
        new TransactionServiceApp().start();
    }
}
