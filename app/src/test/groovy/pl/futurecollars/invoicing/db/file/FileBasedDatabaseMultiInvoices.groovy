package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService
import pl.futurecollars.invoicing.model.Vat
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

// Dodano import

class FileBasedDatabaseMultiInvoices extends Specification {

    FileService fileService = new FileService()
    JsonService jsonService = new JsonService()
    FileBasedDatabase database = new FileBasedDatabase(fileService, jsonService, "test-invoices.json", "test-id.txt")

    def setup() {
        new File("test-invoices.json").createNewFile()
        new File("test-id.txt").createNewFile()
    }

    def cleanup() {
        new File("test-invoices.json").delete()
        new File("test-id.txt").delete()
    }

    def "should return all invoices when getting all invoices"() {

        given: "multiple invoices in the database"
        Invoice invoice1 = new Invoice()
        database.save(invoice1)
        Invoice invoice2 = new Invoice()
        database.save(invoice2)

        when: "all invoices are retrieved"
        List<Invoice> invoices = database.getAll()

        then: "all invoices are returned"
        assert invoices.size() == 2
    }

    def "should save invoice"() {
        given: "an invoice"
        Invoice invoice = new Invoice()
        invoice.setDate(LocalDate.now()) // Teraz LocalDate jest dostępne
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
    }

    def "should read multiple invoices from json"() {
        given: "two invoices"
        Invoice invoice1 = new Invoice()
        Invoice invoice2 = new Invoice()

        when: "the invoices are saved"
        Invoice savedInvoice1 = database.save(invoice1)
        Invoice savedInvoice2 = database.save(invoice2)

        then: "the saved invoices match the expected invoices"
        savedInvoice1 == invoice1
        savedInvoice2 == invoice2

        and: "the invoices are read from the database"
        List<Invoice> retrievedInvoices = database.getAll()

        then: "the retrieved invoices match the saved invoices"
        retrievedInvoices.size() == 2
        retrievedInvoices.contains(savedInvoice1)
        retrievedInvoices.contains(savedInvoice2)
    }
}