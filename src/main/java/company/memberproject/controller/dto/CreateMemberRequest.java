package company.memberproject.controller.dto;


import company.memberproject.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor

public class CreateMemberRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    private final String userId;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private final String nickName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private final String phoneNumber;

    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private final String password;


    public Member createMember(String secretPw){
        return new Member(userId, name, nickName, phoneNumber, email, secretPw);
    }

}
