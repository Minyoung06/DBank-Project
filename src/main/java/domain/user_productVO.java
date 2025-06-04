package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class user_productVO {
    private int user_product_id;
    private int user_id;
    private int product_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private String status; // '가입', '해지'

    private String product_name;
}