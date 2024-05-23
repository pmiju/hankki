package hankki.hankkimap.service;

import hankki.hankkimap.domain.Bookmark;
import hankki.hankkimap.domain.Member;
import hankki.hankkimap.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class BookmarkService {
    @Autowired
    BookmarkRepository bookmarkRepository;

    @Transactional
    public String star(Bookmark bookmark) {
        validateDuplicateBookmark(bookmark);
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    @Transactional
    public String delete(Bookmark bookmark) {
        log.info("bookmarkservice delete 탐");
        log.info(bookmark.getId());
        log.info(bookmark.getName());
        bookmarkRepository.deleteById(bookmark.getId(), bookmark.getName());
        return bookmark.getId();
    }

    private void validateDuplicateBookmark(Bookmark bookmark) {
        log.info(this.getClass().getName() + "중복체크로직실행");
        log.info(bookmark.getName());

        List<Bookmark> findName = bookmarkRepository.findByName(bookmark.getName(),bookmark.getId());
        log.info("로직 후 아이디 = {}", findName);

        if(!findName.isEmpty()) {
            if (!findName.isEmpty()) {
                throw new IllegalStateException("이미 저장함");
            }
        }
    }

    public List<Bookmark> findLikes(String id) {
        return bookmarkRepository.findAll(id);
    }


}
