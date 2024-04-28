package company.memberproject.utility.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    NOT_MEMBER(HttpStatus.NOT_FOUND, "일치하는 회원 아이디가 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 Email 입니다."),
    DUPLICATE_USERID(HttpStatus.BAD_REQUEST, "중복된 UserId 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 NickName 입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
