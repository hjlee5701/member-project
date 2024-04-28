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

    private final String name;

    private final String userId;

    private final String nickName;

    private final String phoneNumber;

    private final String email;

    private final String password;


    public Member createMember(String secretPw){
        return new Member(userId, name, nickName, phoneNumber, email, secretPw);
    }

}
