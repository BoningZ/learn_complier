package ast;

import token.Token;

public class BinaryOpNode extends ASTNode{
    ASTNode left,right;
    Token token,op;

    public BinaryOpNode(ASTNode left, ASTNode right, Token op) {
        this.left = left;
        this.right = right;
        this.token=this.op = op;
    }
}
