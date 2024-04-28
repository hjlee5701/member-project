package company.memberproject.repository;

import company.memberproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByEmail(String email);
}
