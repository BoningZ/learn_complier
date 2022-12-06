package symbol;

import token.TK;

public class Symbol {
    String name;
    TK type;

    public Symbol(String name, TK type) {
        this.name = name;
        this.type = type;
    }

    public Symbol(String name) {
        this.name = name;
    }
}
