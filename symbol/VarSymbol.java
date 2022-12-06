package symbol;

import token.TK;

public class VarSymbol extends Symbol{
    public int offset;
    public VarSymbol symbol;
    public VarSymbol(String name, TK type,int offset){
        super(name,type);
        this.offset=offset;
    }
}
