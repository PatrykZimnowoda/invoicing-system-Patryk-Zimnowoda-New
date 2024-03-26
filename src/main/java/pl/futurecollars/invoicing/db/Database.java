package pl.futurecollars.invoicing.db;

import java.util.List;
import java.util.Optional;
import pl.futurecollars.invoicing.model.Invoice;

public interface Database {

  Invoice save(Invoice invoice);

  Optional<Invoice> getById(int id);

  List<Invoice> getAll();

  Invoice update(int id, Invoice updatedInvoice);

  boolean delete(int id);
}
