package mobileum.serviceframework.serviceframework.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServiceController {

    @PostMapping("/logmessage")
    public void logMessage (@RequestBody String message) {
        System.out.println("THIS IS THE MESSAGE: " + message);
    }

    @GetMapping("/example")
    public String returnTestMessage() {
        return "TEST MESSAGE!!!!!!";
    }

}
