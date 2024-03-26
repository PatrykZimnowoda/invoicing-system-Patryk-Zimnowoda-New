package pl.futurecollars.invoicing.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase;
import pl.futurecollars.invoicing.model.Invoice;
import pl.futurecollars.invoicing.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private InvoiceService invoiceService = new InvoiceService(new InMemoryDatabase());

  @PostMapping
  public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
    Invoice savedInvoice = invoiceService.saveInvoice(invoice);
    return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
  }

  @GetMapping
  public List<Invoice> getAllInvoices() {
    return invoiceService.getAllInvoices();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Invoice> getInvoiceById(@PathVariable int id) {
    return invoiceService.getInvoiceById(id)
        .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Invoice> updateInvoice(@PathVariable int id, @RequestBody Invoice updatedInvoice) {
    if (invoiceService.updateInvoice(id, updatedInvoice) != null) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Invoice> deleteInvoice(@PathVariable int id) {
    if (invoiceService.deleteInvoice(id)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
