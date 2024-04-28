package company.memberproject.service;

import company.memberproject.controller.dto.CreateMemberRequest;
import company.memberproject.controller.dto.UpdateMemberRequest;
import company.memberproject.domain.Member;
import company.memberproject.repository.MemberRepository;
import company.memberproject.utility.exception.MemberErrorCode;
import company.memberproject.utility.exception.MemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private CreateMemberRequest createMemberRequest;
    private UpdateMemberRequest updateMemberRequest;

    private Member member;

    @BeforeEach
    void setUp() {
        createMemberRequest = new CreateMemberRequest("Kim", "testUser", "happy",
                                                    "010132123", "testUser@example.com","password");

        updateMemberRequest = new UpdateMemberRequest("sad", "0109999999", "newNickName");
        member = new Member("testUser", "Kim", "happy", "0102222222", "testUser@example.com", "encryptedPassword");
    }

    @Test
    void 회원가입() {
        when(memberRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(memberRepository.findByNickName(anyString())).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member result = memberService.join(createMemberRequest);

        assertNotNull(result);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void 회원_목록_조회() {
        Page<Member> page = new PageImpl<>(Collections.singletonList(member));
        when(memberRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<Member> result = memberService.getList(1, 5);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(member, result.get(0));
    }

    @Test
    void 존재하지_않은_회원_예외() {
        when(memberRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        assertThrows(MemberException.class, () -> memberService.update("testUser", updateMemberRequest));
    }

    @Test
    void 중복_이메일_예외() {
        when(memberRepository.findByUserId(anyString())).thenReturn(Optional.of(member));
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(new Member()));

        assertThrows(MemberException.class, () -> memberService.update("testUser", updateMemberRequest),
                MemberErrorCode.DUPLICATE_EMAIL.getMessage());
    }
}
