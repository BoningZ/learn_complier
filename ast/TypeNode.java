package ast;

import token.Token;

public class TypeNode extends ASTNode{
    Token token;
    Object val;

    public TypeNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
