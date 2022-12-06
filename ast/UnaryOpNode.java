package ast;

import token.Token;

public class UnaryOpNode extends ASTNode{
    public Token token,op;
    public ASTNode right;
    public UnaryOpNode(Token op,ASTNode right){
        this.token=this.op=op;
        this.right=right;
    }
}
