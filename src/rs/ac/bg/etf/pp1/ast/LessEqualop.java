// generated with ast extension for cup
// version 0.8
// 6/0/2021 20:6:21


package rs.ac.bg.etf.pp1.ast;

public class LessEqualop extends Relop {

    public LessEqualop () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LessEqualop(\n");

        buffer.append(tab);
        buffer.append(") [LessEqualop]");
        return buffer.toString();
    }
}
