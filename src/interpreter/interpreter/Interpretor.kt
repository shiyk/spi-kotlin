package interpreter.interpreter

import interpreter.lexer.TokenType
import interpreter.node.BinOp
import interpreter.node.Num
import interpreter.parser.Parser

/**
 * Created by yk on 17-5-31.
 *
 * 遍历AST节点
 */
class Interpretor(val parser: Parser): NodeVisitor() {

    fun interpret(): Int {
        val tree = parser.parse()
        return visit(tree)
    }

    fun visitBinOp(node: BinOp) = when (node.op.type) {
        TokenType.PLUS -> visit(node.left) + visit(node.right)
        TokenType.MINUS -> visit(node.left) - visit(node.right)
        TokenType.MUL -> visit(node.left) * visit(node.right)
        TokenType.DIV -> visit(node.left) / visit(node.right)
        else -> throw Exception("Syntax Error: unexpected token $node")
    }

    fun visitNum(node: Num): Int {
        return node.value
    }
}
