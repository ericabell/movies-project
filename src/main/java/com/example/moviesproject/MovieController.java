package com.example.moviesproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MovieController {
    @RequestMapping("/")
    public String index(Model model) {

        return "home";
    }

    @RequestMapping("/now-playing")
    public String nowPlaying(Model model) {

        return "now-playing";
    }

    @RequestMapping("/medium-popular-long-name")
    public String mediumPopularLongName(Model model) {

        return "medium-popular-long-name";
    }

    @RequestMapping("/overview-mashup")
    public String overviewMashup(Model model) {

        return "overview-mashup";
    }
}
