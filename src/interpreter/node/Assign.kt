package interpreter.node

import interpreter.lexer.Token

/**
 * Created by yk on 17-6-2.
 *
 * 赋值语句
 */
class Assign(val left: Var, val op: Token, val right: AST): AST()
