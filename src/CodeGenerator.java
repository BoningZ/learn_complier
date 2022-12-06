package src;

import ast.*;
import error.Error;
import symbol.ParamSymbol;
import symbol.VarSymbol;
import token.TK;

import java.lang.reflect.Method;
import java.util.List;

import static src.Main.*;

public class CodeGenerator {
    int alignTo(int n,int align){return ((n+align-1)/align)*align;}
    public void generate(List<ASTNode> tree){
        for(ASTNode node:tree)
            if(node!=null)visit(node);
    }
    public void visitUnaryOpNode(ASTNode node){
        UnaryOpNode unaryOpNode=(UnaryOpNode) node;
        visit(unaryOpNode.right);
        if(unaryOpNode.op.type== TK.MINUS)
            System.out.println("  neg %rax");
    }
    public void visitReturnNode(ASTNode node){
        ReturnNode returnNode=(ReturnNode) node;
        visit(returnNode.right);
        if(returnNode.token.type==TK.RETURN)
            System.out.printf("  jmp .%s.return\n",returnNode.functionName);
    }
    public void visitBinaryOpNode(ASTNode node){
        BinaryOpNode binaryOpNode=(BinaryOpNode) node;
        visit(((BinaryOpNode) node).right);
        System.out.println("  push %rax");
        visit(binaryOpNode.left);
        System.out.println("  pop %rdi");
        switch (binaryOpNode.op.type){
            case PLUS:
                System.out.println("  add %rdi, %rax");
                break;
            case MINUS:
                System.out.println("  sub %rdi, %rax");
                break;
            case MUL:
                System.out.println("  imul %rdi, %rax");
                break;
            case DIV:
                System.out.println(" cqo");
                System.out.println("  idiv %rdi");
                break;
            case EQ:
                System.out.println("  cmp %rdi, %rax");
                System.out.println("  sete %al");
                System.out.println("  movzb %al, %rax");
                break;
            case NE:
                System.out.println("  cmp %rdi, %rax");
                System.out.println("  setne %al");
                System.out.println("  movzb %al, %rax");
                break;
            case LT:
                System.out.println("  cmp %rdi, %rax");
                System.out.println("  setl %al");
                System.out.println("  movzb %al, %rax");
                break;
            case GT:
                System.out.println("  cmp %rdi, %rax");
                System.out.println("  setg %al");
                System.out.println("  movzb %al, %rax");
                break;
            case LE:
                System.out.println("  cmp %rdi, %rax");
                System.out.println("  setle %al");
                System.out.println("  movzb %al, %rax");
                break;
            case GE:
                System.out.println("  cmp %rdi, %rax");
                System.out.println("  setge %al");
                System.out.println("  movzb %al, %rax");
                break;
        }
    }
    public void visitAssignNode(ASTNode node){
        AssignNode assignNode=(AssignNode) node;
        if(((VarNode)assignNode.left).token.type==TK.ID){//var is left=value
            int varOffset=((VarSymbol)(((VarNode) assignNode.left).symbol)).offset;
            System.out.printf("  lea %d(%%rbp), %%rax\n",varOffset);
            System.out.println("  push %rax");
            visit(assignNode.right);
            System.out.println("  pop %rdi");
            System.out.println("  mov %rax, (%rdi)");
        }else new Error(((VarNode)assignNode.left).token).showError("not an lvalue!");
    }
    public void visitNumNode(ASTNode node){
        System.out.printf("  mov $%d, %%rax\n",(Integer)((NumNode)node).val);
    }
    public void visitIfNode(ASTNode node){
        IfNode ifNode=(IfNode) node;
        CountI++;
        visit(ifNode.condition);
        System.out.println("  cmp $0, %rax");
        System.out.printf("  je  .L.else.%d\n",CountI);
        if(ifNode.thenStmt!=null)visit(ifNode.thenStmt);
        System.out.printf("  jmp .L.end.%d\n",CountI);
        System.out.printf(".L.else.%d:\n",CountI);
        if(ifNode.elseStmt!=null)visit(ifNode.elseStmt);
        System.out.printf(".L.end.%d:\n",CountI);
    }
    public void visitBlockNode(ASTNode node){
        for(ASTNode each:((BlockNode)node).stmts)
            visit(each);
    }
    public void visitVarNode(ASTNode node){
        int varOffset;
        try {
            varOffset = ((VarSymbol) ((VarNode) node).symbol).offset;
        }catch (Exception e){
            varOffset=((ParamSymbol) ((VarNode) node).symbol).offset;
        }
        System.out.printf("  lea %d(%%rbp), %%rax\n",varOffset);
        System.out.println("  mov (%rax), %rax");
    }
    public void visitVarDeclNode(ASTNode node){}
    public void visitFormalParamNode(ASTNode node){}
    public void visitFunctionCallNode(ASTNode node){
        FunctionCallNode functionCallNode=(FunctionCallNode) node;
        int nParams=0;
        for(ASTNode each:functionCallNode.actualParams){
            visit(each);
            System.out.println("  push %rax");
            nParams++;
        }
        while(nParams-->0)
            System.out.printf("  pop %%%s\n",paramRegs[nParams]);
        System.out.println("  mov $0, %rax");
        System.out.printf("  call %s\n",functionCallNode.functionName);
    }
    public void visitFunctionDefNode(ASTNode node){
        FunctionDefNode functionDefNode=(FunctionDefNode)node;
        OffsetSum=0;
        System.out.println("  .text");
        System.out.printf("  .globl %s\n",functionDefNode.functionName);
        System.out.println(functionDefNode.functionName+":");
        System.out.println("  push %rbp");
        System.out.println("  mov %rsp, %rbp");
        int stackSize=alignTo(functionDefNode.offset,16);
        System.out.printf("  sub $%d, %%rsp\n",stackSize);

        int i=0;
        for(ASTNode each:functionDefNode.formalParams){
            int paramOffset=((ParamSymbol)((FormalParamNode)each).symbol).offset;
            System.out.printf("  mov %%%s, %d(%%rbp)\n",paramRegs[i++],paramOffset);
        }
        visit(functionDefNode.block);

        System.out.printf(".%s.return:\n",functionDefNode.functionName);

        System.out.println("  mov %rbp, %rsp");
        System.out.println("  pop %rbp");
        System.out.println("  ret");
    }

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
