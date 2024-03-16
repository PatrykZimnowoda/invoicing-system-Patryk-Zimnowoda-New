package pl.futurecollars.invoicing.db.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import pl.futurecollars.invoicing.db.Database;
import pl.futurecollars.invoicing.model.Invoice;

public class InMemoryDatabase implements Database {

  private final List<Invoice> invoices = new ArrayList<>();
  private final AtomicInteger idGenerator = new AtomicInteger();

  @Override
  public Invoice save(Invoice invoice) {
    invoice.setId(idGenerator.incrementAndGet());
    invoices.add(invoice);
    return invoice;
  }

  @Override
  public Optional<Invoice> getById(int id) {
    return invoices.stream().filter(invoice -> invoice.getId().equals(id)).findFirst();
  }

  @Override
  public List<Invoice> getAll() {
    return new ArrayList<>(invoices);
  }

  @Override
  public Invoice update(int id, Invoice updatedInvoice) {
    delete(id);
    updatedInvoice.setId(id);
    invoices.add(updatedInvoice);
    return updatedInvoice;
  }

  @Override
  public boolean delete(int id) {
    return invoices.removeIf(invoice -> invoice.getId().equals(id));
  }
}
