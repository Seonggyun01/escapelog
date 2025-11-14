package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.form.MemberSingUpForm;
import com.seonggyun.escapelog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class SingUpController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUpForm(@ModelAttribute("MemberSignUpForm")MemberSingUpForm form){
        return "login/signup";
    }
}
