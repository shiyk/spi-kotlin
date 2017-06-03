package interpreter.interpreter

import interpreter.node.AST
import kotlin.reflect.full.declaredFunctions

/**
 * Created by yk on 17-5-31.
 *
 * 访问者
 */
open class NodeVisitor {
    fun visit(node: AST): Any? {
        val methodName = "visit${node::class.simpleName}"

        val method = this::class
                .declaredFunctions
                .firstOrNull {
                    it.name == methodName
                }

        if (method != null) {
            return method.call(this, node)
        }

        throw Exception("No $methodName")
    }

    fun eval(node: AST): Int {
        val r = visit(node)
        if (r is Int) {
            return r
        }
        throw Exception("Expecting an expression")
    }
}
