package ast;

import token.Token;

public class VarNode extends ASTNode{
    Object val,symbol;
    Token token;


    public VarNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
