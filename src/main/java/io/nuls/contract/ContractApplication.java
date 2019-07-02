package io.nuls.contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContractApplication {
    public static void main(String[] args) {
        System.out.println("--------------start application--------------");
        SpringApplication.run(ContractApplication.class, args);
    }

    public static void startByMavenPackage(){
        System.out.println("--------------startByMavenPackage--------------");
        SpringApplication.run(ContractApplication.class);
    }

}
