package ast;

import token.Token;

public class ReturnNode extends ASTNode{
    public String functionName;
    public Token token;
    public ASTNode right;


    public ReturnNode(Token token, ASTNode right, String functionName) {
        this.token = token;
        this.right = right;
        this.functionName = functionName;
    }
}
