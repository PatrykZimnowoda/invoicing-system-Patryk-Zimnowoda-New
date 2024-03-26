package pl.futurecollars.invoicing.db.file;

import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.db.file.FileBasedDatabase;
import pl.futurecollars.invoicing.utils.FileService;
import pl.futurecollars.invoicing.utils.JsonService;

public class Configuration {

  private static final String INVOICES_FILE_PATH = "invoices.json";
  private static final String ID_FILE_PATH = "id.txt";

  public Database database() {
    FileService fileService = new FileService();
    JsonService jsonService = new JsonService();
    return new FileBasedDatabase(fileService, jsonService, INVOICES_FILE_PATH, ID_FILE_PATH);
  }
}
