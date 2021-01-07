// generated with ast extension for cup
// version 0.8
// 7/0/2021 14:1:16


package rs.ac.bg.etf.pp1.ast;

public class BMDBlock extends BraceMethodDeclBlock {

    private MethodDeclBlock MethodDeclBlock;

    public BMDBlock (MethodDeclBlock MethodDeclBlock) {
        this.MethodDeclBlock=MethodDeclBlock;
        if(MethodDeclBlock!=null) MethodDeclBlock.setParent(this);
    }

    public MethodDeclBlock getMethodDeclBlock() {
        return MethodDeclBlock;
    }

    public void setMethodDeclBlock(MethodDeclBlock MethodDeclBlock) {
        this.MethodDeclBlock=MethodDeclBlock;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclBlock!=null) MethodDeclBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclBlock!=null) MethodDeclBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclBlock!=null) MethodDeclBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BMDBlock(\n");

        if(MethodDeclBlock!=null)
            buffer.append(MethodDeclBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BMDBlock]");
        return buffer.toString();
    }
}
