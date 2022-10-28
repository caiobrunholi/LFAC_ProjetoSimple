package ast;

public class CondOperator extends Operator {

    // armazena uma árvore da expressão relacional
    private final AST relStmt;

    // para if
    public static AST create(int op, AST relStmt, AST leftAST) {
        AST ast = new CondOperator(op, relStmt, leftAST, null);
        if (leftAST != null) {
            leftAST.setParentAST(ast);
        }
        return ast;
    }

    // para if-else
    public static AST create(int op, AST relStmt, AST leftAST, AST rightAST) {
        AST ast = new CondOperator(op, relStmt, leftAST, rightAST);
        if (leftAST != null) {
            leftAST.setParentAST(ast);
        }
        if (rightAST != null) {
            rightAST.setParentAST(ast);
        }
        return ast;
    }

    protected CondOperator(int op, AST relStmt, AST leftAST, AST rightAST) {
        super(op, leftAST, rightAST);
        this.relStmt = relStmt;
    }

    public AST getRelStmt() {
        return this.relStmt;
    }

    @Override
    public String toString() {
        return "CondOperator: " + this.getOperatorSymbol();
    }
}
