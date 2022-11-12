package ast;

import token.Token;

import java.util.List;

public class FunctionCallNode extends ASTNode{
    Token token;
    List<ASTNode> actualParams;
    String functionName;

    public FunctionCallNode(Token token, List<ASTNode> actualParams, String functionName) {
        this.token = token;
        this.actualParams = actualParams;
        this.functionName = functionName;
    }
}
