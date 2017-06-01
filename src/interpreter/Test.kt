package interpreter

import interpreter.interpreter.Interpretor
import interpreter.lexer.Lexer
import interpreter.parser.Parser

/**
 * Created by yk on 2017/5/29.
 *
 * 测试
 */
fun main(args: Array<String>) {
    print("spi> ")
    while (true) {
        val line = readLine()
        if (line.equals("exit")) {
            return
        }
        if (line == null || line.isBlank()) {
            continue
        }
        val lexer = Lexer(line)
        val parser = Parser(lexer)
        val interpreter = Interpretor(parser)
        println("${interpreter.interpret()}")
        print("spi> ")
    }
}