package company.memberproject.controller;

import company.memberproject.controller.dto.CreateMemberRequest;
import company.memberproject.domain.Member;
import company.memberproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;
    private static final String PAGE_SIZE = "10";

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid CreateMemberRequest createMemberRequest,
                                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return validationError(bindingResult.getFieldErrors());
        }
        log.info("Attempting to join a new member with userID: {}", createMemberRequest.getUserId());

        Member member = memberService.join(createMemberRequest);
        log.info("Member join completed for userID: {}", createMemberRequest.getUserId());
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }
