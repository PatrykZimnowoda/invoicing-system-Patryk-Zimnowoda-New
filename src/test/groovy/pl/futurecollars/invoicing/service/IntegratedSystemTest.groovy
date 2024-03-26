package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.TestHelpers
import pl.futurecollars.invoicing.db.memory.InMemoryDatabase
import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.service.InvoiceService

import spock.lang.Specification


class InvoiceCrudIntegrationSpec extends Specification {

    def "CRUD operations should work correctly for InvoiceService"() {
        given: "An instance of InvoiceService with InMemoryDatabase"
        InMemoryDatabase database = new InMemoryDatabase()
        InvoiceService service = new InvoiceService(database)
        Invoice invoice = TestHelpers.createTestInvoice()

        when: "Invoice is saved"
        Invoice savedInvoice = service.saveInvoice(invoice)

        then: "Saved invoice has an ID"
        savedInvoice.id != null

        when: "Invoice is read by ID"
        Invoice readInvoice = service.getInvoiceById(savedInvoice.id).get()

        then: "Read invoice is equal to the saved invoice"
        assert readInvoice.equals(savedInvoice)

        when: "Invoice is updated"
        readInvoice.entries[0].description = "Updated service"
        Invoice updatedInvoice = service.updateInvoice(readInvoice.id, readInvoice)

        then: "Updated invoice has the updated description"
        assert updatedInvoice.entries[0].description == "Updated service"

        when: "Invoice is deleted"
        service.deleteInvoice(updatedInvoice.id)

        then: "Invoice cannot be found in the database"
        !service.getInvoiceById(updatedInvoice.id).isPresent()
    }
}