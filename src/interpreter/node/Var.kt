package interpreter.node

import interpreter.lexer.Token

/**
 * Created by yk on 17-6-2.
 *
 * 变量
 */
class Var(val token: Token): AST() {
    val value = token.value
}
