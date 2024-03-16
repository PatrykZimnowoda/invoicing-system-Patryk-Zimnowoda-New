package pl.futurecollars.invoicing.service

import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification


class InvoiceServiceSpec extends Specification {
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
}