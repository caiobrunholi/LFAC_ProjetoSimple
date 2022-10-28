package ast;

public class AST {

    // Arvore AST pai
    private AST parentAST;
    // Arvore AST esquerda
    private final AST leftAST;
    // Arvore AST direita
    private final AST rightAST;
    // Valor da arvore
    protected Object value;

    public static AST create(AST leftAST, AST rightAST) {
        AST ast = new AST(leftAST, rightAST);
        if (leftAST != null) {
            leftAST.setParentAST(ast);
        }
        if (rightAST != null) {
            rightAST.setParentAST(ast);
        }
        return ast;
    }

    protected AST(AST leftAST, AST rightAST) {
        this.leftAST = leftAST;
        this.rightAST = rightAST;
        this.parentAST = null;
        this.value = null;
    }

    public AST getLeftAST() {
        return this.leftAST;
    }

    public AST getRightAST() {
        return this.rightAST;
    }

    public void setParentAST(AST ast) {
        this.parentAST = ast;
    }

    public AST getParentAST() {
        return this.parentAST;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AST Node";
    }

    // Percorrimento em pós-ordem
    public static void printAST(AST ast) {
        if (ast != null) {
            printAST(ast.getLeftAST());
            printAST(ast.getRightAST());
            System.out.println(ast);
        }
    }
}
