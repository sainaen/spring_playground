package org.sainaen.mvc_learning;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HelloController {
    private final String messageTemplate = "Hello, %s!";

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model, @RequestParam(defaultValue = "World", required = false) String name) {
        model.addAttribute("message", String.format(messageTemplate, name));
        return "hello";
    }
}
