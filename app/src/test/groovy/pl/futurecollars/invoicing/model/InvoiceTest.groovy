package pl.futurecollars.invoicing.model

import spock.lang.Specification

import java.time.LocalDate


class InvoiceTest extends Specification {

    def "should create invoice with all fields initialized"() {
        given:
        Integer id = 1
        LocalDate date = LocalDate.now()
        Company companyFrom = new Company(1, "1234567890", "Company From Address")
        Company companyTo = new Company(2, "0987654321", "Company To Address")
        InvoiceEntry entry = new InvoiceEntry("Service", new BigDecimal("100.00"), new BigDecimal("23.00"), Vat.VAT_23)
        List<InvoiceEntry> entries = [entry]

        when:
        Invoice invoice = new Invoice(id, date, companyFrom, companyTo, entries)

        then:
        invoice.getId() == id
        invoice.getDate() == date
        invoice.getCompanyFrom() == companyFrom
        invoice.getCompanyTo() == companyTo
        invoice.getEntries() == entries
    }

    def "should allow modification of invoice fields"() {
        given:
        Invoice invoice = new Invoice()
        LocalDate newDate = LocalDate.now()
        Company newCompanyFrom = new Company(3, "1122334455", "New Company From Address")
        Company newCompany
    }
}