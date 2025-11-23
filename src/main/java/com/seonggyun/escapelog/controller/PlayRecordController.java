package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.member.Member;
import com.seonggyun.escapelog.domain.playRecord.PlayRecord;
import com.seonggyun.escapelog.form.PlayRecordForm;
import com.seonggyun.escapelog.service.playRecord.PlayRecordService;
import com.seonggyun.escapelog.service.theme.ThemeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listPlayRecord(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "sort", required = false) String sort,
                                 @RequestParam(value = "cleared",required = false) Boolean cleared,
                                 Model model) {

        List<PlayRecord> playRecords = playRecordService.searchPlayRecord(keyword, sort, cleared);

        model.addAttribute("playRecords", playRecords);
        model.addAttribute("sort", sort);
        model.addAttribute("cleared", cleared);
        return "playRecordPages/playRecordList";
    }
}
