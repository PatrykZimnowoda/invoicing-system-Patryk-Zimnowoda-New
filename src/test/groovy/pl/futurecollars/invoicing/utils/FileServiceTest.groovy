package pl.futurecollars.invoicing.utils

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class FileServiceTest extends Specification {
    FileService fileService = new FileService()
    String testFilePath = "test-file.txt"

    def setup() {
        Files.deleteIfExists(Paths.get(testFilePath))
        Files.createFile(Paths.get(testFilePath))
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

    def "should write line to file"() {
        given: "a line to write"
        String line = "Test line"

        when: "the line is written"
        fileService.writeLine(line, testFilePath)

        then: "the written line is correct"
        List<String> lines = fileService.readLines(testFilePath)
        lines.size() == 1
        lines.get(0) == line
    }

    def "should append lines to file"() {
        given: "some lines to append"
        List<String> linesToAppend = ["line 4", "line 5", "line 6"]

        when: "the lines are appended"
        fileService.appendLines(linesToAppend, testFilePath)

        then: "the appended lines are correct"
        List<String> lines = fileService.readLines(testFilePath)
        lines.size() == 3
        lines.containsAll(linesToAppend)
    }

    def "should append line to file"() {
        given: "a line to append"
        String lineToAppend = "line 7"

        when: "the line is appended"
        fileService.appendLine(lineToAppend, testFilePath)

        then: "the appended line is correct"
        List<String> lines = fileService.readLines(testFilePath)
        lines.size() == 1
        lines.get(0) == lineToAppend
    }

}
