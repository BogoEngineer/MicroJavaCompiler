// generated with ast extension for cup
// version 0.8
// 7/0/2021 16:42:54


package rs.ac.bg.etf.pp1.ast;

public class TernaryExpr extends Expr {

    private Expr1 Expr1;
    private Expr1 Expr11;
    private Expr1 Expr12;

    public TernaryExpr (Expr1 Expr1, Expr1 Expr11, Expr1 Expr12) {
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
        this.Expr11=Expr11;
        if(Expr11!=null) Expr11.setParent(this);
        this.Expr12=Expr12;
        if(Expr12!=null) Expr12.setParent(this);
    }

    public Expr1 getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr1 Expr1) {
        this.Expr1=Expr1;
    }

    public Expr1 getExpr11() {
        return Expr11;
    }

    public void setExpr11(Expr1 Expr11) {
        this.Expr11=Expr11;
    }

    public Expr1 getExpr12() {
        return Expr12;
    }

    public void setExpr12(Expr1 Expr12) {
        this.Expr12=Expr12;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr1!=null) Expr1.accept(visitor);
        if(Expr11!=null) Expr11.accept(visitor);
        if(Expr12!=null) Expr12.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
        if(Expr11!=null) Expr11.traverseTopDown(visitor);
        if(Expr12!=null) Expr12.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        if(Expr11!=null) Expr11.traverseBottomUp(visitor);
        if(Expr12!=null) Expr12.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TernaryExpr(\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr11!=null)
            buffer.append(Expr11.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr12!=null)
            buffer.append(Expr12.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TernaryExpr]");
        return buffer.toString();
    }
}
