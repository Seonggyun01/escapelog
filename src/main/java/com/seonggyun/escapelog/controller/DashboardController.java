package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.member.Member;
import com.seonggyun.escapelog.domain.playRecord.PlayRecord;
import com.seonggyun.escapelog.form.DashBoardDto;
import com.seonggyun.escapelog.service.playRecord.PlayRecordService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final PlayRecordService playRecordService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/login";
        }

        long totalPlays = playRecordService.countByMember(loginMember);
        int successRate = playRecordService.calculateSuccessRate(loginMember);
        long storeCount = playRecordService.countDistinctVenueByMember(loginMember);
        String avgTime = playRecordService.getAverageClearTimeFormatted(loginMember);
        List<PlayRecord> recentPlays = playRecordService.getRecentPlays(loginMember);

        DashBoardDto dashboard = DashBoardDto.builder()
                .totalPlays(totalPlays)
                .successRate(successRate)
                .storeCount(storeCount)
                .avgTime(avgTime)
                .build();

        model.addAttribute("memberName", loginMember.getName());
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("recentPlays", recentPlays);

        return "dashboard";
    }
}
