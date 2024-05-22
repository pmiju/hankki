package hankki.hankkimap.controller;

import hankki.hankkimap.domain.Member;
import hankki.hankkimap.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "join";
    }

    @RequestMapping(value = "/checkId")
    @ResponseBody
    public int idCheck(@RequestParam(value = "id") String id) {
        int result = memberService.checkId(id);
        log.info("검색성공이라네~");
        return result;
    }

    @PostMapping(value = "/join")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.info("실패");
            return "join";
        }

        Member member = new Member();
        member.setId(form.getId());
        member.setPw(form.getPw());
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPhone(form.getPhone());

        log.info("회원가입성공");
        memberService.join(member);
        return "redirect:/main";
    }

    @GetMapping(value = "/login")
    public String createLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping(value = "/member/login")
    public String login(@Valid LoginForm form, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (result.hasErrors()) {
            return "login";
        }

        Member member = new Member();
        member.setId(form.getId());
        member.setPw(form.getPw());

        Member loginMember = memberService.login(member.getId(), member.getPw());

        request.getSession().invalidate();
        HttpSession session = request.getSession(true);

        if (loginMember != null && loginMember.checkPw(loginMember.getPw())) {
            session.setAttribute("id", loginMember.getId());
            session.setAttribute("pw", loginMember.getPw());
            session.setAttribute("name", loginMember.getName());
            session.setAttribute("email", loginMember.getEmail());
            session.setAttribute("phone", loginMember.getPhone());
            session.setMaxInactiveInterval(1000);
            log.info("로그인 성공");
            return "redirect:/main";
        } else {
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html; charset=utf-8");
            out.println("<script language='javascript'>");
            out.println("alert('아이디 또는 비밀번호가 틀립니다.');");
            out.println("history.go(-1); </script>");
            out.close();
            log.info("로그인 실패");
            return "redirect:/login";
        }

    }

    //로그아웃
    @GetMapping(value = "/logout")
    public String checkLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    /*마이페이지*/
    @GetMapping(value = "/mypage")
    public String checkLogin(@SessionAttribute(name = "id", required = false) String id) {

        if (id == null) {
            log.info("로그인 정보 없음");
            return "login";
        } else {
            log.info("로그인 정보 있움");
            return "mypage";
        }

    }

        /*정보수정*/
    @GetMapping(value = "/update")
    public String updateForm(@SessionAttribute(name = "id", required = false) String id,
                             Model model) {
        model.addAttribute("updateForm", new UpdateForm());
        String uinfo = memberService.mypageInfo(id);
        model.addAttribute("uinfo", uinfo);
        return "update";
    }

    @PostMapping(value="/update")
    public String update(@Valid UpdateForm form, BindingResult result,
                         @SessionAttribute(name = "id", required = false) String id, Model model) {
        if (result.hasErrors()) {
            log.info("수정 오류 발생");
            return "update";
        }
        log.info("수정 controller");
        log.info(id);
        Member member = new Member();
        member.setMember_num(form.getMember_num());
        member.setId(id);
        member.setPw(form.getPw());
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPhone(form.getPhone());

        memberService.update(member);

        return "redirect:/update";
    }
}
