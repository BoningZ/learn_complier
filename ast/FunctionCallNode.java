package ast;

import token.Token;

import java.util.List;

public class FunctionCallNode extends ASTNode{
    public String functionName;
    Token token;
    public List<ASTNode> actualParams;


    public FunctionCallNode(String functionName, List<ASTNode> actualParams,Token token) {
        this.token = token;
        this.actualParams = actualParams;
        this.functionName = functionName;
    }
}
