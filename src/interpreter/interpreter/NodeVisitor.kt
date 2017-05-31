package interpreter.interpreter

import interpreter.node.AST
import kotlin.reflect.full.declaredFunctions

/**
 * Created by yk on 17-5-31.
 *
 * 访问者
 */
open class NodeVisitor {
    fun visit(node: AST): Int {
        val methodName = "visit${node::class.simpleName}"

        val method = this::class
                .declaredFunctions
                .firstOrNull {
                    it.name == methodName
                }

        if (method != null) {
            return method.call(this, node) as Int
        } else {
            throw Exception("No $methodName")
        }
    }
}
