package user.domain;

import account.domain.AccountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import user_product.domain.user_productVO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private int userId;
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String ssn;

    // 관계 추가
//    private int accountId;                      // 1:1 관계
    private List<user_productVO> userProducts;       // 1:N 관계
}
