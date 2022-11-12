package ast;

import token.Token;

public class UnaryOpNode extends ASTNode{
    Token token,op;
    ASTNode right;
    public UnaryOpNode(Token op,ASTNode right){
        this.token=this.op=op;
        this.right=right;
    }
}
