package hankki.hankkimap.repository;

import hankki.hankkimap.domain.Bookmark;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Bookmark bookmark) {
        em.persist(bookmark);
    }

    public Bookmark findOne(int num) {
        return em.find(Bookmark.class, num);
    }

    public List<Bookmark> findByName(String name, String id) {
        return em.createQuery("select b from Bookmark b where b.name = :name and b.id = :id", Bookmark.class)
                .setParameter("name", name)
                .setParameter("id",id)
                .getResultList();
    }

    public List<Bookmark> findAll(String id) {
        return em.createQuery("select b from Bookmark b where b.id=:id", Bookmark.class)
                .setParameter("id",id)
                .getResultList();
    }

    public int deleteById(String name, String id) {
        return em.createQuery("delete from Bookmark b where b.name=:name and b.id=:id")
                .setParameter("name", name)
                .setParameter("id", id)
                .executeUpdate();
    }
}
