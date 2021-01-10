// generated with ast extension for cup
// version 0.8
// 10/0/2021 17:14:22


package rs.ac.bg.etf.pp1.ast;

public class ParenExpr extends Factor {

    private LeftParen LeftParen;
    private Expr Expr;

    public ParenExpr (LeftParen LeftParen, Expr Expr) {
        this.LeftParen=LeftParen;
        if(LeftParen!=null) LeftParen.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public LeftParen getLeftParen() {
        return LeftParen;
    }

    public void setLeftParen(LeftParen LeftParen) {
        this.LeftParen=LeftParen;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LeftParen!=null) LeftParen.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LeftParen!=null) LeftParen.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LeftParen!=null) LeftParen.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ParenExpr(\n");

        if(LeftParen!=null)
            buffer.append(LeftParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ParenExpr]");
        return buffer.toString();
    }
}
