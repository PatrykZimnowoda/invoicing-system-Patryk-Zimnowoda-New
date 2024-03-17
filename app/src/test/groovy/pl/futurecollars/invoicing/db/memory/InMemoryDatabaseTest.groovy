package pl.futurecollars.invoicing.db.memory

import pl.futurecollars.invoicing.model.Invoice
import spock.lang.Specification

class InMemoryDatabaseTest extends Specification {

    InMemoryDatabase database = new InMemoryDatabase()

    def "save invoice should add invoice to database"() {
        given:
        Invoice invoice = new Invoice(id: 1,)

        when:
        database.save(invoice)

        then:
        assert database.getAll().size() == 1
        assert database.getById(1).get() == invoice
    }

    def "getById should return correct invoice"() {
        given:
        Invoice invoice1 = new Invoice(id: 1,)
        Invoice invoice2 = new Invoice(id: 2,)
        database.save(invoice1)
        database.save(invoice2)

        when:
        Optional<Invoice> result = database.getById(1)

        then:
        assert result.isPresent()
        assert result.get() == invoice1
    }

    def "getAll should return all invoices"() {
        given:
        Invoice invoice1 = new Invoice(id: 1,)
        Invoice invoice2 = new Invoice(id: 2,)
        database.save(invoice1)
        database.save(invoice2)

        when:
        List<Invoice> result = database.getAll()

        then:
        assert result.size() == 2
        assert result.containsAll([invoice1, invoice2])
    }

    def "update should modify existing invoice"() {
        given:
        Invoice originalInvoice = new Invoice(id: 1,)
        database.save(originalInvoice)
        Invoice updatedInvoice = new Invoice(id: 1,)

        when:
        database.update(1, updatedInvoice)

        then:
        assert database.getById(1).get() == updatedInvoice
    }

    def "delete should remove invoice from database"() {
        given:
        Invoice invoice = new Invoice(id: 1,)
        database.save(invoice)

        when:
        database.delete(1)

        then:
        assert database.getAll().isEmpty()
    }
}