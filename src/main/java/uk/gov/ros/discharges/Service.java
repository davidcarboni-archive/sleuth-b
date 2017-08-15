package uk.gov.ros.discharges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Service {

    private static Logger log = LoggerFactory.getLogger(Service.class);

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/")
    String service() {
        log.info("Java service called");
        String string;

        log.debug("Calling service-c");
        string = restTemplate.getForObject("http://localhost:8003/", String.class);
        log.debug("service-c result: " + string);

        log.debug("Calling service-d");
        string = restTemplate.getForObject("http://localhost:8004/", String.class);
        log.debug("service-d result: " + string);

        return "Service call succeeded (service-b)";
    }

    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }
}
