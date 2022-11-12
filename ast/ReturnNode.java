package ast;

import token.Token;

public class ReturnNode extends ASTNode{
    Token token;
    ASTNode right;
    String functionName;

    public ReturnNode(Token token, ASTNode right, String functionName) {
        this.token = token;
        this.right = right;
        this.functionName = functionName;
    }
}
