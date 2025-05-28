package util;

import java.util.Random;

public class AccountUtil {
    public static String generateAccountNumber() {
        long timestamp = System.currentTimeMillis() % 1_000_000L;  // 앞 6자리
        int random = new Random().nextInt(1_000_000);              // 뒤 6자리
        return String.format("%06d-%06d", timestamp, random);
    }
}
