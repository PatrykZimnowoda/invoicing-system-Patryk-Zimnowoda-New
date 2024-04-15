package pl.futurecollars.invoicing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;

@Configuration
public class AppConfig {

  @Bean
  public Database database() {
    return new InMemoryDatabase();
  }
}
