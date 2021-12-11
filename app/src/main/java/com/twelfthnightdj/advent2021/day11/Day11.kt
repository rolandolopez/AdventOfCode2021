package com.twelfthnightdj.advent2021.day11

import android.graphics.Point
import com.twelfthnightdj.advent2021.AocDays
import com.twelfthnightdj.advent2021.util.InputHelpers

class Day11 : AocDays() {
    private lateinit var ocean: MutableList<MutableList<Int>>
    var alreadyFlashed = mutableSetOf<Point>()
    var totalFlashes = 0

    override fun partA(): String {
        ocean = processInput(input)
        val maxX = ocean.size
        val maxY = ocean[0].size
        (0..99).forEach {
            alreadyFlashed.clear()
            miniStepA()
            while (miniStepB(maxX, maxY) > 0) {
            }
            miniStepC()
        }
        return totalFlashes.toString()
    }

    private fun miniStepC() {
        ocean.forEachIndexed { x, list ->
            list.forEachIndexed { y, value ->
                if (value > 9) {
                    ocean[x][y] = 0
                }
            }
        }
    }
    private fun miniStepB(maxX: Int, maxY: Int): Int {
        var flashes = 0
        var stepUp = mutableSetOf<Point>()
        ocean.forEachIndexed { x, list ->
            list.forEachIndexed { y, value ->
                val point = Point(x, y)
                if ((value >= 10) && (!alreadyFlashed.contains(point))) {
                    flashes++
                    stepUp.add(point)
                    ocean[x][y] = 11
                    alreadyFlashed.add(point)
                }
            }
        }

        stepUp.forEach { point ->
            (-1..1).forEach { dx ->
                (-1..1).forEach { dy ->
                    val newPoint = Point(point.x + dx, point.y + dy)
                    if (newPoint.x in (0 until maxX) && (newPoint.y in (0 until maxY))) {
                        if (newPoint != point) {
                            ocean[newPoint.x][newPoint.y]++
                        }
                    }
                }
            }
        }
        totalFlashes += flashes
        return flashes
    }

    private fun miniStepA() {
        ocean.forEachIndexed { x, list ->
            list.forEachIndexed { y, value ->
                ocean[x][y]++
            }
        }
    }

    private fun processInput(ipt: List<String>) =
        ipt.map { it.toCharArray().map { it.digitToInt() }.toMutableList() }.toMutableList()

    fun MutableList<MutableList<Int>>.prettyPrint() {
        this.forEachIndexed { x, list ->
            println("$list")
        }
    }
    val trialInput = InputHelpers.getListOfStringsFromFile("/day11trial.txt")
    private val input = InputHelpers.getListOfStringsFromFile("/day11.txt")
}
