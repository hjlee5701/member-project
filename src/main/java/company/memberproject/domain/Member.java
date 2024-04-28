package company.memberproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String userId;

    @Column()
    private String name;

    @Column()
    private String nickName;

    @Column()
    private String phoneNumber;

    @Column()
    private String email;

    @JsonIgnore
    @Column
    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regAt;


    public Member(String userId, String name, String nickName, String phoneNumber, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }




}
