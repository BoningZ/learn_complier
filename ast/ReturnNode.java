package ast;

import token.Token;

public class ReturnNode extends ASTNode{
    String functionName;
    Token token;
    ASTNode right;


    public ReturnNode(Token token, ASTNode right, String functionName) {
        this.token = token;
        this.right = right;
        this.functionName = functionName;
    }
}
