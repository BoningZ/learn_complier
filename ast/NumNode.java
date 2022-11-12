package ast;

import token.Token;

public class NumNode extends ASTNode{
    Token token;
    Object val;

    public NumNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
