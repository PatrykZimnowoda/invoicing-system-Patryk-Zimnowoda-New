package pl.futurecollars.invoicing.db.file;

import java.util.List;
import pl.futurecollars.invoicing.utils.FileService;

public class IdService {
  private final String idFilePath;
  private final FileService fileService;

  public IdService(String idFilePath, FileService fileService) {
    this.idFilePath = idFilePath;
    this.fileService = fileService;
  }

  public synchronized int getNextId() {
    List<String> lines;
    try {
      lines = fileService.readLines(idFilePath);
    } catch (RuntimeException e) {
      throw new RuntimeException("Error reading file: " + idFilePath, e);
    }

    int id = lines.isEmpty() ? 1 : Integer.parseInt(lines.get(0)) + 1;

    try {
      fileService.writeLines(List.of(String.valueOf(id)), idFilePath);
    } catch (RuntimeException e) {
      throw new RuntimeException("Error writing to file: " + idFilePath, e);
    }

    return id;
  }
}
