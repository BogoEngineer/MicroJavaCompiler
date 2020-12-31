// generated with ast extension for cup
// version 0.8
// 31/11/2020 16:3:33


package rs.ac.bg.etf.pp1.ast;

public class SwitchStmt extends Matched {

    private Expr Expr;
    private SwitchCaseList SwitchCaseList;

    public SwitchStmt (Expr Expr, SwitchCaseList SwitchCaseList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.SwitchCaseList=SwitchCaseList;
        if(SwitchCaseList!=null) SwitchCaseList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public SwitchCaseList getSwitchCaseList() {
        return SwitchCaseList;
    }

    public void setSwitchCaseList(SwitchCaseList SwitchCaseList) {
        this.SwitchCaseList=SwitchCaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SwitchStmt(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SwitchCaseList!=null)
            buffer.append(SwitchCaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SwitchStmt]");
        return buffer.toString();
    }
}
