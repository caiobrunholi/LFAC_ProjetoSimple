package ast;

public class Operator extends AST {

    // Símbolo do operador
    private final int operator;

    // operadores binários
    public static AST create(int op, AST leftAST, AST rightAST) {
        AST ast = new Operator(op, leftAST, rightAST);
        if (leftAST != null) {
            leftAST.setParentAST(ast);
        }
        if (rightAST != null) {
            rightAST.setParentAST(ast);
        }
        return ast;
    }

    // operadores unários
    public static AST create(int op, AST a) {
        AST ast = new Operator(op, a);
        if (a != null) {
            a.setParentAST(ast);
        }
        return ast;
    }

    // operadores binários
    protected Operator(int op, AST leftAST, AST rightAST) {
        // Inicializa a parte básica do AST
        super(leftAST, rightAST);
        this.operator = op;
    }

    // operadores unários
    protected Operator(int op, AST ast) {
        // Inicializa a parte básica do AST
        super(ast, null);
        this.operator = op;
    }

    public int getOperatorSymbol() {
        return this.operator;
    }

    @Override
    public String toString() {
        return "Operator: " + operator;
    }

}
