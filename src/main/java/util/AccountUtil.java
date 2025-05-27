package util;

import java.util.Random;

public class AccountUtil {
    public static String generateAccountNumber() {
        long timestamp = System.currentTimeMillis() % 10000000000L;  // 10자리
        int random =new Random().nextInt(9000)+1000;
        return String.format("%010d%4d", timestamp, random);
    }
}
