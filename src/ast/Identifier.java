package ast;

public class Identifier extends AST {

    private final String identifier;

    public static AST create(String identifier) {
        AST ast = new Identifier(identifier);
        return ast;
    }

    protected Identifier(String identifier) {
        // Inicializa a parte básica do AST com null
        super(null, null);
        this.identifier = identifier;
    }

    public String getIdentifierName() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return "Identifier: " + this.identifier;
    }
}
