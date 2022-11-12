package ast;

public class VarDeclNode extends ASTNode{
    ASTNode type,var;

    public VarDeclNode(ASTNode type, ASTNode var) {
        this.type = type;
        this.var = var;
    }
}
