// generated with ast extension for cup
// version 0.8
// 11/0/2021 11:12:24


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private Extending Extending;
    private VarDeclarationBlock VarDeclarationBlock;
    private BraceMethodDeclBlock BraceMethodDeclBlock;

    public ClassDecl (String I1, Extending Extending, VarDeclarationBlock VarDeclarationBlock, BraceMethodDeclBlock BraceMethodDeclBlock) {
        this.I1=I1;
        this.Extending=Extending;
        if(Extending!=null) Extending.setParent(this);
        this.VarDeclarationBlock=VarDeclarationBlock;
        if(VarDeclarationBlock!=null) VarDeclarationBlock.setParent(this);
        this.BraceMethodDeclBlock=BraceMethodDeclBlock;
        if(BraceMethodDeclBlock!=null) BraceMethodDeclBlock.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public Extending getExtending() {
        return Extending;
    }

    public void setExtending(Extending Extending) {
        this.Extending=Extending;
    }

    public VarDeclarationBlock getVarDeclarationBlock() {
        return VarDeclarationBlock;
    }

    public void setVarDeclarationBlock(VarDeclarationBlock VarDeclarationBlock) {
        this.VarDeclarationBlock=VarDeclarationBlock;
    }

    public BraceMethodDeclBlock getBraceMethodDeclBlock() {
        return BraceMethodDeclBlock;
    }

    public void setBraceMethodDeclBlock(BraceMethodDeclBlock BraceMethodDeclBlock) {
        this.BraceMethodDeclBlock=BraceMethodDeclBlock;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Extending!=null) Extending.accept(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.accept(visitor);
        if(BraceMethodDeclBlock!=null) BraceMethodDeclBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Extending!=null) Extending.traverseTopDown(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.traverseTopDown(visitor);
        if(BraceMethodDeclBlock!=null) BraceMethodDeclBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Extending!=null) Extending.traverseBottomUp(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.traverseBottomUp(visitor);
        if(BraceMethodDeclBlock!=null) BraceMethodDeclBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(Extending!=null)
            buffer.append(Extending.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarationBlock!=null)
            buffer.append(VarDeclarationBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BraceMethodDeclBlock!=null)
            buffer.append(BraceMethodDeclBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
