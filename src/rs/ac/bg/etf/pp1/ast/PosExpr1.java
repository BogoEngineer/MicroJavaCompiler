// generated with ast extension for cup
// version 0.8
// 14/0/2021 16:52:18


package rs.ac.bg.etf.pp1.ast;

public class PosExpr1 extends Expr1 {

    private UnsignedExpr UnsignedExpr;

    public PosExpr1 (UnsignedExpr UnsignedExpr) {
        this.UnsignedExpr=UnsignedExpr;
        if(UnsignedExpr!=null) UnsignedExpr.setParent(this);
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
        if(UnsignedExpr!=null) UnsignedExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(UnsignedExpr!=null) UnsignedExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PosExpr1(\n");

        if(UnsignedExpr!=null)
            buffer.append(UnsignedExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PosExpr1]");
        return buffer.toString();
    }
}
