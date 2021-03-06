package Algo.Kasiska

import kotlin.math.pow
import kotlin.math.sqrt

class Kasiska (val cipherText : String, val validCongruenceIndex : Double = 0.065, val accuracy : Double = 0.005) {
    private val textSize : Int 
    var validAlphaFrequency : HashMap<Char, Double>
        private set
    var validAlphaAmount : Double 
        private set
    var allPeriods : List<Pair<Boolean, Int>>
        private set

    init {
        textSize = cipherText.length
        validAlphaFrequency = HashMap()
        validAlphaAmount = 0.0
        allPeriods = calculateAllPeriods()
    }

    fun log() {
        println(calculateAllPeriods())
        println(validAlphaFrequency)
        println(validAlphaAmount)
        println(validAlphaFrequency.mapValues { it.value/validAlphaAmount })
        println(validAlphaFrequency.mapValues { it.value/validAlphaAmount }.asSequence().fold(0.0) { acc, item -> 
            acc + item.value
        })
        println(getValidPeriod()) // maybe not the same as for frequency

    }

    private fun calculateValidPeriod(period : Int = 1) : Pair<Boolean, Int> {
        var alphaFrequency : HashMap<Char, Double> = HashMap()
        var alphaAmount : Double = 0.0 // alphaAmount = |text|

        // generate alphaFrequency and alphaAmount
        for (i in 0 until textSize - 1 step period) {
            val currentChar : Char = cipherText[i] 
            alphaFrequency.put(currentChar, alphaFrequency.getOrPut(currentChar) { 0.0 } + 1.0)
            ++alphaAmount
        }

        // alphaAmount^2
        alphaAmount = alphaAmount.pow(2)

        // congruenceIndex(c_1...c_n) = 1/alphaAmount^2 * sum(c_i^2)  
        val ci : Double = alphaFrequency.asSequence().fold(0.0) { acc, item -> 
            acc + item.value.pow(2)/alphaAmount
        }

        // check on valid period with accuracy
        if ((ci > validCongruenceIndex && ci - accuracy < validCongruenceIndex) ||
            (ci < validCongruenceIndex && ci + accuracy > validCongruenceIndex)) {
                validAlphaAmount = sqrt(alphaAmount) // for log
                validAlphaFrequency = alphaFrequency // for log
                return Pair(true, period)
            }

        return Pair(false, period)

    }

    // all periods by borders
    fun calculateAllPeriods(periodFrom : Int = 6, periodTo : Int = 10) : MutableList<Pair<Boolean, Int>> {
        var periodList : MutableList<Pair<Boolean, Int>> = mutableListOf()
        
        for (i in periodFrom..periodTo) {  
            periodList.add(calculateValidPeriod(i))
        }

        return periodList
    }

    // get first valid period
    fun getValidPeriod() = allPeriods.first( { it -> it.first == true } ).second

}