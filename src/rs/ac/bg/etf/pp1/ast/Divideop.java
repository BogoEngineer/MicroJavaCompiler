// generated with ast extension for cup
// version 0.8
// 9/1/2021 10:7:30


package rs.ac.bg.etf.pp1.ast;

public class Divideop extends Mulop {

    public Divideop () {
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
        buffer.append("Divideop(\n");

        buffer.append(tab);
        buffer.append(") [Divideop]");
        return buffer.toString();
    }
}
