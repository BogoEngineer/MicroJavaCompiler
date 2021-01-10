// generated with ast extension for cup
// version 0.8
// 10/0/2021 17:14:22


package rs.ac.bg.etf.pp1.ast;

public class NotEqualop extends Relop {

    public NotEqualop () {
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
        buffer.append("NotEqualop(\n");

        buffer.append(tab);
        buffer.append(") [NotEqualop]");
        return buffer.toString();
    }
}
