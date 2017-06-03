package interpreter

import interpreter.interpreter.Interpretor
import interpreter.lexer.Lexer
import interpreter.parser.Parser
import java.io.File

/**
 * Created by yk on 2017/5/29.
 *
 * 测试
 */
fun main(args: Array<String>) {
    val text = File(args[0]).readText()
    val lexer = Lexer(text)
    val parser = Parser(lexer)
    val interpreter = Interpretor(parser)
    interpreter.interpret()
    println("${interpreter.GLOBAL_SCOPE}")
}