package user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
