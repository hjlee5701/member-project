package company.memberproject.utility.exception;

import org.springframework.http.HttpStatus;


public class MemberException extends RuntimeException{
    private final MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode){
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }

    public HttpStatus getHttpStatus(){
        return memberErrorCode.getHttpStatus();
    }
}
