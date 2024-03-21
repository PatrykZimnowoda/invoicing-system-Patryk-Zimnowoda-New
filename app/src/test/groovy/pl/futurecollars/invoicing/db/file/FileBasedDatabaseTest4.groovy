package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService
import spock.lang.Specification

class FileBasedDatabaseTest4 extends Specification {

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

    def "should read multiple invoices from json"() {
        given: "two invoices"
        Invoice invoice1 = new Invoice()
        Invoice invoice2 = new Invoice()

        when: "the invoices are saved"
        Invoice savedInvoice1 = database.save(invoice1)
        Invoice savedInvoice2 = database.save(invoice2)

        and: "the invoices are read from the database"
        List<Invoice> retrievedInvoices = database.getAll()

        then: "the retrieved invoices match the saved invoices"
        retrievedInvoices.size() == 2
        retrievedInvoices.contains(savedInvoice1)
        retrievedInvoices.contains(savedInvoice2)
    }
}