package util;

public class ValidatorUtil {

    // 아이디: 영문 대소문자 + 숫자, 4~14자
    public static boolean isValidLoginId(String id){
        return id.matches("^[a-zA-Z0-9]{4,14}$");
    }


    // 비밀번호: 영문 + 숫자 + 특수문자 각 1개 이상, 8~20자
    public static boolean isValidPassword(String pw) {
        return pw.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$");
    }

    // 전화번호: 010-XXXX-XXXX 형식
    public static boolean isValidPhone(String phone) {
        return phone.matches("^010-\\d{4}-\\d{4}$");
    }

    // 주민번호: YYMMDD + 7자리, 성별코드(1~4)
    public static boolean isValidSSN(String ssn) {
        return ssn.matches("^\\d{6}[1-4]\\d{6}$");
    }
}
