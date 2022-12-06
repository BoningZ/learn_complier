package ast;

import token.Token;

public class BinaryOpNode extends ASTNode{
    public ASTNode left;
    public Token token,op;
    public ASTNode right;

    public BinaryOpNode(ASTNode left, Token op, ASTNode right) {
        this.left = left;
        this.right = right;
        this.token=this.op = op;
    }
}
