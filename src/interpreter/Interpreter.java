package interpreter;

// Classe para interpretar o código que está representado em uma AST produzida
// pelo analisador sintático
import java.util.HashMap; //para armazenar as variáveis e seus valores
import parser.sym; //para obter as constantes de terminais
import ast.AST; //árvore AST
import ast.Identifier; //AST de identificador
import ast.Number; //AST de número
import ast.Operator; //AST de operador
import ast.CondOperator; //AST de operador condicional (if-then-else)
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Interpreter {

    // Tabela de símbolos
    private static final HashMap<String, Double> symbolTable = new HashMap<>();

    // O método interpret() aplica um percorrimento pós-ordem (filho esquerdo,
    // filho direito e raiz, de modo a obter o operando esquerdo, operando
    // direito. Depois, verificar qual é o tipo de árvore (operador, número
    // ou identificador) e então executar uma ação específica.
    public static void interpret(AST ast) throws Exception {
        // se a árvore não for nula
        if (ast != null) {
            // se for um if-then ou if-then-else
            if (ast instanceof CondOperator) {
                // obter referência para o operador
                CondOperator op = (CondOperator) ast;
                // daí obter a expressão relacional
                AST relExp = op.getRelStmt();
                // interpretar esta expressão
                interpret(relExp);
                // neste caso, deve-se verificar qual o valor booleano
                // retornado pela condição do operador
                Boolean b = (Boolean) relExp.getValue();
                // de acordo com o valor da expressão, interpretar o
                // lado esquerdo (if) ou direito (else), se existir
                if (b == true) {
                    interpret(ast.getLeftAST());
                } else if (ast.getRightAST() != null) {
                    interpret(ast.getRightAST());
                }
            } else { // comandos sequenciais
                // processar a AST esquerda
                interpret(ast.getLeftAST());
                // processar a AST direita
                interpret(ast.getRightAST());
                // se for operador
                if (ast instanceof Operator) {
                    // obter a árvore com o operador
                    Operator operator = (Operator) ast;
                    // obter o valor do operador
                    int op = operator.getOperatorSymbol();
                    // operadores SEMPRE terão, no mínimo, o lado esquerdo
                    // então já pode salvá-lo aqui
                    AST leftAST = operator.getLeftAST();
                    // este switch executa cada operador separadamente
                    switch (op) {
                        case sym.PRINT: {// exibir na tela
                            Double value = (Double) leftAST.getValue();
                            // para imprimir resultados na tela
                            System.out.println("Valor: " + value);
                            break;
                        }
                        case sym.READ: {
                            // obter o nome do identificador
                            String id = ((Identifier) operator.getLeftAST())
                                    .getIdentifierName();
                            // ler seu valor
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(System.in));
                            // prompt
                            System.out.print("(" + id + ")? ");
                            Double val = Double.parseDouble(reader.readLine());
                            // adicionar ou atualizar a variável
                            symbolTable.put(id, val);
                            break;
                        }
                        case sym.ASSIGN: { // atribuição
                            // obter o identificador
                            String id = ((Identifier) leftAST).getIdentifierName();
                            // obter o lado direito (valor) que está na pilha
                            AST rightAST = operator.getRightAST();
                            Double value = (Double) rightAST.getValue();
                            // adicionar à tabela
                            symbolTable.put(id, value);
                            break;
                        }
                        case sym.PLUS: {// soma
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1 + v2);
                            break;
                        }
                        case sym.MINUS: { // subtração
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1 - v2);
                            break;
                        }
                        case sym.UMINUS: { // inversão de sinal
                            Double v = (Double) leftAST.getValue();
                            operator.setValue(-v);
                            break;
                        }
                        case sym.TIMES: { // multiplicação
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1 * v2);
                            break;
                        }
                        case sym.DIVIDE: { // divisão
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1 / v2);
                            break;
                        }
                        case sym.MOD: { // resto
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1 % v2);
                            break;
                        }
                        case sym.EXP: {// exponenciação
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(Math.pow(v1, v2));
                            break;
                        }
                        case sym.COS: { // coseno
                            Double v = (Double) leftAST.getValue();
                            operator.setValue(Math.cos(v));
                            break;
                        }
                        case sym.SIN: { // seno
                            Double v = (Double) leftAST.getValue();
                            operator.setValue(Math.sin(v));
                            break;
                        }
                        case sym.EQ: {
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1.doubleValue() == v2.doubleValue());
                            break;
                        }
                        case sym.NEQ: {
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1.doubleValue() != v2.doubleValue());
                            break;
                        }
                        case sym.LT: {
                            Double v1 = (Double) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Double v2 = (Double) rightAST.getValue();
                            operator.setValue(v1.doubleValue() < v2.doubleValue());
                            break;
                        }
                        case sym.AND: {
                            Boolean b1 = (Boolean) leftAST.getValue();
                            AST rightAST = operator.getRightAST();
                            Boolean b2 = (Boolean) rightAST.getValue();
                            operator.setValue(b1.booleanValue() && b2.booleanValue());
                            break;
                        }
                    }
                } else if (ast instanceof Number) {
                    // se for número, basta armazená-lo no próprio
                    // campo valor da árvore
                    Number number = (Number) ast;
                    number.setValue(number.getValue());
                } else if (ast instanceof Identifier) {
                    // obter o identificador
                    Identifier id = (Identifier) ast;
                    // veja se ele está definido na tabela de símbolos
                    Double value = symbolTable.get(id.getIdentifierName());
                    // se estiver na tabela de símbolos, basta retornar seu
                    // conteúdo na pilha
                    if (value != null) {
                        // armazenar no campo de valor da árvore
                        id.setValue(value);
                    } else { // não está na tabela
                        // como ele não está na tabela, então pode ser o caso de
                        // um ID não inicializado. Mas se ele estiver em uma
                        // expressão de ATRIBUIÇÃO, isso não é um erro ->
                        // precisa verificar esse caso
                        AST parentAST = id.getParentAST();
                        // primeiro, se o pai não for um operador então não
                        // está inicializada com certeza
                        if (parentAST instanceof Operator == false) {
                            throw new Exception("Identificador não inicializado: "
                                    + "\"" + id.getIdentifierName() + "\"");
                        } else {
                            // ver o tipo do operador
                            Operator op = ((Operator) parentAST);
                            // se for diferente do operador de atribuição ou leitura,
                            // então o id não está inicializado
                            if (!(op.getOperatorSymbol() == sym.ASSIGN
                                    || op.getOperatorSymbol() == sym.READ)) {
                                throw new Exception("Identificador não inicializado: "
                                        + "\"" + id.getIdentifierName() + "\"");
                            }
                        }
                    }
                }
            }
        }
    }
}
