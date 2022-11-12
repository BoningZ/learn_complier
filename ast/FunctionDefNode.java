package ast;

import java.util.List;

public class FunctionDefNode extends ASTNode{
    ASTNode type,block;
    List<ASTNode> formalParams;
    String functionName;
    int offset;

    public FunctionDefNode(ASTNode type, ASTNode block, List<ASTNode> formalParams, String functionName) {
        this.type = type;
        this.block = block;
        this.formalParams = formalParams;
        this.functionName = functionName;
    }
}
