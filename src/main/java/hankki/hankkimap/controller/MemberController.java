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
import java.util.List;


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

    @PostMapping(value = "/member/join")
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
    public String checkLogin(@Valid LoginForm loginform, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response,
                        Model model) throws Exception {
        if (result.hasErrors()) {
            return "login";
        }

        Member member = new Member();
        member.setId(loginform.getId());
        member.setPw(loginform.getPw());

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
            return "login";
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
    @GetMapping(value = "/update/{id}")
    public String updateForm(@PathVariable("id") String id,
                             @Valid @ModelAttribute UpdateForm form, Model model) {
        log.info("update controller 탐");
        log.info(id);

        Member member = new Member();
        member.setId(id);

        String info = memberService.mypageInfo(member);

        log.info(info);
        model.addAttribute("info", info);
        return "update";
    }

    @PostMapping(value = "/member/{id}/update")
    public String update(@Valid @RequestBody UpdateForm form, BindingResult result,
                         @PathVariable(name = "id") String id,
                         Model model) {
        if (result.hasErrors()) {
            log.info("수정 오류 발생");
            return "mypage";
        }
        log.info("수정버튼 controller");
        log.info(id);
        Member member = new Member();
        member.setId(id);

        String updateInfo = memberService.update(member);
        model.addAttribute("uInfo", updateInfo);

        return "redirect:/update/{id}";
    }
}
