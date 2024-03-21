package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService
import spock.lang.Specification

class FileBasedDatabaseTest extends Specification {

    FileService fileService = new FileService()
    JsonService jsonService = new JsonService()
    FileBasedDatabase database = new FileBasedDatabase(fileService, jsonService, "test-invoices.json", "test-id.txt")

    def cleanup() {
        new File("test-invoices.json").delete()
        new File("test-id.txt").delete()
    }

    def setup() {
        new File("test-invoices.json").createNewFile()
        new File("test-id.txt").createNewFile()
    }

    def "should throw exception when updating non-existing invoice"() {
        given: "a non-existing invoice id and an updated invoice"
        int nonExistingId = 999
        Invoice updatedInvoice = new Invoice()

        when: "the invoice is updated"
        database.update(nonExistingId, updatedInvoice)

        then: "an IllegalArgumentException is thrown"
        thrown(IllegalArgumentException)
    }

    def "should return false when deleting non-existing invoice"() {
        given: "a non-existing invoice id"
        int nonExistingId = 999

        when: "the invoice is deleted"
        boolean result = database.delete(nonExistingId)

        then: "the result is false"
        result == false
    }

    def "should return empty optional when getting non-existing invoice"() {
        given: "a non-existing invoice id"
        int nonExistingId = 999

        when: "the invoice is retrieved"
        Optional<Invoice> result = database.getById(nonExistingId)

        then: "the result is an empty optional"
        !result.isPresent()
    }
}