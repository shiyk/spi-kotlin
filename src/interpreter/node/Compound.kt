package interpreter.node

/**
 * Created by yk on 17-6-2.
 *
 * 复合语句
 */
class Compound : AST() {
    val children = mutableListOf<AST>()
}
