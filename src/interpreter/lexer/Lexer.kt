package interpreter.lexer

/**
 * Created by yk on 2017/5/29.
 *
 * 分词器
 */
class Lexer(val text: String) {
    var pos = 0

    val RESERVED_KEYWORDS = mapOf(
            "BEGIN" to Token(TokenType.BEGIN, "BEGIN"),
            "END" to Token(TokenType.END, "END")
    )

    fun currentChar() = if (pos < text.length) {
        text[pos]
    } else {
        null
    }

    fun peek() = if (pos + 1 < text.length) {
        text[pos + 1]
    } else {
         null
    }

    fun skipWhitespace() {
        while (currentChar()?.isWhitespace() ?: false) {
            pos++
        }
    }

    fun id(): Token {
        var result = ""
        while (currentChar()?.isLetterOrDigit() ?: false) {
            result += currentChar()
            pos++
        }

        return RESERVED_KEYWORDS.getOrDefault(result, Token(TokenType.ID, result))
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

        if (currentChar.isLetter()) {
            return id()
        }
        if (currentChar == ':' && peek() == '=') {
            pos += 2
            return Token(TokenType.ASSIGN, ":=")
        }
        if (currentChar == ';') {
            pos++
            return Token(TokenType.SEMI, ';')
        }
        if (currentChar == '.') {
            pos++
            return Token(TokenType.DOT, '.')
        }
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
