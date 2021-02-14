// generated with ast extension for cup
// version 0.8
// 9/1/2021 10:7:30


package rs.ac.bg.etf.pp1.ast;

public class AddExpr extends UnsignedExpr {

    private UnsignedExpr UnsignedExpr;
    private Addop Addop;
    private Term Term;

    public AddExpr (UnsignedExpr UnsignedExpr, Addop Addop, Term Term) {
        this.UnsignedExpr=UnsignedExpr;
        if(UnsignedExpr!=null) UnsignedExpr.setParent(this);
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public UnsignedExpr getUnsignedExpr() {
        return UnsignedExpr;
    }

    public void setUnsignedExpr(UnsignedExpr UnsignedExpr) {
        this.UnsignedExpr=UnsignedExpr;
    }

    public Addop getAddop() {
        return Addop;
    }

    public void setAddop(Addop Addop) {
        this.Addop=Addop;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(UnsignedExpr!=null) UnsignedExpr.accept(visitor);
        if(Addop!=null) Addop.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(UnsignedExpr!=null) UnsignedExpr.traverseTopDown(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(UnsignedExpr!=null) UnsignedExpr.traverseBottomUp(visitor);
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddExpr(\n");

        if(UnsignedExpr!=null)
            buffer.append(UnsignedExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Addop!=null)
            buffer.append(Addop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddExpr]");
        return buffer.toString();
    }
}
