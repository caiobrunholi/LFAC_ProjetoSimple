// Arquivo para o scanner a ser utilizado
package parser;
// Importar classes do cup - classe Symbol
import java_cup.runtime.*;

%%

%class Scanner
%cup
%unicode
%line
%column
%yylexthrow Exception

%{
    // type é a classe do token
    // yyline e yycolumn são variáveis reservadas
    // do JFlex para armazenar a linha e a coluna
    // de um token encontrado na entrada (precisa
    //  usar %line e %column)
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

ws = [\ \t\f\r\n]
digit = [0-9]
number = \d+(\.\d+)?(["E""e"]["+""-"]?\d+)?
identifier = [A-Za-z][A-Za-z0-9]*

%%
"PI"            { return symbol(sym.PI, Math.PI); }
"sin"           { return symbol(sym.SIN); }
"cos"           { return symbol(sym.COS); }
"print"         { return symbol(sym.PRINT); }
"read"          { return symbol(sym.READ); }
"if"            { return symbol(sym.IF); }
"then"          { return symbol(sym.THEN); }
"else"          { return symbol(sym.ELSE); }
"and"           { return symbol(sym.AND); }
{identifier}    { return symbol(sym.ID, yytext()); }
"="             { return symbol(sym.ASSIGN); }
";"             { return symbol(sym.SEMI); }
"+"             { return symbol(sym.PLUS); }
"-"             { return symbol(sym.MINUS); }
"*"             { return symbol(sym.TIMES); }
"/"             { return symbol(sym.DIVIDE); }
"%"             { return symbol(sym.MOD); }
"**"            { return symbol(sym.EXP); }
"("             { return symbol(sym.LPAREN); }
")"             { return symbol(sym.RPAREN); }
"{"             { return symbol(sym.LBRACE); }
"}"             { return symbol(sym.RBRACE); }
"=="            { return symbol(sym.EQ); }
"!="            { return symbol(sym.NEQ); }
"<"             { return symbol(sym.LT); }

{number}        { return symbol(sym.NUMBER,
                                Double.valueOf(yytext())); }
{ws}            {/* Ignore */}
.               { throw new Exception("Símbolo inválido: \"" + yycharat(0) +
                    "\" na linha " + yyline + ", coluna " + yycolumn); }
