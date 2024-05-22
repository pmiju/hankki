package hankki.hankkimap.repository;


import hankki.hankkimap.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*repository=dao*/
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(String id) {
        return em.find(Member.class, id); //멤버 찾아서 반환(단권조회)(타입, PK)
    }

    public List<Member> findById(String id) {
        return em.createQuery("select m from Member m where m.id = :id",
                Member.class)
                .setParameter("id", id)
                .getResultList();
    }

   public Optional<Member> findByUser(String id, String pw) {
        return em.createQuery("select m from Member m where m.id = :id and m.pw = :pw",
                        Member.class)
                .setParameter("id", id)
                .setParameter("pw", pw)
                .getResultList().stream().findAny();

    }

    public Optional<Member> findByUserId(String id) {
        return em.createQuery("select m from Member m where m.id = :id",
                        Member.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();
    }

    /*정보수정*/
    public int updateById(String id, String pw, String name, String email, String phone) {
        return em.createQuery("update Member m set m.pw=:pw, m.name=:name, m.email=:email, m.phone=:phone where m.id=:id")
                .setParameter("pw", pw)
                .setParameter("name", name)
                .setParameter("email", email)
                .setParameter("phone", phone)
                .setParameter("id", id)
                .executeUpdate();
    }

}


