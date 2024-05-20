package hankki.hankkimap.controller;

import hankki.hankkimap.domain.Member;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkForm {
    private int num;

    private String id;

    private String places;

    private String name;

    private String phone;

    private String address;
}
