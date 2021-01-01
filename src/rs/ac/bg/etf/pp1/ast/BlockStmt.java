// generated with ast extension for cup
// version 0.8
// 1/0/2021 21:46:19


package rs.ac.bg.etf.pp1.ast;

public class BlockStmt extends Matched {

    private StatementBlock StatementBlock;

    public BlockStmt (StatementBlock StatementBlock) {
        this.StatementBlock=StatementBlock;
        if(StatementBlock!=null) StatementBlock.setParent(this);
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
        if(StatementBlock!=null) StatementBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementBlock!=null) StatementBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementBlock!=null) StatementBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BlockStmt(\n");

        if(StatementBlock!=null)
            buffer.append(StatementBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BlockStmt]");
        return buffer.toString();
    }
}
