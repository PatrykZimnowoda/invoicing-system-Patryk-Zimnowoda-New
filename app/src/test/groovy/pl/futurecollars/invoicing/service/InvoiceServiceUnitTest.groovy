package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification


class InvoiceServiceUnitTest extends Specification {
    def database = Mock(Database)
    def invoiceService = new InvoiceService(database)

    def "saveInvoice should call database save method"() {
        given:
        def invoice = new Invoice()

        when:
        invoiceService.saveInvoice(invoice)

        then:
        1 * database.save(invoice)
    }

    def "getInvoiceById should call database getById method"() {
        given:
        def invoiceId = 1
        database.getById(_ as int) >> Optional.of(new Invoice())

        when:
        invoiceService.getInvoiceById(invoiceId)

        then:
        1 * database.getById(invoiceId)
    }

    def "updateInvoice should call database update method"() {
        given:
        def invoiceId = 1
        def invoice = new Invoice()
        invoice.setId(invoiceId)
        database.update(invoiceId, invoice) >> invoice

        when:
        invoiceService.updateInvoice(invoiceId, invoice)

        then:
        1 * database.update(invoiceId, invoice)
    }

    def "deleteInvoice should call database delete method"() {
        given:
        def invoiceId = 1
        database.delete(invoiceId) >> true

        when:
        invoiceService.deleteInvoice(invoiceId)

        then:
        1 * database.delete(invoiceId)
    }

    def "getAllInvoices should call database getAll method"() {
        given:
        database.getAll() >> []

        when:
        invoiceService.getAllInvoices()

        then:
        1 * database.getAll()
    }


}