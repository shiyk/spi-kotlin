package interpreter.interpreter

import interpreter.lexer.TokenType
import interpreter.node.*
import interpreter.parser.Parser

/**
 * Created by yk on 17-5-31.
 *
 * 遍历AST节点
 */
class Interpretor(val parser: Parser): NodeVisitor() {

    val GLOBAL_SCOPE = mutableMapOf<String, Any>()

    fun interpret(){
        val tree = parser.parse()
        visit(tree)
    }

    fun visitCompound(node: Compound) {
        for (child in node.children) {
            visit(child)
        }
    }

    fun visitNoOp(node: NoOp) {
    }

    fun visitAssign(node: Assign) {
        val varName = node.left.value as String
        GLOBAL_SCOPE[varName] = eval(node.right)
    }

    fun visitVar(node: Var): Any {
        val varName = node.value as String
        val value = GLOBAL_SCOPE[varName] ?: throw Exception("Name Error: $varName")
        return value
    }

    fun visitBinOp(node: BinOp): Int {
        val left = eval(node.left)
        val right = eval(node.right)
        return when (node.op.type) {
            TokenType.PLUS -> left + right
            TokenType.MINUS -> left - right
            TokenType.MUL -> left * right
            TokenType.DIV -> left / right
            else -> throw Exception("Syntax Error: unexpected token $node")
        }
    }

    fun visitNum(node: Num): Int {
        return node.value
    }

    fun visitUnaryOp(node: UnaryOp) = when (node.op.type) {
        TokenType.PLUS -> visit(node.expr)
        TokenType.MINUS -> - (visit(node.expr) as Int)
        else -> throw Exception("Syntax Error: unexpected token $node")
    }
}
