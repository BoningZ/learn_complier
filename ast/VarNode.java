package ast;

import symbol.Symbol;
import token.Token;

public class VarNode extends ASTNode{
    public Symbol symbol;
    public Object val;
    public Token token;


    public VarNode(Token token) {
        this.token = token;
        this.val = token.val;
    }
}
