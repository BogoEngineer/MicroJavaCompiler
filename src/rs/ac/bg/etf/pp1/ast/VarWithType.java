// generated with ast extension for cup
// version 0.8
// 7/0/2021 14:1:16


package rs.ac.bg.etf.pp1.ast;

public class VarWithType extends VarDecl {

    private Type Type;
    private VarNoType VarNoType;

    public VarWithType (Type Type, VarNoType VarNoType) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarNoType=VarNoType;
        if(VarNoType!=null) VarNoType.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarNoType getVarNoType() {
        return VarNoType;
    }

    public void setVarNoType(VarNoType VarNoType) {
        this.VarNoType=VarNoType;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarNoType!=null) VarNoType.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarNoType!=null) VarNoType.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarNoType!=null) VarNoType.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarWithType(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarNoType!=null)
            buffer.append(VarNoType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarWithType]");
        return buffer.toString();
    }
}
