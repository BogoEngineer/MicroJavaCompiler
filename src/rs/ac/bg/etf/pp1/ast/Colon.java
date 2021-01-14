// generated with ast extension for cup
// version 0.8
// 14/0/2021 11:50:30


package rs.ac.bg.etf.pp1.ast;

public class Colon implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public Colon () {
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("Colon(\n");

        buffer.append(tab);
        buffer.append(") [Colon]");
        return buffer.toString();
    }
}
