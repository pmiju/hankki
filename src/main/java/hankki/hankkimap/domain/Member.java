package hankki.hankkimap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*memberDto랑 같은 것?*/
@Entity
@Getter
@Setter
@Table(name="membertbl")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="member_num")
    private int member_num;

    @Column(name="id", length=500, nullable = false)
    private String id;

    @Column(name="pw", length=500, nullable = false)
    private String pw;

    @Column(name="name", length=500, nullable = false)
    private String name;

    @Column(name="email", length=500, nullable = false)
    private String email;

    @Column(name="phone", length=500, nullable = false)
    private String phone;

    public Member() {

    }
    @Builder
    public Member(int member_num,
                  String id,
                  String pw,
                  String name,
                  String email,
                  String phone) {
        this.member_num = member_num;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public boolean checkPw(String pw) {
        return this.pw.equals(pw);
    }

}
