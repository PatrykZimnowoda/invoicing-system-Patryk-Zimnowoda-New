package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat
import spock.lang.Specification
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService

import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.math.BigDecimal

class FileBasedDatabaseIntegrationTest extends Specification {

    FileService fileService = new FileService()
    JsonService jsonService = new JsonService()
    FileBasedDatabase database = new FileBasedDatabase(fileService, jsonService, "test-invoices.json", "test-id.txt")

    def setup() {
        if (!Files.exists(Paths.get("test-id.txt"))) {
            new File("test-id.txt").createNewFile()
        }
    }

    def cleanup() {
        new File("test-invoices.json").delete()
        new File("test-id.txt").delete()
    }

    def "should save and retrieve invoice"() {
        given: "an invoice"
        Invoice invoice = new Invoice()
        invoice.setDate(LocalDate.now())
        Company companyFrom = new Company(1, "1234567890", "Address 1")
        invoice.setCompanyFrom(companyFrom)
        Company companyTo = new Company(2, "0987654321", "Address 2")
        invoice.setCompanyTo(companyTo)
        List<InvoiceEntry> entries = new ArrayList<>()
        InvoiceEntry entry = new InvoiceEntry("Product Description", BigDecimal.valueOf(50.00), BigDecimal.valueOf(10.00), Vat.VAT_23)
        entries.add(entry)
        invoice.setEntries(entries)

        when: "the invoice is saved"
        Invoice savedInvoice = database.save(invoice)

        then: "the invoice is saved correctly"
        savedInvoice == invoice

        when: "the invoice is retrieved by id"
        Invoice retrievedInvoice = database.getById(savedInvoice.getId()).get()

        then: "the retrieved invoice is correct"
        retrievedInvoice == savedInvoice
    }
}