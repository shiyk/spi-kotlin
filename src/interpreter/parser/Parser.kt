package interpreter.parser

import interpreter.lexer.Lexer
import interpreter.lexer.Token
import interpreter.lexer.TokenType
import interpreter.node.AST
import interpreter.node.BinOp
import interpreter.node.Num
import interpreter.node.UnaryOp

/**
 * Created by yk on 2017/5/27.
 *
 * AST解析器
 */

class Parser(val lexer: Lexer) {

    var currentToken: Token = lexer.getNextToken()

    fun eat(type: TokenType) {
        if (currentToken.type == type) {
            currentToken = lexer.getNextToken()
        } else {
            throw Exception("Syntax Error: expected $type, found ${currentToken.type}")
        }
    }

    /**
     * factor: (PLUS | MINUS) factor | INTEGER | LPAREN expr RPAREN
     */
    fun factor(): AST {
        val token = currentToken
        if (token.type == TokenType.PLUS) {
            eat(TokenType.PLUS)
            return UnaryOp(token, factor())
        }
        if (token.type == TokenType.MINUS) {
            eat(TokenType.MINUS)
            return UnaryOp(token, factor())
        }
        if (token.type == TokenType.INTEGER) {
            eat(TokenType.INTEGER)
            return Num(token)
        }

        eat(TokenType.LPAREN)
        val node = expr()
        eat(TokenType.RPAREN)
        return node
    }

    /**
     * term: factor ((MUL | DIV) factor)*
     */
    fun term(): AST {
        var node: AST = factor()

        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            val token = currentToken
            if (token.type == TokenType.MUL) {
                eat(TokenType.MUL)
            } else if (token.type == TokenType.DIV) {
                eat(TokenType.DIV)
            }

            node = BinOp(node, token, factor())
        }

        return node
    }

    /**
     * expr     : term ((PLUS | MINUS) term)*
     * term     : factor ((MUL | DIV) factor)*
     * factor   : (PLUS | MINUS) factor | INTEGER | LPAREN expr RPAREN
     */
    fun expr(): AST {
        var node = term()

        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            val token = currentToken
            if (token.type == TokenType.PLUS) {
                eat(TokenType.PLUS)
            } else if (token.type == TokenType.MINUS) {
                eat(TokenType.MINUS)
            }

            node = BinOp(node, token, term())
        }

        return node
    }

    fun parse() = expr()
}