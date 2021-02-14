// generated with ast extension for cup
// version 0.8
// 9/1/2021 10:7:30


package rs.ac.bg.etf.pp1.ast;

public class MultiVarDeclSemi extends VarDeclarationBlock {

    private VarDeclarationBlock VarDeclarationBlock;
    private VarDeclSemi VarDeclSemi;

    public MultiVarDeclSemi (VarDeclarationBlock VarDeclarationBlock, VarDeclSemi VarDeclSemi) {
        this.VarDeclarationBlock=VarDeclarationBlock;
        if(VarDeclarationBlock!=null) VarDeclarationBlock.setParent(this);
        this.VarDeclSemi=VarDeclSemi;
        if(VarDeclSemi!=null) VarDeclSemi.setParent(this);
    }

    public VarDeclarationBlock getVarDeclarationBlock() {
        return VarDeclarationBlock;
    }

    public void setVarDeclarationBlock(VarDeclarationBlock VarDeclarationBlock) {
        this.VarDeclarationBlock=VarDeclarationBlock;
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
        if(VarDeclarationBlock!=null) VarDeclarationBlock.accept(visitor);
        if(VarDeclSemi!=null) VarDeclSemi.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.traverseTopDown(visitor);
        if(VarDeclSemi!=null) VarDeclSemi.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclarationBlock!=null) VarDeclarationBlock.traverseBottomUp(visitor);
        if(VarDeclSemi!=null) VarDeclSemi.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiVarDeclSemi(\n");

        if(VarDeclarationBlock!=null)
            buffer.append(VarDeclarationBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclSemi!=null)
            buffer.append(VarDeclSemi.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiVarDeclSemi]");
        return buffer.toString();
    }
}
