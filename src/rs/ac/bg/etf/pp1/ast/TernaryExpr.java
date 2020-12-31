// generated with ast extension for cup
// version 0.8
// 31/11/2020 16:3:33


package rs.ac.bg.etf.pp1.ast;

public class TernaryExpr extends Expr {

    private CondFact CondFact;
    private UnsignedExpr UnsignedExpr;
    private UnsignedExpr UnsignedExpr1;

    public TernaryExpr (CondFact CondFact, UnsignedExpr UnsignedExpr, UnsignedExpr UnsignedExpr1) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.UnsignedExpr=UnsignedExpr;
        if(UnsignedExpr!=null) UnsignedExpr.setParent(this);
        this.UnsignedExpr1=UnsignedExpr1;
        if(UnsignedExpr1!=null) UnsignedExpr1.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public UnsignedExpr getUnsignedExpr() {
        return UnsignedExpr;
    }

    public void setUnsignedExpr(UnsignedExpr UnsignedExpr) {
        this.UnsignedExpr=UnsignedExpr;
    }

    public UnsignedExpr getUnsignedExpr1() {
        return UnsignedExpr1;
    }

    public void setUnsignedExpr1(UnsignedExpr UnsignedExpr1) {
        this.UnsignedExpr1=UnsignedExpr1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFact!=null) CondFact.accept(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.accept(visitor);
        if(UnsignedExpr1!=null) UnsignedExpr1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.traverseTopDown(visitor);
        if(UnsignedExpr1!=null) UnsignedExpr1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.traverseBottomUp(visitor);
        if(UnsignedExpr1!=null) UnsignedExpr1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TernaryExpr(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(UnsignedExpr!=null)
            buffer.append(UnsignedExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(UnsignedExpr1!=null)
            buffer.append(UnsignedExpr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TernaryExpr]");
        return buffer.toString();
    }
}
