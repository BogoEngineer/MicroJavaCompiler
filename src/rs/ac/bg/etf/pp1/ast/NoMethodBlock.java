// generated with ast extension for cup
// version 0.8
// 7/0/2021 16:42:54


package rs.ac.bg.etf.pp1.ast;

public class NoMethodBlock extends MethodDeclBlock {

    public NoMethodBlock () {
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
        buffer.append("NoMethodBlock(\n");

        buffer.append(tab);
        buffer.append(") [NoMethodBlock]");
        return buffer.toString();
    }
}
