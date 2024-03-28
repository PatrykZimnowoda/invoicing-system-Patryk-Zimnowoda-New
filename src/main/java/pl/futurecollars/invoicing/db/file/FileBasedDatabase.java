package pl.futurecollars.invoicing.db.file;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.utils.FileService;
import pl.futurecollars.invoicing.utils.JsonService;

public class FileBasedDatabase implements Database {

  private final FileService fileService;
  private final JsonService jsonService;
  private final String invoicesFilePath;
  private final IdService idService;

  public FileBasedDatabase(FileService fileService, JsonService jsonService, String invoicesFilePath, String idFilePath) {
    this.fileService = fileService;
    this.jsonService = jsonService;
    this.invoicesFilePath = invoicesFilePath;
    this.idService = new IdService(idFilePath, fileService);
  }

  @Override
  public Invoice save(Invoice invoice) {
    int nextId = idService.getNextId();
    invoice.setId(nextId);
    String json = jsonService.toJson(invoice);
    fileService.appendLine(json, invoicesFilePath);
    return invoice;
  }

  @Override
  public Optional<Invoice> getById(int id) {
    return getAll().stream()
        .filter(invoice -> invoice.getId() == id)
        .findFirst();
  }

  @Override
  public List<Invoice> getAll() {
    List<String> lines = fileService.readLines(invoicesFilePath);
    return lines.stream()
        .filter(line -> !line.isBlank())
        .map(line -> jsonService.fromJson(line, Invoice.class))
        .collect(Collectors.toList());
  }

  @Override
  public Invoice update(int id, Invoice updatedInvoice) {
    List<Invoice> invoices = getAll();
    int index = -1;
    for (int i = 0; i < invoices.size(); i++) {
      if (invoices.get(i).getId() == id) {
        index = i;
        break;
      }
    }
    if (index == -1) {
      throw new IllegalArgumentException("Invoice with id " + id + " not found");
    }
    invoices.set(index, updatedInvoice);
    fileService.writeLines(
        invoices.stream()
            .map(jsonService::toJson)
            .collect(Collectors.toList()),
        invoicesFilePath
    );
    return updatedInvoice;
  }

  @Override
  public boolean delete(int id) {
    List<Invoice> invoices = getAll();
    boolean removed = invoices.removeIf(invoice -> invoice.getId() == id);
    if (removed) {
      fileService.writeLines(
          invoices.stream()
              .map(jsonService::toJson)
              .collect(Collectors.toList()),
          invoicesFilePath
      );
    }
    return removed;
  }

}
