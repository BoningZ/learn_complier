package ast;

import token.Token;

public class VarNode extends ASTNode{
    Token token;
    Object val,symbol;

    public VarNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
