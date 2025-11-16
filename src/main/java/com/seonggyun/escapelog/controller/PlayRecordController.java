package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.Member;
import com.seonggyun.escapelog.form.PlayRecordForm;
import com.seonggyun.escapelog.service.PlayRecordService;
import com.seonggyun.escapelog.service.ThemeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/playRecords")
public class PlayRecordController {
    private final PlayRecordService playRecordService;
    private final ThemeService themeService;

    /**
     * 후기 등록 폼 출력
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("playRecordForm", new PlayRecordForm());
        model.addAttribute("themes", themeService.findAll());
        return "playRecordPages/createPlayRecord";
    }

    @PostMapping("/new")
    public String createPlayRecord(@Valid @ModelAttribute("playRecordForm") PlayRecordForm form,
                                   BindingResult bindingResult,
                                   Model model,
                                   HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("themes", themeService.findAll());
            return "playRecordPages/createPlayRecord";
        }

        int clearTimeSec = form.getClearTimeSecAsInt();

        playRecordService.savePlayRecord(loginMember.getId(),
                form.getThemeId(),
                form.getPlayDate(),
                form.getCleared(),
                clearTimeSec,
                form.getHintCount(),
                form.getRating(),
                form.getComment()
        );
        return "redirect:/playRecords";
    }

    /**
     * 후기 목록 조회
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("playRecords", playRecordService.findAll());
        return "playRecordPages/playRecordList";
    }
}
