package interpreter

/**
 * Created by ShiYK on 2017/5/29.
 *
 * 分词器
 */
class Lexer(val text: String) {
    var pos: Int = 0

    fun currentChar(): Char? {
        if (pos < text.length) {
            return text[pos]
        } else {
            return null
        }
    }

    fun skipWhitespace() {
        while (currentChar()?.isWhitespace() ?: false) {
            pos++
        }
    }

    fun integer(): Int {
        var result = ""
        while (currentChar()?.isDigit() ?: false) {
            result += currentChar()
            pos++
        }

        return result.toInt()
    }


    fun getNextToken(): Token {
        skipWhitespace()
        val currentChar = currentChar() ?: return Token(Token.Type.EOF, "")

        if (currentChar.isDigit()) {
            return Token(Token.Type.INTEGER, integer())
        }
        if (currentChar == '+') {
            pos++
            return Token(Token.Type.PLUS, '+')
        }
        if (currentChar == '-') {
            pos++
            return Token(Token.Type.MINUS, '-')
        }
        if (currentChar == '*') {
            pos++
            return Token(Token.Type.MUL, '*')
        }
        if (currentChar == '/') {
            pos++
            return Token(Token.Type.DIV, '/')
        }
        if (currentChar == '(') {
            pos++
            return Token(Token.Type.LPAREN, '(')
        }
        if (currentChar == ')') {
            pos++
            return Token(Token.Type.RPAREN, ')')
        }

        throw Exception("Invalid Character $currentChar")
    }


}
