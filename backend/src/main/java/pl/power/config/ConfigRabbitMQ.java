package pl.power.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigRabbitMQ {

    static final String topicExchangeName = "test-mateusz-exchange";

    static final String queueName = "app";

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    Queue queue2() {
        return new Queue("app2");
    }

    @Bean
    Queue powerStation() {
        return new Queue("powerStation",false);
    }

    @Bean
    Queue event() {
        return new Queue("event",false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(topicExchangeName);
    }

    @Bean
    Binding binding(FanoutExchange exchange) {
        return BindingBuilder.bind(queue()).to(exchange);
    }

    @Bean
    Binding binding2(FanoutExchange exchange) {
        return BindingBuilder.bind(queue2()).to(exchange);
    }

}
