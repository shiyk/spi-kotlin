package interpreter.lexer

enum class TokenType {
    BEGIN, END, DOT, ASSIGN, SEMI, ID,
    INTEGER, PLUS, MINUS, MUL, DIV, LPAREN, RPAREN, EOF
}