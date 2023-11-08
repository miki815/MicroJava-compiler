// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:46


package rs.ac.bg.etf.pp1.ast;

public class WhileStatement extends Statement {

    private WhileStart WhileStart;
    private WhileConditionStart WhileConditionStart;
    private Condition Condition;
    private WhileConditionEnd WhileConditionEnd;
    private Statement Statement;

    public WhileStatement (WhileStart WhileStart, WhileConditionStart WhileConditionStart, Condition Condition, WhileConditionEnd WhileConditionEnd, Statement Statement) {
        this.WhileStart=WhileStart;
        if(WhileStart!=null) WhileStart.setParent(this);
        this.WhileConditionStart=WhileConditionStart;
        if(WhileConditionStart!=null) WhileConditionStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.WhileConditionEnd=WhileConditionEnd;
        if(WhileConditionEnd!=null) WhileConditionEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public WhileStart getWhileStart() {
        return WhileStart;
    }

    public void setWhileStart(WhileStart WhileStart) {
        this.WhileStart=WhileStart;
    }

    public WhileConditionStart getWhileConditionStart() {
        return WhileConditionStart;
    }

    public void setWhileConditionStart(WhileConditionStart WhileConditionStart) {
        this.WhileConditionStart=WhileConditionStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public WhileConditionEnd getWhileConditionEnd() {
        return WhileConditionEnd;
    }

    public void setWhileConditionEnd(WhileConditionEnd WhileConditionEnd) {
        this.WhileConditionEnd=WhileConditionEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileStart!=null) WhileStart.accept(visitor);
        if(WhileConditionStart!=null) WhileConditionStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(WhileConditionEnd!=null) WhileConditionEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileStart!=null) WhileStart.traverseTopDown(visitor);
        if(WhileConditionStart!=null) WhileConditionStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(WhileConditionEnd!=null) WhileConditionEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileStart!=null) WhileStart.traverseBottomUp(visitor);
        if(WhileConditionStart!=null) WhileConditionStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(WhileConditionEnd!=null) WhileConditionEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileStatement(\n");

        if(WhileStart!=null)
            buffer.append(WhileStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileConditionStart!=null)
            buffer.append(WhileConditionStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileConditionEnd!=null)
            buffer.append(WhileConditionEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileStatement]");
        return buffer.toString();
    }
}
