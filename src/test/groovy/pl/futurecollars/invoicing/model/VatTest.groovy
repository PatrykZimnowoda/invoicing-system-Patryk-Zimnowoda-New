package pl.futurecollars.invoicing.model

import spock.lang.Specification

import spock.lang.Specification

class VatTest extends Specification {

    def "Vat enum should have correct rates"() {
        expect:
        Vat.VAT_23.rate == 23
        Vat.VAT_8.rate == 8
        Vat.VAT_5.rate == 5
        Vat.VAT_0.rate == 0
    }

    def "Vat should return correct rate for each value"() {
        expect:
        Vat.valueOf("VAT_23").getRate() == 23
        Vat.valueOf("VAT_8").getRate() == 8
        Vat.valueOf("VAT_5").getRate() == 5
        Vat.valueOf("VAT_0").getRate() == 0
    }
}