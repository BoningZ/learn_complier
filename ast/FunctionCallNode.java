package ast;

import token.Token;

import java.util.List;

public class FunctionCallNode extends ASTNode{
    String functionName;
    Token token;
    List<ASTNode> actualParams;


    public FunctionCallNode(String functionName, List<ASTNode> actualParams,Token token) {
        this.token = token;
        this.actualParams = actualParams;
        this.functionName = functionName;
    }
}
