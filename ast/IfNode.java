package ast;

public class IfNode extends ASTNode{
    public ASTNode condition,thenStmt,elseStmt;

    public IfNode(ASTNode condition, ASTNode thenStmt, ASTNode elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }
}
