package symbol;

import java.util.HashMap;
import java.util.Map;

public class ScopedSymbolTable {
    Map<String,Symbol> symbols=new HashMap<>();
    public String scopeName;
    public int scopeLevel;
    public ScopedSymbolTable enclosingScope;
    public ScopedSymbolTable(String scopeName,int scopeLevel){
        this.scopeName=scopeName;
        this.scopeLevel=scopeLevel;
    }
    public ScopedSymbolTable(String scopeName,int scopeLevel,ScopedSymbolTable enclosingScope){
        this(scopeName,scopeLevel);
        this.enclosingScope=enclosingScope;
    }
    public void insert(Symbol symbol){symbols.put(symbol.name,symbol);}
    public Symbol lookup(String name,boolean curScopeOnly){
        Symbol symbol=symbols.get(name);
        if(symbol!=null)return symbol;
        if(curScopeOnly)return null;
        if(enclosingScope!=null)return enclosingScope.lookup(name);
        return null;
    }
    public Symbol lookup(String name){
        Symbol symbol=symbols.get(name);
        if(symbol!=null)return symbol;
        if(enclosingScope!=null)return enclosingScope.lookup(name);
        return null;
    }

}
