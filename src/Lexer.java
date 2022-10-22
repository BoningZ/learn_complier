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
    public Lexer(String text){//初始化
        this.text=text;
        pos=0;
        cur=text.charAt(pos);
        tokens=new ArrayList<>();
        error=new Error();
    }
    void advance(){//向前移动
        if(cur=='\n'){error.line+=1;error.col=0;}
        pos+=1;
        if(pos>text.length()-1)cur=null;
        else{
            cur=text.charAt(pos);
            error.col+=1;
        }
    }
    void skipWhitespace(){while(cur!=null&&Character.isWhitespace(cur))advance();}//跳过大量空白符
    Token number(){//数字，目前只有整数
        int oldCol=error.col;
        StringBuilder res= new StringBuilder();
        while(cur!=null&&Character.isDigit(cur)){
            res.append(cur);
            advance();
        }
        return new Token(TK.INTEGER_CONST,Integer.parseInt(res.toString()),error.line,error.col,error.col-oldCol);
    }
    int readPunctuation(){//符号，位数为2返回2 位数为1返回1 若不是指定的保留符号返回0
        if(text.startsWith("==",pos)||text.startsWith("!=",pos)||text.startsWith("<=",pos)||text.startsWith(">=",pos))return 2;
        return token.util.isSinglePunctuation(cur)?1:0;
    }
    Token getNextToken(){
        while(cur!=null){
            if(Character.isWhitespace(cur)){skipWhitespace();continue;}//当前空白，跳过空白符
            if(Character.isDigit(cur))return number();//第一位为数字，token一定是数字
            if(token.util.isId1(cur)){//字母或下划线开头，token为标识符（保留字/自定义）
                int oldCol=error.col;
                StringBuilder res= new StringBuilder(String.valueOf(cur));
                advance();
                while(token.util.isId2(cur)){res.append(cur);advance();}
                String ac=res.toString();
                if(token.util.MAP.containsKey(ac))//是保留字
                    return new Token(token.util.MAP.get(ac),ac,error.line,error.col,error.col-oldCol);
                return new Token(TK.ID,ac,error.line,error.col,error.col-oldCol);//自定义标识符
            }
            if(readPunctuation()==2){//长度为2的符号
                String ac=text.substring(pos,pos+2);
                Token tmp=new Token(token.util.MAP.get(ac),ac,error.line,error.col,2);
                advance(); advance();
                return tmp;
            }
            if(token.util.MAP.containsKey(String.valueOf(cur))){//长度为1的符号
                Token tmp=new Token(token.util.MAP.get(String.valueOf(cur)),String.valueOf(cur),error.line,error.col,1);
                advance();
                return tmp;
            }
            error.showError("Invalid Token");//以上均不符合，不合法
        }
        return new Token(TK.EOF,null,-1,-1,0);
    }
    public ArrayList<Token> gatherAllTokens(){//返回一个token列表
        Token token=getNextToken();
        tokens.add(token);
        while(token.type!=TK.EOF){
            token=getNextToken();
            tokens.add(token);
        }
        return tokens;
    }



}
