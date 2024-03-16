package pl.futurecollars.invoicing.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Invoice {
  private Integer id; // Użycie obiektowej wersji int pozwala na przyjmowanie wartości null
  private LocalDate date;
  private Company companyFrom;
  private Company companyTo;
  private List<InvoiceEntry> entries;
}
