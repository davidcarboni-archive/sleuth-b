package uk.gov.ros.sleuthb;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    static Logger log = LoggerFactory.getLogger(Service.class);

    @Value("${SERVICE_C:http://localhost:8003/}")
    static String serviceC = configure("SERVICE_C", "http://localhost:8003");

    @Value("${SERVICE_D:http://localhost:8004/}")
    static String serviceD = configure("SERVICE_D", "http://localhost:8004");;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/")
    String service() {
        log.info("Java service called");
        String string;

        log.debug("Calling service-c: " + serviceC);
        string = restTemplate.getForObject(serviceC, String.class);
        log.debug("service-c result: " + string);

        log.debug("Calling service-d: " + serviceD);
        string = restTemplate.getForObject(serviceD, String.class);
        log.debug("service-d result: " + string);

        return "Service call succeeded (service-b)";
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Service.class, args);
    }

    static String configure(String variable, String fallback) {
        return StringUtils.defaultIfBlank(System.getenv(variable), fallback);
    }
}
