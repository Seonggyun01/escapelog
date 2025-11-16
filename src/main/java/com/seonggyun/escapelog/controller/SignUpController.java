package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.form.MemberSignUpForm;
import com.seonggyun.escapelog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUpForm(@ModelAttribute("MemberSignUpForm") MemberSignUpForm form) {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String joinMember(@ModelAttribute("MemberSignUpForm") MemberSignUpForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "login/signup";
        }

        try{
            memberService.join(form.getLoginId(),form.getPassword(),form.getName());
        } catch (IllegalArgumentException e){
            bindingResult.reject("signupError", e.getMessage());
            return "login/signup";
        }

        return "redirect:/login";
    }
}
