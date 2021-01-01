// generated with ast extension for cup
// version 0.8
// 1/0/2021 21:46:19


package rs.ac.bg.etf.pp1.ast;

public class FuncCall extends Factor {

    private DesignatorFactor DesignatorFactor;

    public FuncCall (DesignatorFactor DesignatorFactor) {
        this.DesignatorFactor=DesignatorFactor;
        if(DesignatorFactor!=null) DesignatorFactor.setParent(this);
    }

    public DesignatorFactor getDesignatorFactor() {
        return DesignatorFactor;
    }

    public void setDesignatorFactor(DesignatorFactor DesignatorFactor) {
        this.DesignatorFactor=DesignatorFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorFactor!=null) DesignatorFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorFactor!=null) DesignatorFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorFactor!=null) DesignatorFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCall(\n");

        if(DesignatorFactor!=null)
            buffer.append(DesignatorFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCall]");
        return buffer.toString();
    }
}
