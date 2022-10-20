package token;

public class Token {
    public TK type;
    public Object val;
    public int line,col,width;

    public Token(TK type, Object val, int line, int col, int width) {
        this.type = type;
        this.val = val;
        this.line = line;
        this.col = col;
        this.width = width;
    }
    public String toString(){
        if(type==TK.EOF)return "[<EOF>]";
        return String.format("[<%s,%s>@(%d:%d) w:%d]",type.toString(),val.toString(),line,col,width);
    }
}
