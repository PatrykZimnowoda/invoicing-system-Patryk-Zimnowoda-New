package pl.futurecollars.invoicing.db.file

import pl.futurecollars.invoicing.model.Invoice
import pl.futurecollars.invoicing.utils.FileService
import pl.futurecollars.invoicing.utils.JsonService
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class FileBasedDatabaseMultiInvoices extends Specification {

    FileService fileService = new FileService()
    JsonService jsonService = new JsonService()
    FileBasedDatabase database = new FileBasedDatabase(fileService, jsonService, "test-invoices.json", "test-id.txt")

    def setup() {
        if (!Files.exists(Paths.get("test-id.txt"))) {
            new File("test-id.txt").createNewFile()
        }
    }

    def cleanup() {
        new File("test-invoices.json").delete()
        new File("test-id.txt").delete()
    }

    def "should return all invoices when getting all invoices"() {

        given: "multiple invoices in the database"
        Invoice invoice1 = new Invoice()
        database.save(invoice1)
        Invoice invoice2 = new Invoice()
        database.save(invoice2)

        when: "all invoices are retrieved"
        List<Invoice> invoices = database.getAll()

        then: "all invoices are returned"
        invoices.size() == 2
    }
}