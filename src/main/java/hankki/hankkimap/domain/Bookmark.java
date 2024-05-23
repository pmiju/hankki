package hankki.hankkimap.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="bookmark")
public class Bookmark {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="bookmark_num")
    private int num;

    @Column(name="bookmark_id", length = 500, nullable = false)
    private String id;

    @Column(name="bookmark_name", length = 500, nullable = false)
    private String name;

    @Column(name="phone", length = 500, nullable = false)
    private String phone;

    @Column(name="address", length = 500, nullable = false)
    private String address;
}
