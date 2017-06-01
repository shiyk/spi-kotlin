package interpreter.node

import interpreter.lexer.Token


/**
 * Created by yk on 17-6-1.
 *
 * 一元操作符
 */
class UnaryOp(val op: Token, val expr: AST): AST()