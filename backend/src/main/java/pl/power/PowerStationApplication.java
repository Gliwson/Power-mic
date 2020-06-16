package pl.power;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//@Controller
@SpringBootApplication
public class PowerStationApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PowerStationApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PowerStationApplication.class);
    }
//    @GetMapping(value = "/{path:[^\\.]*}")
//    public String redirect() {
//        return "forward:/";
//    }
}
