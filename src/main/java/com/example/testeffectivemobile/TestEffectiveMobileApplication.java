package com.example.testeffectivemobile;

import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TestEffectiveMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestEffectiveMobileApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
