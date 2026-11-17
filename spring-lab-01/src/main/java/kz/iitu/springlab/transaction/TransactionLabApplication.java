package kz.iitu.springlab.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "kz.iitu.springlab.transaction")
@EnableJpaRepositories(basePackages = "kz.iitu.springlab.transaction")
public class TransactionLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionLabApplication.class, args);
    }
}