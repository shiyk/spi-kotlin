package interpreter

/**
 * Created by hasee on 2017/5/27.
 *
 * Token
 */
class Token(val type: Type, val value: Any) {

    enum class Type {
        INTEGER, PLUS, MINUS, MUL, DIV, LPAREN, RPAREN, EOF
    }

    override fun toString() : String {
        return "Token($type, $value)"
    }
}