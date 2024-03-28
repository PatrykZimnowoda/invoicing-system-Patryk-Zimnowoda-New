package pl.futurecollars.invoicing

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat

import java.time.LocalDate

public class TestHelpers {

    public static Invoice createTestInvoice() {
        Company companyFrom = new Company(1, "123-456-78-90", "Company Street 1");
        Company companyTo = new Company(2, "098-765-43-21", "Company Avenue 2");
        InvoiceEntry entry = new InvoiceEntry("Test service", new BigDecimal("100.00"), new BigDecimal("23.00"), Vat.VAT_23);

        return new Invoice(null, LocalDate.now(), companyFrom, companyTo, Collections.singletonList(entry));
    }
}