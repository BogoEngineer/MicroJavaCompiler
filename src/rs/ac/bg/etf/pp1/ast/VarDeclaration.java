// generated with ast extension for cup
// version 0.8
// 31/11/2020 16:3:33


package rs.ac.bg.etf.pp1.ast;

public class VarDeclaration extends Declaration {

    private VarDeclSemi VarDeclSemi;

    public VarDeclaration (VarDeclSemi VarDeclSemi) {
        this.VarDeclSemi=VarDeclSemi;
        if(VarDeclSemi!=null) VarDeclSemi.setParent(this);
    }

    public VarDeclSemi getVarDeclSemi() {
        return VarDeclSemi;
    }

    public void setVarDeclSemi(VarDeclSemi VarDeclSemi) {
        this.VarDeclSemi=VarDeclSemi;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclSemi!=null) VarDeclSemi.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclSemi!=null) VarDeclSemi.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclSemi!=null) VarDeclSemi.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclaration(\n");

        if(VarDeclSemi!=null)
            buffer.append(VarDeclSemi.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclaration]");
        return buffer.toString();
    }
}
