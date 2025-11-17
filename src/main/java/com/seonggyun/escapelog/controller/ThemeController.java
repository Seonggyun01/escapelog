package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.form.ThemeForm;
import com.seonggyun.escapelog.service.ThemeService;
import com.seonggyun.escapelog.service.VenueService;
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
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;
    private final VenueService venueService;

    /**
     * 테마 등록폼
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("themeForm", new ThemeForm());
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("genres", Genre.values());
        return "themePages/createTheme";
    }

    /**
     * 테마 등록 처리 → 목록으로 리다이렉트
     */
    @PostMapping("/new")
    public String createTheme(@Valid @ModelAttribute("themeForm") ThemeForm form,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            model.addAttribute("genres", Genre.values());
            return "themePages/createTheme";
        }
        themeService.saveTheme(form.getVenueId(),
                form.getTitle(),
                form.getDifficulty(),
                form.getDurationMin(),
                form.getMinPlayer(),
                form.getMaxPlayer(),
                form.getGenres()
        );
        return "redirect:/themes";
    }

    /**
     * 테마 목록 조회
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("themes", themeService.findAll());
        return "themePages/themeList";
    }
}
