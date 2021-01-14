// generated with ast extension for cup
// version 0.8
// 14/0/2021 16:52:18


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclVars extends MethodDecl {

    private MethodTypeName MethodTypeName;
    private FormPars FormPars;
    private VarDeclarationBlock VarDeclarationBlock;
    private StatementBlock StatementBlock;

    public MethodDeclVars (MethodTypeName MethodTypeName, FormPars FormPars, VarDeclarationBlock VarDeclarationBlock, StatementBlock StatementBlock) {
        this.MethodTypeName=MethodTypeName;
        if(MethodTypeName!=null) MethodTypeName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.VarDeclarationBlock=VarDeclarationBlock;
        if(VarDeclarationBlock!=null) VarDeclarationBlock.setParent(this);
        this.StatementBlock=StatementBlock;
        if(StatementBlock!=null) StatementBlock.setParent(this);
    }

    public MethodTypeName getMethodTypeName() {
        return MethodTypeName;
    }

    public void setMethodTypeName(MethodTypeName MethodTypeName) {
        this.MethodTypeName=MethodTypeName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public VarDeclarationBlock getVarDeclarationBlock() {
        return VarDeclarationBlock;
    }

    public void setVarDeclarationBlock(VarDeclarationBlock VarDeclarationBlock) {
        this.VarDeclarationBlock=VarDeclarationBlock;
    }

    public StatementBlock getStatementBlock() {
        return StatementBlock;
    }

    public void setStatementBlock(StatementBlock StatementBlock) {
        this.StatementBlock=StatementBlock;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.accept(visitor);
        if(StatementBlock!=null) StatementBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodTypeName!=null) MethodTypeName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.traverseTopDown(visitor);
        if(StatementBlock!=null) StatementBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(VarDeclarationBlock!=null) VarDeclarationBlock.traverseBottomUp(visitor);
        if(StatementBlock!=null) StatementBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclVars(\n");

        if(MethodTypeName!=null)
            buffer.append(MethodTypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarationBlock!=null)
            buffer.append(VarDeclarationBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementBlock!=null)
            buffer.append(StatementBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclVars]");
        return buffer.toString();
    }
}
