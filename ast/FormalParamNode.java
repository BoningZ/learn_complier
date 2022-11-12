package ast;

public class FormalParamNode extends ASTNode{
    ASTNode type,param;
    Object symbol;

    public FormalParamNode(ASTNode type, ASTNode param) {
        this.type = type;
        this.param = param;
    }
}
