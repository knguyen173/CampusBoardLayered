package controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class WelcomeController {
    
    @GetMapping("/")
    public String HelloWorld(){
        return "Hello World";
    }

    @GetMapping("/welcome")
    public String Welcome(){
        return "Welcome to CS7319";
    }
    
    @GetMapping("/quote")
    public String Quotes(){
        String[] quotes = {"Believe you can and you're halfway there.", 
        "The only way to do great work is to love what you do.",
        "Success is not the only key to happiness. Happiness is the key to success.", 
        "It always seems impossible until it's done.", 
        "The future belongs to those who believe in the beauty of their dreams."};

        int randomIndex = ThreadLocalRandom.current().nextInt(quotes.length);
        
        
        return quotes[randomIndex];
    }
    
}
