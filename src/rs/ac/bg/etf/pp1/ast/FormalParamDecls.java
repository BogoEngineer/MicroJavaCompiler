// generated with ast extension for cup
// version 0.8
// 14/0/2021 11:50:30


package rs.ac.bg.etf.pp1.ast;

public class FormalParamDecls extends FormalParamList {

    private FormalParamList FormalParamList;
    private FormalParamDecl FormalParamDecl;

    public FormalParamDecls (FormalParamList FormalParamList, FormalParamDecl FormalParamDecl) {
        this.FormalParamList=FormalParamList;
        if(FormalParamList!=null) FormalParamList.setParent(this);
        this.FormalParamDecl=FormalParamDecl;
        if(FormalParamDecl!=null) FormalParamDecl.setParent(this);
    }

    public FormalParamList getFormalParamList() {
        return FormalParamList;
    }

    public void setFormalParamList(FormalParamList FormalParamList) {
        this.FormalParamList=FormalParamList;
    }

    public FormalParamDecl getFormalParamDecl() {
        return FormalParamDecl;
    }

    public void setFormalParamDecl(FormalParamDecl FormalParamDecl) {
        this.FormalParamDecl=FormalParamDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParamList!=null) FormalParamList.accept(visitor);
        if(FormalParamDecl!=null) FormalParamDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParamList!=null) FormalParamList.traverseTopDown(visitor);
        if(FormalParamDecl!=null) FormalParamDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParamList!=null) FormalParamList.traverseBottomUp(visitor);
        if(FormalParamDecl!=null) FormalParamDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParamDecls(\n");

        if(FormalParamList!=null)
            buffer.append(FormalParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParamDecl!=null)
            buffer.append(FormalParamDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParamDecls]");
        return buffer.toString();
    }
}
