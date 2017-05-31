package interpreter.node

import interpreter.lexer.Token

/**
 * Created by yk on 17-5-30.

 * 二元操作符
 */
class BinOp(val left: AST, val op: Token, val right: AST) : AST()
