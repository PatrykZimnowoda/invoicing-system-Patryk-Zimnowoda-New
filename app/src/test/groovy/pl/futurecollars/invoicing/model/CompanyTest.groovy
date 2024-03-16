package pl.futurecollars.invoicing.model

import spock.lang.Specification

class CompanySpec extends Specification {
    def "should correctly create Company instance"() {
        given:
        def taxId = "123-456-78-90"
        def address = "Some address 123"

        when:
        def company = new Company(1, taxId, address)

        then:
        company.id == 1
        company.taxIdentificationNumber == taxId
        company.address == address
    }
}