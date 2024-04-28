package company.memberproject.service;

import company.memberproject.controller.dto.CreateMemberRequest;
import company.memberproject.domain.Member;
import company.memberproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public Member join(CreateMemberRequest createMemberReq) {
        log.info("Attempting to register a new member: {}", createMemberReq.getUserId());

        String userId = createMemberReq.getUserId();
        String nickName = createMemberReq.getNickName();
        String email = createMemberReq.getEmail();

        //TODO 중복체크

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secretPw = encoder.encode(createMemberReq.getPassword());
        Member member = createMemberReq.createMember(secretPw);

        memberRepository.save(member);

        log.info("Member registration successful for: {}", createMemberReq.getUserId());
        return member;
    }
}