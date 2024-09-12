package com.danozzo.organizeserver;

import com.danozzo.organizeserver.enumaration.Status;
import com.danozzo.organizeserver.model.Server;
import com.danozzo.organizeserver.repository.ServerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class OrganizeserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizeserverApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(new Server(null,
                    "192.168.1.23",
                    "Ubuntu Linux",
                    "16GB",
                    "Personal PC",
                    "http://localhost:8080/server/image/server1.png",
                    Status.SERVER_UP)
            );
            serverRepository.save(new Server(null,
                    "192.168.1.55",
                    "Fedora Linux",
                    "16GB",
                    "Dell Tower",
                    "http://localhost:8080/server/image/server1.png",
                    Status.SERVER_DOWN)
            );
            serverRepository.save(new Server(null,
                    "192.168.1.60",
                    "Ms Windows",
                    "32GB",
                    "Dell Tower",
                    "http://localhost:8080/server/image/server1.png",
                    Status.SERVER_UP)
            );
            serverRepository.save(new Server(null,
                    "192.168.1.79",
                    "OsX",
                    "16GB",
                    "Mail Server",
                    "http://localhost:8080/server/image/server1.png",
                    Status.SERVER_UP)
            );
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With","Jwt-Token"));
        config.setExposedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With","Jwt-Token"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);


        return new CorsFilter(source);
    }
}
