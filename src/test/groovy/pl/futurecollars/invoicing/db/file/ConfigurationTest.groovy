import pl.futurecollars.invoicing.db.Database
import pl.futurecollars.invoicing.db.file.Configuration
import pl.futurecollars.invoicing.db.file.FileBasedDatabase
import spock.lang.Specification

class ConfigurationTest extends Specification {

    def "should return FileBasedDatabase instance when database method is called"() {
        given: "a Configuration instance"
        Configuration configuration = new Configuration()

        when: "database method is called"
        Database database = configuration.database()

        then: "the result is an instance of FileBasedDatabase"
        assert database instanceof FileBasedDatabase
    }
}