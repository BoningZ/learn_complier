package ast;

import token.Token;

public class AssignNode extends ASTNode{
    ASTNode left;
    Token token,op;
    ASTNode right;

    public AssignNode(ASTNode left, Token op, ASTNode right) {
        this.left = left;
        this.right = right;
        this.token=this.op = op;
    }
}
