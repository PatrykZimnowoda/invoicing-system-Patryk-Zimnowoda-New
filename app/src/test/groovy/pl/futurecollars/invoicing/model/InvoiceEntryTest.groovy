package pl.futurecollars.invoicing.model

import spock.lang.Specification


class InvoiceEntrySpec extends Specification {

    def "should create invoice entry with correct values"() {
        given:
        String description = "Test Description"
        BigDecimal price = BigDecimal.valueOf(100.0)
        BigDecimal vatValue = BigDecimal.valueOf(23.0)
        Vat vatRate = Vat.VAT_23

        when:
        InvoiceEntry entry = new InvoiceEntry(description, price, vatValue, vatRate)

        then:
        entry.description == description
        entry.price == price
        entry.vatValue == vatValue
        entry.vatRate == vatRate
    }

    def "equals and hashCode should work correctly"() {
        given:
        InvoiceEntry entry1 = new InvoiceEntry("Description", BigDecimal.valueOf(100), BigDecimal.valueOf(23), Vat.VAT_23)
        InvoiceEntry entry2 = new InvoiceEntry("Description", BigDecimal.valueOf(100), BigDecimal.valueOf(23), Vat.VAT_23)
        InvoiceEntry entry3 = new InvoiceEntry("Different", BigDecimal.valueOf(200), BigDecimal.valueOf(23), Vat.VAT_8)

        expect:
        entry1.equals(entry2)
        !entry1.equals(entry3)
        entry1.hashCode() == entry2.hashCode()
        entry1.hashCode() != entry3.hashCode()
    }

    def "toString should include description, price, vatValue, and vatRate"() {
        given:
        InvoiceEntry entry = new InvoiceEntry("Description", BigDecimal.valueOf(100), BigDecimal.valueOf(23), Vat.VAT_23)

        expect:
        entry.toString().contains("Description")
        entry.toString().contains("100")
        entry.toString().contains("23")
        entry.toString().contains("VAT_23")
    }
}