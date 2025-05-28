package transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionVO {
    private Integer transaction_id;
    private Integer send_account_id;
    private Integer receiver_account_id;
    private BigDecimal amount;
    private String memo;
    private LocalDateTime timestamp;


    private String transactionType; // 입금 or 출금
    private String counterpartyName;
    private String counterpartyAccountNumber;
}
