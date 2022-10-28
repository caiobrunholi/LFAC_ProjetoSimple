package ast;

public class Number extends AST {

    public static AST create(Double value) {
        AST ast = new Number(value);
        return ast;
    }

    protected Number(Double value) {
        // Inicializa a parte básica do AST com null
        super(null, null);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Number: " + value;
    }

}
