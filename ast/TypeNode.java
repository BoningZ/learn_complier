package ast;

import token.Token;

public class TypeNode extends ASTNode{
    public Object val;
    public Token token;


    public TypeNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
