import kotlin.math.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.measureTimeMillis


class MonteCarloIntegration {
    companion object {

        var minRange : Double = 0.0 //x intercept of function
        var maxRange : Double = 8.0 //x intercept of function
        val numOfSamples : Int = 1000000
        var functionYValue : Double = 0.0
        var totalYValue : Double = 0.0


        @JvmStatic
        fun main(args: Array<String>) {

            var maxSize = processFunctionMaxValue()
            println(
                "Would you like to calculate the area under the curve of -(x^2 - 8x) using (1) the dart technique,\n" +
                        "or (2) the random generation technique?\nenter 1/2"
            )
            val method = readLine()!!.toInt()

            if (method == 1) {
                var time = measureTimeMillis {
                    var timesAbove = 0.0
                    var timesUnderOrOn = 0.0

                    for (i in 0..numOfSamples) {
                        var dart = tossADartAndGetItsPosition()
                        if (dart) {
                            timesUnderOrOn++
                            //println("times under = $timesUnderOrOn")
                        } else {
                            timesAbove++
                            //println("times above = $timesAbove")
                        }
                    }
                    var percentage: Double = (timesUnderOrOn / numOfSamples) * 100
                    var areaCalculated: Double = (timesUnderOrOn / numOfSamples) * (processFunction(maxSize) * maxRange)

                    println(
                        "The total number of throws under or on the curve of the function was $timesUnderOrOn.\n" +
                                "The total number of throws over the curve was $timesAbove.\n" +
                                "The percentage of these under the curve is %${percentage}, or an area of $areaCalculated \n" +
                                "compared to the actual value of the area, " +
                                "which is approximately 85.33333~, \n" +
                                "so a difference of ${(areaCalculated - 85.33333333333333).absoluteValue} over $numOfSamples throws."
                    )
                }
                println ( "Calculation took $time milliseconds.")
            } else if (method == 2) {
                var time = measureTimeMillis {
                    var randomValues = (1..numOfSamples).map {
                        ThreadLocalRandom.current().nextDouble(minRange, (maxRange + 0.0000000000000001))
                    }
                    for (i in randomValues) {
                        functionYValue = processFunction(i)
                        totalYValue += functionYValue
//                println("random x val $i results in a y value of $functionYValue and a running y total of $totalYValue")
                    }
                    totalYValue = totalYValue / numOfSamples
                    println(
                        "Our final average height is $totalYValue after $numOfSamples run samples." +
                                "\nOur area then calculates to $totalYValue * 8 which is ${totalYValue * 8}." +
                                "\nThe actual calculated area under -(x^2 -8x) is approximately 85.33333~." +
                                "\nThis is a difference of just ${((totalYValue * 8) - 85.33333333333333).absoluteValue}."
                    )
                }
                println("Calculation took $time milliseconds.")
            } else {
                println("Not a valid selection.")
            }
        }


        // calculate f(x) = -(x^2 - 8x)
        private fun processFunction( xVal : Double): Double{
            var x = xVal * xVal
            var yVal = (maxRange * xVal) - x
            return yVal
        }

        //calculate highest value of f(x) = -(x^2 - 8x)
        private fun processFunctionMaxValue() : Double {
            //use derivative of f(x) which is 8-2x to find when slope is 0
            var highest = 0

            for (i in 0..5000){
                var slopeVal = 8 - (2 * i)
                if( slopeVal == 0 ) {
                    println("x = $i is the highest value of y in our formula")
                    highest =  i
                }
            }
            return highest.toDouble()
        }

        private fun tossADartAndGetItsPosition(): Boolean {

            var dart: Dart = Dart(0.0, 0.0)
            dart.throwDart()
            dart.getDartPosition()
            var functionY = dart.x?.let { processFunction(it) } ?: 0.0
            val dartY = dart.y ?: 0.0

            return (dartY <= functionY)

        }
    }
}