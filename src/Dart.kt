import kotlin.random.Random

data class Dart(var x: Double?, var y: Double?){

    fun throwDart(){
        this.x = Random.nextDouble(0.0, 8.0)
        this.y = Random.nextDouble(0.0, 16.0)
    }

    fun getDartPosition(){
        println("This Dart's position is (${this.x},${this.y})")
    }
}