package interpreter

/**
 * Created by ShiYK on 2017/5/29.
 */

fun main(args: Array<String>) {
    print("calc> ")
    while (true) {
        val line = readLine()
        if (line.equals("exit")) {
            return
        }
        if (line == null || line.isBlank()) {
            continue
        }
        val interpreter = Interpreter(Lexer(line))
        println("${interpreter.expr()}")
        print("calc> ")
    }
}