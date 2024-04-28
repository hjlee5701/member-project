package company.memberproject.service;

import company.memberproject.controller.dto.CreateMemberRequest;
import company.memberproject.controller.dto.UpdateMemberRequest;
import company.memberproject.domain.Member;
import company.memberproject.repository.MemberRepository;
import company.memberproject.utility.exception.MemberErrorCode;
import company.memberproject.utility.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private boolean isPresentUserId(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }
    private boolean isPresentNickName(String nickName) {
        return memberRepository.findByNickName(nickName).isPresent();
    }

    private boolean isPresentEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public Member join(CreateMemberRequest createMemberReq) {
        log.info("Attempting to register a new member: {}", createMemberReq.getUserId());

        String userId = createMemberReq.getUserId();
        String nickName = createMemberReq.getNickName();
        String email = createMemberReq.getEmail();

        if (isPresentUserId(userId)) {
            log.warn("Duplicate userID detected: {}", userId);
            throw new MemberException(MemberErrorCode.DUPLICATE_USERID);
        }

        if (isPresentNickName(nickName)) {
            log.warn("Duplicate nickname detected: {}", nickName);
            throw new MemberException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        if (isPresentEmail(email)) {
            log.warn("Duplicate email detected: {}", email);
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secretPw = encoder.encode(createMemberReq.getPassword());
        Member member = createMemberReq.createMember(secretPw);

        memberRepository.save(member);

        log.info("Member registration successful for: {}", createMemberReq.getUserId());
        return member;
    }

    @Transactional
    public List<Member> getList(int page, int pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("regAt"), Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        log.info("Retrieving member list for page: {}", page);
        Page<Member> memberPage = memberRepository.findAll(pageable);
        log.info("Members retrieved for page: {}", page);

        return memberPage.getContent();
    }

    @Transactional
    public Member update(String userId, UpdateMemberRequest updateMemberReq) {
        log.info("Updating member: {}", userId);
        Optional<Member> maybeMember = memberRepository.findByUserId(userId);

        if (!maybeMember.isPresent()) {
            log.error("No member found with userID: {}", userId);
            throw new MemberException(MemberErrorCode.NOT_MEMBER);
        }

        Member member = maybeMember.get();
        String newEmail = updateMemberReq.getEmail();
        String newPhoneNumber = updateMemberReq.getPhoneNumber();
        String newNickName = updateMemberReq.getNickName();

        if (newEmail != null && !member.getEmail().equals(newEmail) && isPresentEmail(newEmail)) {

            log.warn("Duplicate email detected during update: {}", newEmail);
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        if (newNickName != null && !member.getNickName().equals(newNickName) && isPresentNickName(newNickName)) {
            log.warn("Duplicate nickname detected during update: {}", newNickName);
            throw new MemberException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        member.updateMember(newNickName, newPhoneNumber, newEmail);
        memberRepository.save(member);
        log.info("Member update successful for: {}", userId);
        return member;
    }
}