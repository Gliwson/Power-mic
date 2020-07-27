package pl.power;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "pl.power.feignRepository")
@EnableElasticsearchRepositories(basePackages = "pl.power.elasticRepository")
@EnableJpaRepositories(basePackages = "pl.power.repository")
public class PowerStationApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PowerStationApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PowerStationApplication.class);
    }

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
