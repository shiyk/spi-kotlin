package interpreter.node

import interpreter.lexer.Token

/**
 * Created by yk on 17-5-30.
 *
 * 数字
 */
class Num(val token: Token): AST() {
    val value = token.value as Int
}
