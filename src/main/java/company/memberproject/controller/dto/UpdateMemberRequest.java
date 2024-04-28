package company.memberproject.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
public class UpdateMemberRequest {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private final String nickName;

    private final String phoneNumber;

    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private final String email;

}
