// generated with ast extension for cup
// version 0.8
// 11/0/2021 11:12:24


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListDerived1 extends VarDeclList {

    public VarDeclListDerived1 () {
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
        buffer.append("VarDeclListDerived1(\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListDerived1]");
        return buffer.toString();
    }
}
