package interpreter.lexer

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
        val currentChar = currentChar() ?: return Token(TokenType.EOF, "")

        if (currentChar.isDigit()) {
            return Token(TokenType.INTEGER, integer())
        }
        if (currentChar == '+') {
            pos++
            return Token(TokenType.PLUS, '+')
        }
        if (currentChar == '-') {
            pos++
            return Token(TokenType.MINUS, '-')
        }
        if (currentChar == '*') {
            pos++
            return Token(TokenType.MUL, '*')
        }
        if (currentChar == '/') {
            pos++
            return Token(TokenType.DIV, '/')
        }
        if (currentChar == '(') {
            pos++
            return Token(TokenType.LPAREN, '(')
        }
        if (currentChar == ')') {
            pos++
            return Token(TokenType.RPAREN, ')')
        }

        throw Exception("Invalid Character $currentChar")
    }


}
