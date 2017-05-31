package interpreter

/**
 * Created by ShiYK on 2017/5/27.
 *
 * 递归向下解释器-简单的计算器
 */

class Interpreter(val lexer: Lexer) {

    var currentToken: Token = lexer.getNextToken()

    fun eat(type: Token.Type) {
        if (currentToken.type == type) {
            currentToken = lexer.getNextToken()
        } else {
            throw Exception("Syntax Error: expected $type, found ${currentToken.type}")
        }
    }

    fun factor(): Int {
        if (currentToken.type == Token.Type.INTEGER) {
            val int = currentToken.value as Int
            eat(Token.Type.INTEGER)
            return int
        }

        eat(Token.Type.LPAREN)
        val int = expr()
        eat(Token.Type.RPAREN)
        return int
    }

    fun term(): Int {
        var result = factor()

        while (currentToken.type == Token.Type.MUL || currentToken.type == Token.Type.DIV) {
            if (currentToken.type == Token.Type.MUL) {
                eat(Token.Type.MUL)
                result *= factor()
            } else if (currentToken.type == Token.Type.DIV) {
                eat(Token.Type.DIV)
                result /= factor()
            }
        }

        return result
    }

    fun expr(): Int {
        var result = term()

        while (currentToken.type == Token.Type.PLUS || currentToken.type == Token.Type.MINUS) {
            if (currentToken.type == Token.Type.PLUS) {
                eat(Token.Type.PLUS)
                result += term()
            } else if (currentToken.type == Token.Type.MINUS) {
                eat(Token.Type.MINUS)
                result -= term()
            }
        }

        return result
    }

}