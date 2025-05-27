package common;

public class ErrorMessage {
    public static final String INVALID_LOGIN_ID ="아이디는 영문/숫자 조합이어야 하며 4~14자입니다.";
    public static final String INVALID_PASSWORD = "비밀번호는 영문+숫자+특수문자 조합, 8~20자여야 합니다.";
    public static final String INVALID_PHONE = "전화번호 형식이 올바르지 않습니다.";
    public static final String INVALID_SSN = "주민번호 형식이 올바르지 않습니다.";

    public static final String LOGIN_FAILED = "[로그인 실패]";
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAILED_MAX = "로그인 3회 실패. 처음화면으로 돌아갑니다.";
    public static final String REGISTER_SUCCESS = "[가입 완료]";
}
