package ast;

import java.util.List;

public class FunctionDefNode extends ASTNode{
    public String functionName;
    public int offset;
    ASTNode type;
    public List<ASTNode> formalParams;
    public ASTNode block;


    public FunctionDefNode(ASTNode type, String functionName, List<ASTNode> formalParams, ASTNode block) {
        this.type = type;
        this.block = block;
        this.formalParams = formalParams;
        this.functionName = functionName;
    }
}
