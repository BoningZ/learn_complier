package symbol;

import token.TK;

public class ParamSymbol extends Symbol{
    public int offset;
    public ParamSymbol(String name, TK type, int offset){
        super(name,type);
        this.offset=offset;
    }
}
