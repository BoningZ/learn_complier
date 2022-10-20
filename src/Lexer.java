package src;

import error.Error;
import token.TK;
import token.Token;
import token.util;

import java.util.ArrayList;

public class Lexer {
    String text;
    int pos;
    Character cur;
    Error error;
    ArrayList<Token> tokens;
    public Lexer(String text){
        this.text=text;
        pos=0;
        cur=text.charAt(pos);
        tokens=new ArrayList<>();
        error=new Error();
    }
    void advance(){
        if(cur=='\n'){error.line+=1;error.col=0;}
        pos+=1;
        if(pos>text.length()-1)cur=null;
        else{
            cur=text.charAt(pos);
            error.col+=1;
        }
    }
    void skipWhitespace(){while(cur!=null&&Character.isWhitespace(cur))advance();}
    Token number(){
        int oldCol=error.col;
        StringBuilder res= new StringBuilder();
        while(cur!=null&&Character.isDigit(cur)){
            res.append(cur);
            advance();
        }
        return new Token(TK.INTEGER_CONST,Integer.parseInt(res.toString()),error.line,error.col,error.col-oldCol);
    }
    int readPunctuation(){
        if(text.startsWith("==",pos)||text.startsWith("!=",pos)||text.startsWith("<=",pos)||text.startsWith(">=",pos))return 2;
        return token.util.isSinglePunctuation(cur)?1:0;
    }
    Token getNextToken(){
        while(cur!=null){
            if(Character.isWhitespace(cur)){skipWhitespace();continue;}
            if(Character.isDigit(cur))return number();
            if(token.util.isId1(cur)){
                int oldCol=error.col;
                StringBuilder res= new StringBuilder(String.valueOf(cur));
                advance();
                while(token.util.isId2(cur)){res.append(cur);advance();}
                String ac=res.toString();
                if(token.util.MAP.containsKey(ac))
                    return new Token(token.util.MAP.get(ac),ac,error.line,error.col,error.col-oldCol);
                return new Token(TK.ID,ac,error.line,error.col,error.col-oldCol);
            }
            if(readPunctuation()==2){
                String ac=text.substring(pos,pos+2);
                Token tmp=new Token(token.util.MAP.get(ac),ac,error.line,error.col,2);
                advance(); advance();
                return tmp;
            }
            if(token.util.MAP.containsKey(String.valueOf(cur))){
                Token tmp=new Token(token.util.MAP.get(String.valueOf(cur)),String.valueOf(cur),error.line,error.col,1);
                advance();
                return tmp;
            }
            error.showError("Invalid Token");
        }
        return new Token(TK.EOF,null,-1,-1,0);
    }
    public ArrayList<Token> gatherAllTokens(){
        Token token=getNextToken();
        tokens.add(token);
        while(token.type!=TK.EOF){
            token=getNextToken();
            tokens.add(token);
        }
        return tokens;
    }



}
