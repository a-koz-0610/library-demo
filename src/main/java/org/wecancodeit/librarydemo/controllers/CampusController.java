package org.wecancodeit.librarydemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wecancodeit.librarydemo.models.Campus;
import org.wecancodeit.librarydemo.repositories.CampusRepository;

import javax.annotation.Resource;

@Controller
public class CampusController {

    @Resource
    private CampusRepository campusRepo;

    @RequestMapping({"/campuses", "/", ""})
    public String displayCampuses(Model model){
        model.addAttribute("campuses", campusRepo.findAll());
        return "campusesView";
    }

    @GetMapping("/campuses/{location}")
    public String displaySingleCampus(@PathVariable String location, Model model){
        Campus retrievedCampus = campusRepo.findCampusByLocation(location);
        model.addAttribute("campus", retrievedCampus);
        return "campusView";
    }

    @PostMapping("/add-campus")
    public String addCampus(@RequestParam String location) {
        Campus campusToAdd = campusRepo.findCampusByLocation(location);
        if(campusToAdd == null) {
            campusRepo.save(new Campus(location));
        }
            return "redirect:campuses";
    }
}
