package symbol;

import ast.ASTNode;
import ast.BlockNode;

import java.util.ArrayList;
import java.util.List;

public class FunctionSymbol extends Symbol{
    List<ASTNode> formalParams;
    public BlockNode blockAST;

    public FunctionSymbol(String name){
        super(name);
    }
    public FunctionSymbol(String name,List<ASTNode> formalParams) {
        super(name);
        this.formalParams = formalParams;
        if(formalParams==null)formalParams=new ArrayList<>();
    }
}
