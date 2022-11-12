package ast;

public class IfNode extends ASTNode{
    ASTNode condition,thenStmt,elseStmt;

    public IfNode(ASTNode condition, ASTNode thenStmt, ASTNode elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }
}
