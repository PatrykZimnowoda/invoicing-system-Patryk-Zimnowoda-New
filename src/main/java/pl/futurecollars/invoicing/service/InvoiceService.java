package pl.futurecollars.invoicing.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

@Service
public class InvoiceService {

  private final Database database;

  @Autowired
  public InvoiceService(Database database) {
    this.database = database;
  }

  public Invoice saveInvoice(Invoice invoice) {
    return database.save(invoice);
  }

  public List<Invoice> getAllInvoices() {
    return database.getAll();
  }

  public Optional<Invoice> getInvoiceById(int id) {
    return database.getById(id);
  }

  public Invoice updateInvoice(int id, Invoice updatedInvoice) {
    return database.update(id, updatedInvoice);
  }

  public boolean deleteInvoice(int id) {
    return database.delete(id);
  }
}
