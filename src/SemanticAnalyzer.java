package src;

import ast.*;
import error.Error;
import symbol.*;
import token.TK;

import java.lang.reflect.Method;
import java.util.List;

import static src.Main.OffsetSum;

public class SemanticAnalyzer {
    ScopedSymbolTable globalScope,curScope;
    public SemanticAnalyzer(){
        globalScope=new ScopedSymbolTable("global",0,null);
        curScope=globalScope;
    }
    public void analyze(List<ASTNode> tree){
        for(ASTNode node:tree)
            if(node!=null)visit(node);
    }
    public void visitUnaryOpNode(ASTNode node){}
    public void visitReturnNode(ASTNode node){
        visit(((ReturnNode)node).right);
    }
    public void visitBinaryOpNode(ASTNode node){
        visit(((BinaryOpNode)node).left);
        visit(((BinaryOpNode)node).right);
    }
    public void visitAssignNode(ASTNode node){
        AssignNode assignNode=(AssignNode) node;
        if(((VarNode)assignNode.left).token.type!= TK.ID)
            new Error(((VarNode)assignNode.left).token).showError("left side of assign is not a variable!");
        visit(assignNode.left);
        visit(assignNode.right);
    }
    public void visitIfNode(ASTNode node){
        IfNode ifNode=(IfNode) node;
        visit(ifNode.condition);
        if(ifNode.thenStmt!=null)visit(ifNode.thenStmt);
        if(ifNode.elseStmt!=null)visit(ifNode.elseStmt);
    }
    public void visitBlockNode(ASTNode node){
        String blockName=curScope.scopeName+" block"+(curScope.scopeLevel+1);
        curScope= new ScopedSymbolTable(blockName,curScope.scopeLevel+1,curScope);
        for(ASTNode each:((BlockNode)node).stmts)visit(each);
        curScope=curScope.enclosingScope;
    }
    public void visitNumNode(ASTNode node){}
    public void visitVarNode(ASTNode node){
        VarNode varNode=(VarNode) node;
        String varName=(String) varNode.val;
        Symbol varSymbol=curScope.lookup(varName);
        if(varSymbol==null)
            new Error(varNode.token).showError("semantic error, var not declared!");
        else varNode.symbol=varSymbol;
    }
    public void visitVarDeclNode(ASTNode node){
        VarDeclNode varDeclNode=(VarDeclNode) node;
        String varName=(String) ((VarNode)varDeclNode.var).val;
        TK varType=((TypeNode)varDeclNode.type).token.type;
        OffsetSum+=8;
        int varOffset=-OffsetSum;
        Symbol varSymbol=new VarSymbol(varName,varType,varOffset);
        curScope.insert(varSymbol);
    }
    public void visitFormalParamNode(ASTNode node){
        FormalParamNode formalParamNode=(FormalParamNode)node;
        String paramName=(String) ((VarNode)formalParamNode.param).val;
        TK paramType=((TypeNode)formalParamNode.type).token.type;
        OffsetSum+=8;
        int paramOffset=-OffsetSum;
        Symbol paramSymbol=new ParamSymbol(paramName,paramType,paramOffset);
        curScope.insert(paramSymbol);
        formalParamNode.symbol=paramSymbol;

    }
    public void visitFunctionDefNode(ASTNode node){
        FunctionDefNode functionDefNode=(FunctionDefNode) node;
        OffsetSum=0;
        String functionName=functionDefNode.functionName;
        FunctionSymbol functionSymbol=new FunctionSymbol(functionName);
        curScope.insert(functionSymbol);
        curScope=new ScopedSymbolTable(functionName,curScope.scopeLevel+1,curScope);
        for(ASTNode each:functionDefNode.formalParams)visit(each);
        visit(functionDefNode.block);
        functionDefNode.offset=OffsetSum;
        curScope=curScope.enclosingScope;
        functionSymbol.blockAST= (BlockNode) functionDefNode.block;
    }
    public void visitFunctionCallNode(ASTNode node){}
    void visit(ASTNode node){
        String name=node.getClass().getSimpleName();
        try {
            Method method = this.getClass().getMethod("visit" + name, ASTNode.class);
            method.invoke(this,node);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
