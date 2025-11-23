package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.Genre;
import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.form.ThemeForm;
import com.seonggyun.escapelog.service.theme.ThemeService;
import com.seonggyun.escapelog.service.venue.VenueService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
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
    public String listTheme(@RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "genres", required = false) List<Genre> genres,
            Model model) {
        List<Theme> themes = themeService.searchTheme(keyword, sort, genres);
        Set<Genre> availableGenres = themeService.extractGenres(themes);

        model.addAttribute("themes", themes);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("selectedGenres", genres);
        model.addAttribute("availableGenres", availableGenres);

        return "themePages/themeList";
    }
}
