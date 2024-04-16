package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Company
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.model.Vat
import spock.lang.Specification
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService
import java.time.LocalDate
import java.math.BigDecimal

class FileBasedDatabase2 extends Specification {

    FileService fileService = new FileService()
    JsonService jsonService = new JsonService()
    IdService idService = new IdService("test-id.txt", fileService)
    FileBasedDatabase database = new FileBasedDatabase(fileService, jsonService, "test-invoices.json", idService)
    def setup() {
        new File("test-invoices.json").createNewFile()
        new File("test-id.txt").createNewFile()
    }

    def cleanup() {
        new File("test-invoices.json").delete()
        new File("test-id.txt").delete()
    }

    def "should save invoice"() {
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
    }

    def "should get invoice by id"() {
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

        and: "the invoice is retrieved by id"
        Optional<Invoice> retrievedInvoice = database.getById(savedInvoice.getId())

        then: "the retrieved invoice is correct"
        retrievedInvoice.isPresent()
        retrievedInvoice.get() == savedInvoice
    }

    def "should update invoice"() {
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

        and: "the invoice is saved"
        Invoice savedInvoice = database.save(invoice)

        and: "an updated invoice"
        Invoice updatedInvoice = new Invoice()
        updatedInvoice.setId(savedInvoice.getId())

        when: "the invoice is updated"
        Invoice resultInvoice = database.update(savedInvoice.getId(), updatedInvoice)

        then: "the updated invoice is correct"
        resultInvoice == updatedInvoice
    }

    def "should delete invoice"() {
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

        and: "the invoice is saved"
        Invoice savedInvoice = database.save(invoice)

        when: "the invoice is deleted"
        boolean deleted = database.delete(savedInvoice.getId())

        then: "the invoice is deleted correctly"
        deleted == true
    }
}