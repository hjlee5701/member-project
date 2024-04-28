package company.memberproject.controller;

import company.memberproject.controller.dto.CreateMemberRequest;
import company.memberproject.controller.dto.UpdateMemberRequest;
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

    @GetMapping("/list")
    public ResponseEntity<List<Member>> getList(@RequestParam(name="page", defaultValue = "1") int page,
                                                @RequestParam(name="pageSize", defaultValue = PAGE_SIZE) int pageSize){

        log.info("Retrieving member list for page: {}, pageSize: {}", page, pageSize);

        List<Member> memberList = memberService.getList(page, pageSize);

        log.info("Members retrieved for page: {}", page);

        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable("userId") String userId,
                                    @Valid @RequestBody UpdateMemberRequest updateMemberRequest,
                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return validationError(bindingResult.getFieldErrors());
        }

        log.debug("Attempting to update member with userId: {}", userId);
        Member member = memberService.update(userId, updateMemberRequest);
        log.debug("Member update completed for userId: {}", userId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    private ResponseEntity<?> validationError(List<FieldError> fieldErrors){
        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

}