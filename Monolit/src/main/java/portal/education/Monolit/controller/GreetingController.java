package portal.education.Monolit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/hellofree")
    public String getHelloFree() {
        return "hellofree";
    }

    @GetMapping("/hellonotfree")
    public String getHelloNotFree() {
        return "hellonotfree";
    }

}
