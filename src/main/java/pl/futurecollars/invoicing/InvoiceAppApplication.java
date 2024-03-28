package pl.futurecollars.invoicing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class InvoiceAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(InvoiceAppApplication.class, args);
  }

  @GetMapping
  public String helloWorld() {
    return "Hello World!";
  }
}

