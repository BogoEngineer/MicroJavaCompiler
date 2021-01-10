// generated with ast extension for cup
// version 0.8
// 10/0/2021 17:14:22


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStmt extends Statement {

    private StatementBlock StatementBlock;
    private Condition Condition;

    public DoWhileStmt (StatementBlock StatementBlock, Condition Condition) {
        this.StatementBlock=StatementBlock;
        if(StatementBlock!=null) StatementBlock.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public StatementBlock getStatementBlock() {
        return StatementBlock;
    }

    public void setStatementBlock(StatementBlock StatementBlock) {
        this.StatementBlock=StatementBlock;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementBlock!=null) StatementBlock.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementBlock!=null) StatementBlock.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementBlock!=null) StatementBlock.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStmt(\n");

        if(StatementBlock!=null)
            buffer.append(StatementBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStmt]");
        return buffer.toString();
    }
}
