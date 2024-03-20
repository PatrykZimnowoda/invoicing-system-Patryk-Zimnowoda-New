package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.model.InvoiceEntry
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService
import spock.lang.Specification
import pl.futurecollars.invoicing.model.Vat

class FileBasedDatabaseTest3 extends Specification {

    FileService fileService = new FileService()
    JsonService jsonService = new JsonService()
    FileBasedDatabase database = new FileBasedDatabase(fileService, jsonService, "test-invoices.json", "test-id.txt")

    def cleanup() {
        new File("test-invoices.json").delete()
        new File("test-id.txt").delete()
    }

    def "should update invoice when given existing id"() {
        given: "an existing invoice and an updated invoice"
        Invoice existingInvoice = new Invoice()
        existingInvoice.setId(1)
        database.save(existingInvoice)
        Invoice updatedInvoice = new Invoice()
        updatedInvoice.setId(1)
        InvoiceEntry updatedEntry = new InvoiceEntry("Service", new BigDecimal("100.00"), new BigDecimal("23.00"), Vat.VAT_23)
        updatedInvoice.setEntries([updatedEntry])

        when: "the invoice is updated"
        database.update(1, updatedInvoice)

        then: "the updated invoice is retrieved"
        Invoice retrievedInvoice = database.getById(1).get()
        retrievedInvoice.getEntries()[0].getPrice() == new BigDecimal("100.00")
    }

    def "should delete invoice when given existing id"() {
        given: "an existing invoice"
        Invoice existingInvoice = new Invoice()
        existingInvoice.setId(1)
        database.save(existingInvoice)

        when: "the invoice is deleted"
        database.delete(1)

        then: "the invoice is not found"
        !database.getById(1).isPresent()
    }

    def "should return invoice when given existing id"() {
        given: "an existing invoice"
        Invoice existingInvoice = new Invoice()
        existingInvoice.setId(1)
        database.save(existingInvoice)

        when: "the invoice is retrieved by id"
        Optional<Invoice> retrievedInvoice = database.getById(1)

        then: "the retrieved invoice matches the existing invoice"
        retrievedInvoice.isPresent()
        retrievedInvoice.get().getId() == 1
    }
}
