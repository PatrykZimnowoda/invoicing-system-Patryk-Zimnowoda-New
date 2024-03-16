package pl.futurecollars.invoicing.service;

import java.util.List;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InvoiceService {

  private final Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public Invoice saveInvoice(Invoice invoice) {
    return database.save(invoice);
  }

  public List<Invoice> getAllInvoices() {
    return database.getAll();
  }

}
