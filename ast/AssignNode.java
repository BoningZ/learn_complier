package ast;

import token.Token;

public class AssignNode extends ASTNode{
    public ASTNode left;
    Token token,op;
    public ASTNode right;

    public AssignNode(ASTNode left, Token op, ASTNode right) {
        this.left = left;
        this.right = right;
        this.token=this.op = op;
    }
}
