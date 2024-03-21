package pl.futurecollars.invoicing.utils

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class JsonServiceTest extends Specification {

    FileService fileService = new FileService()
    String testFilePath = "test-file.txt"

    def setup() {
        Files.deleteIfExists(Paths.get(testFilePath))
    }

    def "should write and read lines"() {
        given: "some lines to write"
        List<String> lines = ["line 1", "line 2", "line 3"]

        when: "the lines are written and then read"
        fileService.writeLines(lines, testFilePath)
        List<String> readLines = fileService.readLines(testFilePath)

        then: "the read lines are the same as the written lines"
        readLines == lines
    }
}
