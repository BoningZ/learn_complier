package ast;

import token.Token;

public class AssignNode extends ASTNode{
    ASTNode left,right;
    Token token,op;

    public AssignNode(ASTNode left, ASTNode right, Token op) {
        this.left = left;
        this.right = right;
        this.token=this.op = op;
    }
}
