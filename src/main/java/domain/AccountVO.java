package domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccountVO {
    private int accountId;
    private int userId;
    private double balance;
    private String accountNumber;
}
