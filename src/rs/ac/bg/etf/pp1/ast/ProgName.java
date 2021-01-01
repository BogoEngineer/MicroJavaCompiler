// generated with ast extension for cup
// version 0.8
// 1/0/2021 21:46:19


package rs.ac.bg.etf.pp1.ast;

public class ProgName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String pName;

    public ProgName (String pName) {
        this.pName=pName;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName=pName;
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
        buffer.append("ProgName(\n");

        buffer.append(" "+tab+pName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgName]");
        return buffer.toString();
    }
}
