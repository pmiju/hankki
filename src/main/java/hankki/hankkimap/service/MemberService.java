package hankki.hankkimap.service;

import hankki.hankkimap.domain.Member;
//import hankki.hankkimap.dto.MemberDTO;
import hankki.hankkimap.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public String join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        memberRepository.findByUserId(member.getId());
        return member.getId();
    }

    @Transactional
    public Member login(String id, String pw) {
        Optional<Member> findMember = memberRepository.findByUser(id,pw);
        return findMember.get();
    }

    @Transactional
    public String update(Member member) {
        log.info("member service 로직 탐");
        log.info(member.getId());
        log.info(member.getPw());
        log.info(member.getPhone());
        log.info(member.getName());
        log.info(member.getEmail());
        memberRepository.updateById(member.getId(), member.getPw(), member.getName(), member.getEmail(), member.getPhone());
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        log.info(this.getClass().getName() + "중복체크로직실행");
        log.info(member.getId());

        List<Member> findMembers = memberRepository.findById(member.getId());

        log.info("로직 후 아이디 = ", findMembers);

        if(!findMembers.isEmpty()) {
            if (!findMembers.isEmpty()) {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }
        }
    }

    public int checkId(String id) {
        Optional<Member> byUserId = memberRepository.findByUserId(id);
        if (byUserId.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public String mypageInfo(String id) {
        List<Member> updateId = memberRepository.findById(id);
        return id;
    }
}
