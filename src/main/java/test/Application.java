package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@SpringBootApplication(proxyBeanMethods = false)
public class Application {

    public static void main(String[] args) throws IntrospectionException {

        SpringApplication.run(Application.class, args);


    }

//    @Bean
//    public MessageRoutingCallback customRouter() {
//        return new MessageRoutingCallback() {
//            @Override
//            public FunctionRoutingResult routingResult(Message<?> message) {
//                return new FunctionRoutingResult((String)message.getHeaders().get("Func-Name"));
//            }
//        };
//    }

    @EnableAutoConfiguration
    public static class RoutingFunctionConfiguration {

        @Bean
        public Function<String, String> echo() {
            return x -> {
                System.out.println("===> echo");
                return x+" echo function";
            };
        }

        @Bean
        public Function<Person, Person> pojoecho() {
            return x -> {
                System.out.println("===> pojoecho");
                return x;
            };
        }


        @SuppressWarnings("unused")
        private static class Person {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}