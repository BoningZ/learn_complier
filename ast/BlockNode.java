package ast;

import token.Token;

import java.util.List;

public class BlockNode extends ASTNode{
    Token lTok;
    List<ASTNode> stmts;
    Token rTok;

    public BlockNode(Token lTok, Token rTok, List<ASTNode> stmts) {
        this.lTok = lTok;
        this.rTok = rTok;
        this.stmts = stmts;
    }
}
