package src;

import ast.*;
import error.Error;
import error.ErrorCode;
import token.TK;
import token.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    Lexer lexer;
    Token cur;
    String curFunctionName;
    public Parser(Lexer lexer){
        this.lexer=lexer;
        cur=lexer.getNextToken();
        curFunctionName="";
    }
    Token getNextToken(){return lexer.getNextToken();}
    void showError(Token token,TK type){new Error(token,ErrorCode.UNEXPECTED_TOKEN).showParserError(String.format("read %s, expected %s",token.val,type.toString()));}
    void eat(TK type){
        if(cur.type==type)cur=getNextToken();
        else showError(cur,type);
    }
    //PRIMARY->(EXPR)|id ARGS|id|NUM
    //IN_ARGS->ASSIGN|ASSIGN,IN_ARGS
    //ARGS->(IN_ARGS)|()
    ASTNode primary(){
        Token token=cur;
        switch (cur.type){
            case LPAREN://(EXPR)
                eat(TK.LPAREN); ASTNode node=expr(); eat(TK.RPAREN);
                return node;
            case ID://id ARGS| id
                eat(TK.ID);
                switch (cur.type){
                    case LPAREN: //id ARGS
                        String functionName=token.val.toString();
                        eat(TK.LPAREN);
                        List<ASTNode> actualParas=new ArrayList<>();
                        //IN_ARGS
                        if(cur.type!=TK.RPAREN)actualParas.add(assign());
                        while(cur.type==TK.COMMA){
                            eat(TK.COMMA);
                            actualParas.add(assign());
                        }
                        eat(TK.RPAREN);
                        return new FunctionCallNode(functionName,actualParas,token);
                    default://id
                        return new VarNode(token);
                }
            case INTEGER_CONST://NUM
                eat(TK.INTEGER_CONST);
                return new NumNode(token);
            default:
                return null;
        }
    }
    //UNARY->+UNARY| -UNARY| PRIMARY
    ASTNode unary(){
        Token token=cur;
        if(cur.type==TK.PLUS||cur.type==TK.MINUS){
            eat(cur.type);
            return new UnaryOpNode(token,unary());
        }
        else return primary();
    }
    //MUL_DIV->UNARY MUL_DIV_EXTRA| MUL_DIV MUL_DIV_EXTRA
    //MUL_DIV_EXTRA->*UNARY|/UNARY
    ASTNode mulDiv(){
        ASTNode node=unary();
        for(;;){
            Token token=cur;
            if(cur.type==TK.MUL||cur.type==TK.DIV){
                eat(cur.type);
                node=new BinaryOpNode(node,token,unary());
                continue;
            }
            return node;
        }
    }
    //ADD_SUB->MUL_DIV ADD_SUB_EXTRA| ADD_SUB ADD_SUB_EXTRA
    //ADD_SUB_EXTRA->+MUL_DIV| -MUL_DIV
    ASTNode addSub(){
        ASTNode node=mulDiv();
        for(;;){
            Token token=cur;
            if(cur.type==TK.PLUS||cur.type==TK.MINUS){
                eat(cur.type);
                node=new BinaryOpNode(node,token,mulDiv());
                continue;
            }
            return node;
        }
    }
    //RELATIONAL->ADD_SUB RELATIONAL_EXTRA| RELATIONAL RELATIONAL_EXTRA
    //RELATIONAL_EXTRA-><ADD_SUB|<=ADD_SUB| >ADD_SUB| >=ADD+SUB
    ASTNode relational(){
        ASTNode node=addSub();
        for(;;){
            Token token=cur;
            if(cur.type==TK.LT||cur.type==TK.LE||cur.type==TK.GT||cur.type==TK.GE){
                eat(cur.type);
                node=new BinaryOpNode(node,token,addSub());
                continue;
            }
            return node;
        }
    }
    //EQUALITY->RELATIONAL EQUALITY_EXTRA| EQUALITY EQUALITY_EXTRA
    //EQUALITY_EXTRA->==RELATIONAL| !=RELATIONAL
    ASTNode equality(){
        ASTNode node=relational();
        for(;;){
            Token token=cur;
            if(cur.type==TK.EQ||cur.type==TK.NE){
                eat(cur.type);
                node=new BinaryOpNode(node,token,equality());
                continue;
            }
            return node;
        }
    }
    //ASSIGN->EQUALITY|EQUALITY=ASSIGN
    ASTNode assign(){
        ASTNode node=equality();
        Token token=cur;
        if(cur.type==TK.ASSIGN){
            eat(TK.ASSIGN);
            node=new AssignNode(node,token,assign());
        }
        return node;
    }
    //EXPR->ASSIGN
    ASTNode expr(){return assign();}
    //EXPR_STMT=EXPR;|;
    ASTNode exprStmt(){
        Token token=cur;
        ASTNode node=null;
        if(cur.type==TK.SEMICOLON)eat(TK.SEMICOLON);
        else{
            node=expr();
            if(cur.type==TK.SEMICOLON)eat(TK.SEMICOLON);
            else showError(token,TK.SEMICOLON);
        }
        return node;
    }
    //STMT->EXPR_STMT
    //      |return EXPR_STMT
    //      |BLOCK
    //      |if(EXPR)then STMT
    //      |if(EXPR)then STMT else STMT
    //      |EXPR_STMT
    ASTNode stmt(){
        Token token=cur;
        //return EXPR_STMT
        if(cur.type==TK.RETURN){
            eat(TK.RETURN);
            return new ReturnNode(token,exprStmt(),curFunctionName);
        }
        //BLOCK
        else if(cur.type==TK.LBRACE)return block();
        //if(EXPR)then STMT| if(EXPR)then STMT else STMT
        else if(cur.type==TK.IF){
            ASTNode condition=null,thenStmt=null,elseStmt=null;
            eat(TK.IF);
            if(cur.type==TK.LPAREN){
                eat(TK.LPAREN); condition=expr(); eat(TK.RPAREN);
                if(cur.type==TK.THEN){
                    eat(TK.THEN); thenStmt=stmt();
                    if(cur.type==TK.ELSE){
                        eat(TK.ELSE); elseStmt=stmt();
                    }
                }
            }
            return new IfNode(condition,thenStmt,elseStmt);
        }
        //EXPR_STMT
        else return exprStmt();
    }
    //TYPE_SPEC->int
    ASTNode typeSpec(){
        Token token=cur;
        if(cur.type==TK.INT)eat(TK.INT);
        return new TypeNode(token);
    }
    //VAR_DECL->TYPE_SPEC ID_LIST;
    //ID_LIST->id| id,ID_LIST
    List<ASTNode> varDecl(){
        ASTNode typeNode=typeSpec();
        List<ASTNode> varNodes=new ArrayList<>();
        while(cur.type!=TK.SEMICOLON){
            if(cur.type==TK.ID){
                ASTNode node=new VarDeclNode(typeNode,new VarNode(cur));
                eat(TK.ID);
                varNodes.add(node);
                if(cur.type==TK.COMMA)eat(TK.COMMA);
            }
        }
        eat(TK.SEMICOLON);
        return varNodes;
    }
    //COMPOUND_STMT->META_STMT|META_STMT,COMPOUND_STMT
    //META_STMT->VAR_DECL|STMT
    List<ASTNode> compoundStmt(){
        List<ASTNode> stmts=new ArrayList<>();
        while(cur.type!=TK.RBRACE&&cur.type!=TK.EOF){
            if(cur.type==TK.INT)stmts.addAll(varDecl());
            else{
                ASTNode node=stmt();
                if(node!=null)stmts.add(node);
            }
        }
        return stmts;
    }
    //BLOCK->{COMPOUND_STMT}
    ASTNode block(){
        if(cur.type==TK.LBRACE){
            Token lTok=cur;
            eat(TK.LBRACE);
            List<ASTNode> stmts=compoundStmt();
            Token rTok=cur;
            eat(TK.RBRACE);
            return new BlockNode(lTok,rTok,stmts);
        }
        return null;
    }
    //FORMAL_PARAM->TYPE_SPEC id
    ASTNode formalParam(){
        ASTNode typeNode=typeSpec();
        ASTNode paramNode=new VarNode(cur);
        eat(TK.ID);
        return new FormalParamNode(typeNode,paramNode);
    }
    //FORMAL_PARAMS->FORMAL_PARAM| FORMAL_PARAM,FORMAL_PARAMS
    List<ASTNode> formalParams(){
        List<ASTNode> params=new ArrayList<>();
        params.add(formalParam());
        while(cur.type!=TK.RPAREN){
            if(cur.type==TK.COMMA){
                eat(TK.COMMA);
                params.add(formalParam());
            }
            else showError(cur,TK.COMMA);
        }
        return params;
    }
    //FUNCTION_DEF->TYPE_SPEC id()BLOCK
    //              | TYPE_SPEC id(FORMAL_PARAMS)BLOCK
    ASTNode functionDef(){
        ASTNode typeNode=typeSpec();
        curFunctionName=cur.val.toString();
        List<ASTNode> formalParams=new ArrayList<>();
        ASTNode blockNode=null;
        eat(TK.ID);
        if(cur.type==TK.LPAREN){
            eat(TK.LPAREN);
            if(cur.type!=TK.RPAREN)formalParams=formalParams();
            eat(TK.RPAREN);
        }
        if(cur.type==TK.LBRACE)blockNode=block();
        else showError(cur,TK.LBRACE);
        return new FunctionDefNode(typeNode, curFunctionName,formalParams,blockNode);
    }
    //PROG->FUNCTION_DEF|FUNCTION_DEF,PROG|ε
    List<ASTNode> parse(){
        List<ASTNode> defNodes=new ArrayList<>();
        while(cur.type!=TK.EOF)defNodes.add(functionDef());
        return defNodes;
    }
}
