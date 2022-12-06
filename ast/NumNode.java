package ast;

import token.Token;

public class NumNode extends ASTNode{
    public Object val;
    Token token;


    public NumNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
