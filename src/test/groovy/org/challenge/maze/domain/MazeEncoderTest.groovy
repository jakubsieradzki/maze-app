package org.challenge.maze.domain

import org.challenge.maze.domain.exception.InvalidMazeFormatException
import org.challenge.maze.domain.model.Cell
import org.challenge.maze.domain.model.MazeSize
import spock.lang.Specification

class MazeEncoderTest extends Specification {
    def "should create maze cell from String"() {
        when:
        def mazeCell = MazeEncoder.decodeCell(input)

        then:
        mazeCell.row() == expectedRowIdx
        mazeCell.column() == expectedColumnIdx

        where:
        input | expectedRowIdx | expectedColumnIdx
        "A1"  | 0              | 0
        "C5"  | 4              | 2
        "Z20" | 19             | 25
        "AA1" | 0              | 26
        "AB1" | 0              | 27
        "AZ1" | 0              | 51
        "BA1" | 0              | 52
        "AH1" | 0              | 33
    }

    def "throw exception on invalid format"() {
        when:
        MazeEncoder.decodeCell(invalidInput)

        then:
        thrown(InvalidMazeFormatException)

        where:
        invalidInput << ["A", "1", "A0", "a2", "A-4", "A01", "A1da"]
    }

    def "should create valid maze size"() {
        when:
        def mazeSize = MazeEncoder.decodeSize(input)

        then:
        mazeSize.rows() == expectedRows
        mazeSize.columns() == expectedColumns

        where:
        input   | expectedRows | expectedColumns
        "8x12"  | 12           | 8
        "16x10" | 10           | 16
    }

    def "should throw exception when given size has invalid format"() {
        when:
        MazeEncoder.decodeSize(invalidInput)

        then:
        thrown(InvalidMazeFormatException)

        where:
        invalidInput << ["7x", "x8", "Ax3", "5xa", "5x03"]
    }

    def "should encode maze size"() {
        when:
        def encoded = MazeEncoder.encodeSize(new MazeSize(10, 16))

        then:
        encoded == "16x10"
    }

    def "should encode maze cell"() {
        expect:
        MazeEncoder.encodeCell(cell) == result

        where:
        cell           || result
        Cell.of(0, 0)  || "A1"
        Cell.of(2, 1)  || "B3"
        Cell.of(3, 6)  || "G4"
        Cell.of(3, 27) || "AB4"
        Cell.of(3, 51) || "AZ4"
    }
}
