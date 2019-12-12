package instagram.controllers;


import instagram.services.helloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloWorldController {
    private final helloWorldService helloWorldService;

    public helloWorldController(@Autowired instagram.services.helloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/")
    public String say_hello () {
        return helloWorldService.talk();
    }



}
