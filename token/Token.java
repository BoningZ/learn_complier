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
}
