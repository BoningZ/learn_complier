package ast;

import java.util.List;

public class FunctionDefNode extends ASTNode{
    String functionName;
    int offset;
    ASTNode type;
    List<ASTNode> formalParams;
    ASTNode block;


    public FunctionDefNode(ASTNode type, String functionName, List<ASTNode> formalParams, ASTNode block) {
        this.type = type;
        this.block = block;
        this.formalParams = formalParams;
        this.functionName = functionName;
    }
}
