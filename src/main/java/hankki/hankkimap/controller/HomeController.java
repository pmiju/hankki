package hankki.hankkimap.controller;

import hankki.hankkimap.domain.Member;
import hankki.hankkimap.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @RequestMapping("/")
    public String splash() {
        return "splash";
    }

    @RequestMapping("/main")
    public String main() {
        log.info("main controller");
        return "main";
    }

    @RequestMapping("/join")
    public String join() {
        return "join";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
