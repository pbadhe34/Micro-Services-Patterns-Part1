package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class SayHelloApplication {

  private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);

  @RequestMapping(value = "/greeting")
  public String greet(HttpServletRequest request) {
    log.info("Access /greeting");

    List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
    Random rand = new Random();

    int randomNum = rand.nextInt(greetings.size());    
    
    System.out.println("Greeting gets processed on Server-1");
    String data = "I am running on "+request.getRequestURL().toString();
    return data; //greetings.get(randomNum);
  }

  @RequestMapping(value = "/")
  public String home() {
    log.info("Access /");
    return "Hi!";
  }

  public static void main(String[] args) {
    SpringApplication.run(SayHelloApplication.class, args);
  }
}
