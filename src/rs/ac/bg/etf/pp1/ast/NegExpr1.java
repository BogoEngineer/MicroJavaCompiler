// generated with ast extension for cup
// version 0.8
// 9/1/2021 10:7:30


package rs.ac.bg.etf.pp1.ast;

public class NegExpr1 extends Expr1 {

    private Negate Negate;
    private UnsignedExpr UnsignedExpr;

    public NegExpr1 (Negate Negate, UnsignedExpr UnsignedExpr) {
        this.Negate=Negate;
        if(Negate!=null) Negate.setParent(this);
        this.UnsignedExpr=UnsignedExpr;
        if(UnsignedExpr!=null) UnsignedExpr.setParent(this);
    }

    public Negate getNegate() {
        return Negate;
    }

    public void setNegate(Negate Negate) {
        this.Negate=Negate;
    }

    public UnsignedExpr getUnsignedExpr() {
        return UnsignedExpr;
    }

    public void setUnsignedExpr(UnsignedExpr UnsignedExpr) {
        this.UnsignedExpr=UnsignedExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Negate!=null) Negate.accept(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Negate!=null) Negate.traverseTopDown(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Negate!=null) Negate.traverseBottomUp(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NegExpr1(\n");

        if(Negate!=null)
            buffer.append(Negate.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(UnsignedExpr!=null)
            buffer.append(UnsignedExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NegExpr1]");
        return buffer.toString();
    }
}
