/*
    Para executar o interpretador na linha de comando (fora do IDE):
        java -jar simple-expr-ast.jar
        ou
        java -jar simple-expr-ast.jar <nome do arquivo de entrada>
 */
package parser;

import ast.AST;

public class Main {

    // Percorrimento em pós-ordem
    public static void printAST(AST ast) {
        if (ast != null) {
            printAST(ast.getLeftAST());
            printAST(ast.getRightAST());
            System.out.println(ast);
        }
    }

    public static void main(String[] args) {
        java.io.Reader reader = null;
        if (args.length == 0) {
            reader = new java.io.InputStreamReader(System.in);
        } else {
            if (args.length != 1) {
                System.out.println(
                        "Usage : java -jar simple_expr.jar <filename>");
            } else {
                try {
                    java.io.FileInputStream stream = new java.io.FileInputStream(args[0]);
                    reader = new java.io.InputStreamReader(stream);
                } catch (java.io.FileNotFoundException e) {
                    System.out.println("File not found : \"" + args[0] + "\"");
                } catch (Exception e) {
                    System.out.println("Not expected exception:" + e);
                }
            }
        }
        try {
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);
            // Executar a análise e interpretação
            parser.parse();
        } catch (Exception e) {
            System.out.println("Exceção detectada: " + e);
        }
    }
}
