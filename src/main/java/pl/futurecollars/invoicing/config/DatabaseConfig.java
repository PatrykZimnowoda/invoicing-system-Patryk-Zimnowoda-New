package pl.futurecollars.invoicing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.db.file.IdService;
import pl.futurecollars.invoicing.utils.FileService;
import pl.futurecollars.invoicing.utils.JsonService;

@Configuration
public class DatabaseConfig {

  private static final String INVOICES_FILE_PATH = "invoices.json";
  private static final String ID_FILE_PATH = "id.txt";

  @Bean
  public IdService idService() {
    return new IdService(ID_FILE_PATH, new FileService());
  }

  @Bean
  public Database fileBasedDatabase(IdService idService) {
    return new FileBasedDatabase(new FileService(), new JsonService(), INVOICES_FILE_PATH, idService);
  }
}
