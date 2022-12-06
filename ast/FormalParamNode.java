package ast;

public class FormalParamNode extends ASTNode{
    public Object symbol;
    public ASTNode type,param;

    public FormalParamNode(ASTNode type, ASTNode param) {
        this.type = type;
        this.param = param;
    }
}
