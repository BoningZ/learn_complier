package ast;

import token.Token;

public class TypeNode extends ASTNode{
    Object val;
    Token token;


    public TypeNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
