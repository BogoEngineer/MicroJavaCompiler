// generated with ast extension for cup
// version 0.8
// 14/0/2021 16:52:18


package rs.ac.bg.etf.pp1.ast;

public class SingleCase extends SwitchCaseList {

    private Integer N1;
    private StatementList StatementList;

    public SingleCase (Integer N1, StatementList StatementList) {
        this.N1=N1;
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleCase(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleCase]");
        return buffer.toString();
    }
}
