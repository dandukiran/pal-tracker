package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    String welMsg;

    public WelcomeController(@Value("${WELCOME_MESSAGE}") String welMsg) {
        this.welMsg = welMsg;
    }

    @GetMapping("/")
    public String sayHello() {
        System.out.println("COming here ");
        return welMsg;
    }
}
