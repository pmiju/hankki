package hankki.hankkimap.controller;

import hankki.hankkimap.domain.Bookmark;
import hankki.hankkimap.domain.Member;
import hankki.hankkimap.repository.MemberRepository;
import hankki.hankkimap.service.BookmarkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping(value = "/addPlace")
    @ResponseBody
    public String addPlaces(@Valid BookmarkForm form, BindingResult result,
                            @RequestParam(value = "name") String name,
                            @RequestParam(value = "phone") String phone,
                            @RequestParam(value = "address") String address,
                            @SessionAttribute(value = "id", required = false) String id) {
        if (result.hasErrors()) {
            log.info("장소저장실패");
            return "main";
        }
        if (id == null) {
            log.info("로그인 후 사용 가능");
            return "main";
        } else {
            log.info(name);
            log.info(phone);
            log.info(address);
            log.info(id);
            log.info("장소저장 controller 탐");
            Bookmark bookmark = new Bookmark();
            bookmark.setNum(form.getNum());
            bookmark.setId(id);
            bookmark.setName(name);
            bookmark.setPhone(phone);
            bookmark.setAddress(address);

            bookmarkService.star(bookmark);
            return "redirect:/";
        }
    }

    @GetMapping(value = "/like")
    public String like(@SessionAttribute(value = "id", required = false) String id,
                       Model model) {

        if (id == null) {
            log.info("로그인 정보 없음");
            return "login";
        } else {
            List<Bookmark> likes = bookmarkService.findLikes(id);
            model.addAttribute("likes", likes);
            return "like";
        }
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(value="id") String id,
                         @RequestParam(value="name") String name) {
        log.info(id);
        log.info(name);
        Bookmark bookmark = new Bookmark();
        bookmark.setId(id);
        bookmark.setName(name);
        bookmarkService.delete(bookmark);
        return "redirect:/like";
    }
}
