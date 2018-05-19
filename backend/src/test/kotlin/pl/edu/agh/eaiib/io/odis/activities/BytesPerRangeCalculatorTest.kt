package pl.edu.agh.eaiib.io.odis.activities

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BytesPerRangeCalculatorTest {

    @Test
    fun calculateLimit1() {
        val limit: Long = 1
        val activities = mutableMapOf<Long, Long>()
        activities[1] = 100
        activities[100] = 100
        val bytesPerRangeCalculator = BytesPerRangeCalculator(activities.toMap(), limit, null)
        assertEquals(mapOf(Pair(1, 100) to 200).toString(), bytesPerRangeCalculator.calculate().toString())
    }

    @Test
    fun calculateLimit10() {
        val limit: Long = 10
        val activities = mutableMapOf<Long, Long>()
        activities[1] = 100
        activities[30] = 300
        activities[50] = 300
        activities[70] = 300
        activities[100] = 100

        val bytesPerRangeCalculator = BytesPerRangeCalculator(activities.toMap(), limit, null)
        val returnedMap = bytesPerRangeCalculator.calculate()
        assertEquals(limit.toInt(), returnedMap.size)
        assertEquals(mapOf(Pair(1, 11) to 100,
                Pair(11, 21) to 0,
                Pair(21, 31) to 300,
                Pair(31, 41) to 0,
                Pair(41, 51) to 300,
                Pair(51, 61) to 0,
                Pair(61, 71) to 300,
                Pair(71, 81) to 0,
                Pair(81, 91) to 0,
                Pair(91, 100) to 100
                ).toString(), returnedMap.toString())
    }

    @Test
    fun calculateLimit10_2() {
        val limit: Long = 10
        val activities = mutableMapOf<Long, Long>()
        activities[0] = 100
        activities[5] = 150
        activities[30] = 300
        activities[50] = 300
        activities[58] = 300
        activities[70] = 300
        activities[100] = 100

        val bytesPerRangeCalculator = BytesPerRangeCalculator(activities.toMap(), limit, null)
        val returnedMap = bytesPerRangeCalculator.calculate()
        assertEquals(limit.toInt(), returnedMap.size)
        assertEquals(mapOf(Pair(0, 10) to 250,
                Pair(10, 20) to 0,
                Pair(20, 30) to 0,
                Pair(30, 40) to 300,
                Pair(40, 50) to 0,
                Pair(50, 60) to 600,
                Pair(60, 70) to 0,
                Pair(70, 80) to 300,
                Pair(80, 90) to 0,
                Pair(90, 100) to 100
        ).toString(), returnedMap.toString())
    }

    @Test
    fun calculateLimit10_3_DataRangeRequested() {
        val limit: Long = 5
        val activities = mutableMapOf<Long, Long>()
        activities[0] = 100
        activities[5] = 150
        activities[30] = 300
        activities[50] = 300
        activities[58] = 300
        activities[59] = 300
        activities[70] = 300
        activities[100] = 100

        val bytesPerRangeCalculator = BytesPerRangeCalculator(activities.toMap(), limit, Pair(25, 65))
        val returnedMap = bytesPerRangeCalculator.calculate()
        assertEquals(limit.toInt(), returnedMap.size)
        assertEquals(mapOf(Pair(25, 33) to 300,
                Pair(33, 41) to 0,
                Pair(41, 49) to 0,
                Pair(49, 57) to 300,
                Pair(57, 65) to 600
        ).toString(), returnedMap.toString())
    }
}