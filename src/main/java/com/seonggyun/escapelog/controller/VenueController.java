package com.seonggyun.escapelog.controller;

import com.seonggyun.escapelog.domain.venue.Venue;
import com.seonggyun.escapelog.form.VenueForm;
import com.seonggyun.escapelog.service.venue.VenueService;
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

//    /**
//     * 매장 목록 조회
//     */
//    @GetMapping
//    public String listVenues(Model model) {
//        model.addAttribute("venues", venueService.findAll());
//        return "venuePages/venueList";
//    }

    @GetMapping
    public String searchVenues(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "sort", required = false) String sort,
                               Model model) {
        List<Venue> venues = venueService.searchVenues(keyword, sort);
        model.addAttribute("venues", venues);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        return "venuePages/venueList";
    }
}
