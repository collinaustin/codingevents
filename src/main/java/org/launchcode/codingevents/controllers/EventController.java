package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String handlerMethod(Model model) {
        List<String> events = new ArrayList<>();
        events.add("Swimming");
        events.add("Meditation");
        events.add("Coding bootcamp");
        events.add("Watch football");

        model.addAttribute("events", events);
        return "events/index";
    }

//    Lives at /events
    @GetMapping("create")
    public String renderCreateEventForm() {
        return "events/create";
    }
}
