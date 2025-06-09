package app;

import dao.AccountDaoImpl;
import service.transactionList.TransactionListService;
import service.transactionList.TransactionListServiceImpl;
import dao.TransactionDao;
import dao.TransactionDaoImpl;
import domain.TransactionVO;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransactionHistoryApp {
    private final TransactionListService service;
    private final Scanner scanner = new Scanner(System.in);

    public TransactionHistoryApp(TransactionListService service) {
        this.service = service;
    }

    // 최근 거래 내역 출력 (최신 n건)
    private void printRecentTransactions(int userId, int n) {
        List<TransactionVO> list = service.getRecentTransactionsByUserId(userId, n);
        list.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));

        System.out.printf("=== 최근 거래 내역 (최신 %d건) ===\n", n);
        printTransactions(list);
        System.out.println();
    }

    // 정렬 메뉴를 반복하며 처리
    private void printSortMenuAndHandle(int userId) {
        int currentLimit = 10; // 기본 조회 개수

        while (true) {
            System.out.println("1. 금액 (기본: 많은순)");
            System.out.println("2. 메모 (기본: 내림차순)");
            System.out.println("3. 날짜 (기본: 최근순)");
            System.out.println("m. 더 많은 내역 조회");
            System.out.println("q. 종료");
            System.out.print("선택 : ");

            String input = scanner.nextLine().trim();

            if ("q".equalsIgnoreCase(input)) {
                System.out.println("정렬 메뉴를 종료합니다.");
                return;
            } else if ("m".equalsIgnoreCase(input)) {
                System.out.print("조회할 거래 내역 개수 입력: ");
                try {
                    int newN = Integer.parseInt(scanner.nextLine().trim());
                    if (newN <= 0) {
                        System.out.println("양수만 입력 가능합니다.");
                        continue;
                    }
                    currentLimit = newN;
                    printRecentTransactions(userId, currentLimit);  // 더 많은 내역 출력
                    continue;
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 올바르게 입력하세요.");
                    continue;
                }
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 다시 시도하세요.");
                continue;
            }

            String sortBy = null;
            switch (choice) {
                case 1: sortBy = "amount"; break;
                case 2: sortBy = "memo"; break;
                case 3: sortBy = "timestamp"; break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                    continue;
            }

            // 기본 정렬 방향 설정
            boolean defaultAsc;
            switch (sortBy) {
                case "amount": // 금액: 많은순 = 내림차순 (false)
                case "memo":   // 메모: 내림차순 (false)
                case "timestamp": // 날짜: 최근순 = 내림차순 (false)
                    defaultAsc = false;
                    break;
                default:
                    defaultAsc = true;
            }

            // 정렬 방향 선택 유도
            String orderPrompt = "";
            boolean ascDefault = true; // 기본값

            switch (sortBy) {
                case "amount":
                    orderPrompt = "정렬 방향 선택 (1. 많은순 2. 적은순): ";
                    ascDefault = false;  // 많은순 = 내림차순(false)
                    break;
                case "timestamp":
                    orderPrompt = "정렬 방향 선택 (1. 최신순 2. 오래된순): ";
                    ascDefault = false;  // 최신순 = 내림차순(false)
                    break;
                case "memo":
                    orderPrompt = "정렬 방향 선택 (1. 가나다순 2. 역순): ";
                    ascDefault = true;   // 가나다순 = 오름차순(true)
                    break;
                default:
                    orderPrompt = "정렬 방향 선택 (1. 오름차순 2. 내림차순): ";
                    ascDefault = true;
            }

            System.out.print(orderPrompt);
            String orderInput = scanner.nextLine().trim();
            boolean asc;

            if ("1".equals(orderInput)) {
                // 각 옵션에 따라 1번이 무조건 asc 또는 desc가 아니므로 아래처럼 맞춰줌
                switch (sortBy) {
                    case "amount":    asc = false; break; // 많은순 = 내림차순
                    case "timestamp": asc = false; break; // 최신순 = 내림차순
                    case "memo":      asc = true;  break; // 가나다순 = 오름차순
                    default:          asc = true;  break;
                }
            } else if ("2".equals(orderInput)) {
                switch (sortBy) {
                    case "amount":    asc = true;  break; // 적은순 = 오름차순
                    case "timestamp": asc = true;  break; // 오래된순 = 오름차순
                    case "memo":      asc = false; break; // 역순 = 내림차순
                    default:          asc = false; break;
                }
            } else {
                System.out.println("입력 없거나 잘못된 값, 기본값으로 진행합니다.");
                asc = ascDefault;
            }

            List<TransactionVO> sortedList = service.getTransactionsByUserIdSorted(userId, sortBy, asc);

            System.out.printf("=== %s %s 정렬 결과 ===\n", sortBy, asc ? "오름차순" : "내림차순");
            printTransactions(sortedList);
            System.out.println();
        }
    }


    private boolean defaultAscDesc(String sortBy) {
        // 기본 방향: 금액, 날짜, 메모는 모두 내림차순(false 리턴)
        switch (sortBy) {
            case "amount": // 금액 큰 순서 (내림차순)
            case "timestamp": // 최근 날짜 (내림차순)
            case "memo": // 메모 내림차순
                return false;
            default:
                return true;
        }
    }

    private void printTransactions(List<TransactionVO> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("거래 내역이 없습니다.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (TransactionVO tx : transactions) {
            String date = tx.getTimestamp().format(formatter);
            String name = tx.getCounterpartyName();
            BigDecimal amount = tx.getAmount();
            String memo = tx.getMemo() == null ? "" : tx.getMemo();
            String accountNum = tx.getCounterpartyAccountNumber();
            String sign = "?";
            if ("출금".equals(tx.getTransactionType())) sign = "-";
            else if ("입금".equals(tx.getTransactionType())) sign = "+";

            System.out.printf("[%s] %s (%s%s) (%s) (%s)\n", date, name, sign, amount.toPlainString(), memo, accountNum);
        }
    }

    public void run(int userId) {
        int initialN = 10;
        printRecentTransactions(userId, initialN);
        printSortMenuAndHandle(userId);
    }

    private void exit() {
        scanner.close();
//        System.out.println("프로그램 종료");
//        System.exit(0);
    }

    public static void main(String[] args) {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        TransactionDao transactionDao = new TransactionDaoImpl(accountDao);
        TransactionListService service = new TransactionListServiceImpl(transactionDao);

        TransactionHistoryApp app = new TransactionHistoryApp(service);
        app.run(1);
    }
}
