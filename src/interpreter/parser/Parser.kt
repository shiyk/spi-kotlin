package interpreter.parser

import interpreter.lexer.Lexer
import interpreter.lexer.Token
import interpreter.lexer.TokenType
import interpreter.node.*

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
     * program : compound_statement DOT
     */
    fun program(): Compound {
        val node = compoundStatement()
        eat(TokenType.DOT)
        return node
    }

    /**
     * compound_statement: BEGIN statement_list END
     */
    fun compoundStatement(): Compound {
        eat(TokenType.BEGIN)
        val nodes = statementList()
        eat(TokenType.END)

        val root = Compound()
        root.children.addAll(nodes)
        return root
    }

    /**
     * statement_list : statement | statement SEMI statement_list
     */
    fun statementList(): List<AST> {
        val node = statement()
        val results = mutableListOf(node)

        while (currentToken.type == TokenType.SEMI) {
            eat(TokenType.SEMI)
            results.add(statement())
        }

        if (currentToken.type == TokenType.ID) {
            throw Exception("Unexpected token ${currentToken}")
        }

        return results
    }

    /**
     * statement : compound_statement
                    | assignment_statement
                    | empty
     */
    fun statement(): AST {
        if (currentToken.type == TokenType.BEGIN) {
            return compoundStatement()
        }
        if (currentToken.type == TokenType.ID) {
            return assignmentStatement()
        }
        return empty()
    }

    /**
     * assignment_statement : variable ASSIGN expr
     */
    fun assignmentStatement(): Assign {
        val left = variable()
        val token = currentToken
        eat(TokenType.ASSIGN)
        val right = expr()
        val node = Assign(left, token, right)
        return node
    }

    /**
     * variable : ID
     */
    fun variable(): Var {
        val node = Var(currentToken)
        eat(TokenType.ID)
        return node
    }

    /**
     * An empty production
     */
    fun empty(): NoOp {
        return NoOp()
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
     * factor: PLUS factor
     *          | MINUS factor
     *          | INTEGER
     *          | LPAREN expr RPAREN
     *          | variable
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
        if (token.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN)
            val node = expr()
            eat(TokenType.RPAREN)
            return node
        }
        return variable()
    }

    fun parse() = program()
}