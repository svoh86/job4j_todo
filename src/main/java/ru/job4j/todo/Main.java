package ru.job4j.todo;

import net.jcip.annotations.ThreadSafe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
