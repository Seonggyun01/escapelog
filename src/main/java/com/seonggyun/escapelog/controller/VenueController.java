package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.form.VenueForm;
import com.seonggyun.escapelog.service.venue.VenueService;
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
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    /**
     * 매장 등록 폼
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("venueForm", new VenueForm());
        return "venuePages/createVenue"; //createVenue.html
    }

    /**
     * 매장 등록 처리 → 목록으로 리다이렉트
     */
    @PostMapping("/new")
    public String createVenue(@Valid @ModelAttribute("venueForm") VenueForm form,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "venuePages/createVenue";
        }
        venueService.saveVenue(form.getName(), form.getRegion());
        return "redirect:/venues";
    }

    /**
     * 매장 목록 조회
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("venues", venueService.findAll());
        return "venuePages/venueList";
    }
}
