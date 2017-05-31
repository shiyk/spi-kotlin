package interpreter.lexer

/**
 * Created by hasee on 2017/5/27.
 *
 * Token
 */
class Token(val type: TokenType, val value: Any) {
    override fun toString() : String {
        return "Token($type, $value)"
    }
}