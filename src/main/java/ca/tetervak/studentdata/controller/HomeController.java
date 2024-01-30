package ca.tetervak.studentdata.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class HomeController {

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "Index";
    }

    @GetMapping( "/login")
    public String login(
            @RequestParam(defaultValue = "false") boolean error,
            Model model
    ){
        log.trace("login() is called");
        model.addAttribute("error", error);
        if(error){
            log.debug("Login error");
        }

        return "Login";
    }

}
