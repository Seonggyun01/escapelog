package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String loginId, @RequestParam String password, HttpSession session,
                        RedirectAttributes redirectAttributes) {
        Member loginMember = memberService.login(loginId, password);

        if (loginMember == null) {
            redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호 올바르지 않습니다.");
            return "redirect:/login";
        }

        session.setAttribute("loginMember", loginMember);
        return "redirect:/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
