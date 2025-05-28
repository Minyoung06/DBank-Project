package app.user;

import account.dao.AccountDao;
import account.dao.AccountDaoImpl;
import account.domain.AccountVO;
import common.Session;
import service.user.UserService;

import user.dao.UserDao;
import util.ValidatorUtil;

import java.util.Scanner;
import java.util.function.Predicate;

import static common.ErrorMessage.*;

public class UserApp {
    private final UserService userService;
    private final Scanner scanner;

    public UserApp(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println("\n===1. 메인===");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");

        int choice = inputInt("선택: ");

        switch (choice){
            case 1 -> login();
            case 2 -> register();
            case 3 -> {
                System.out.println("프로그램 종료");
                System.exit(0);
            }
            default ->System.out.println("잘못된 입력입니다.");
        }
    }

    private void register() {
        System.out.println("\n===2. 회원가입===");
        String name = input("이름: ");
        boolean success = false;

        while(!success) {
            String loginId = validatedInput(
                    "로그인 ID:",
                    INVALID_LOGIN_ID,
                    ValidatorUtil::isValidLoginId
            );

            String password = validatedInput(
                    "비밀번호: ",
                    INVALID_PASSWORD,
                    ValidatorUtil::isValidPassword
            );

            String phone = validatedInput(
                    "전화번호 (010-XXXX-XXXX): ",
                    INVALID_PHONE,
                    ValidatorUtil::isValidPhone
            );
            String address = input("주소: ");
            String ssn = validatedInput(
                    "주민번호 (13자리): ",
                    INVALID_SSN,
                    ValidatorUtil::isValidSSN
            );

            success = userService.register(name, loginId, password, phone, address, ssn);
            if (success) {
                System.out.println("[가입완료]");
                login();
            } else {
                System.out.println("회원가입에 실패했습니다. 다시 시도해주세요.\n");
            }
        }
    }

    private void login() {
        System.out.println("\n===3. 로그인===");
        int attempts = 0;
        while(attempts<3) {
            String loginId = input("로그인 ID: ");
            String password = input("비밀번호: ");

            if (userService.login(loginId, password)) {
                System.out.println("로그인 성공");
                showUserMenu();
                return;
            } else {
                System.out.println("[로그인 실패]");
                attempts++;
            }
        }
        System.out.println("로그인 3회 실패. 처음화면으로 돌아갑니다.");
    }

    private void showUserMenu() {
        while (Session.isLoggedIn()) {
            AccountVO loginAccount = userService.getAccountByUserId(Session.getUser().getUserId());
            System.out.println("\n===4. 로그인 성공===");
            System.out.println(loginAccount.getAccountNumber()+ " (계좌)");
            System.out.println("현재 잔액: "+loginAccount.getBalance()+"원");
            System.out.println("1. 계좌 상세 조회 및 거래 내역");
            System.out.println("2. 계좌 이체");
            System.out.println("3. 금융 상품 가입");
            System.out.println("4. 내 상품 목록");
            System.out.println("5. 로그아웃");
            System.out.println("6. 종료");

            switch (inputInt("선택: ")) {
                case 1 -> System.out.println("계좌 상세 조회 기능");
                case 2 -> System.out.println("계좌 이체 기능");
                case 3 -> System.out.println("금융 상품 가입 기능");
                case 4 -> System.out.println("내 상품 목록 기능");
                case 5 -> {
                    Session.logout();
                    System.out.println("로그아웃 되었습니다.");
                    return;
                }
                case 6 -> {
                    Session.logout();
                    System.out.println("로그아웃 되었습니다.");
                    System.out.println("프로그램 종료");
                    System.exit(0);
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private int inputInt(String message) {
        while(true){
            try {
                return Integer.parseInt(input(message));
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    private String input(String message){
        System.out.print(message);
        return scanner.nextLine();
    }

    private String validatedInput(String label, String errorMessage, Predicate<String> validator) {
        while (true) {
            String value = input(label);
            if (validator.test(value)) return value;
            System.out.println(errorMessage);
        }
    }

}
