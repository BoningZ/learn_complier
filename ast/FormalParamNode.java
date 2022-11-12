package ast;

public class FormalParamNode extends ASTNode{
    Object symbol;
    ASTNode type,param;

    public FormalParamNode(ASTNode type, ASTNode param) {
        this.type = type;
        this.param = param;
    }
}
