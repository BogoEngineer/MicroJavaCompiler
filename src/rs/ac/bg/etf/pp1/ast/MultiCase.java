// generated with ast extension for cup
// version 0.8
// 31/11/2020 16:3:33


package rs.ac.bg.etf.pp1.ast;

public class MultiCase extends SwitchCaseList {

    private SwitchCaseList SwitchCaseList;
    private Integer N2;
    private StatementList StatementList;

    public MultiCase (SwitchCaseList SwitchCaseList, Integer N2, StatementList StatementList) {
        this.SwitchCaseList=SwitchCaseList;
        if(SwitchCaseList!=null) SwitchCaseList.setParent(this);
        this.N2=N2;
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public SwitchCaseList getSwitchCaseList() {
        return SwitchCaseList;
    }

    public void setSwitchCaseList(SwitchCaseList SwitchCaseList) {
        this.SwitchCaseList=SwitchCaseList;
    }

    public Integer getN2() {
        return N2;
    }

    public void setN2(Integer N2) {
        this.N2=N2;
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
        if(SwitchCaseList!=null) SwitchCaseList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchCaseList!=null) SwitchCaseList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchCaseList!=null) SwitchCaseList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiCase(\n");

        if(SwitchCaseList!=null)
            buffer.append(SwitchCaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+N2);
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiCase]");
        return buffer.toString();
    }
}
