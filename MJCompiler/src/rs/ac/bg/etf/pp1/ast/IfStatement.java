// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:46


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends Statement {

    private IfStart IfStart;
    private Condition Condition;
    private IfEnd IfEnd;
    private Statement Statement;
    private ElseBlock ElseBlock;

    public IfStatement (IfStart IfStart, Condition Condition, IfEnd IfEnd, Statement Statement, ElseBlock ElseBlock) {
        this.IfStart=IfStart;
        if(IfStart!=null) IfStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.IfEnd=IfEnd;
        if(IfEnd!=null) IfEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseBlock=ElseBlock;
        if(ElseBlock!=null) ElseBlock.setParent(this);
    }

    public IfStart getIfStart() {
        return IfStart;
    }

    public void setIfStart(IfStart IfStart) {
        this.IfStart=IfStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public IfEnd getIfEnd() {
        return IfEnd;
    }

    public void setIfEnd(IfEnd IfEnd) {
        this.IfEnd=IfEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ElseBlock getElseBlock() {
        return ElseBlock;
    }

    public void setElseBlock(ElseBlock ElseBlock) {
        this.ElseBlock=ElseBlock;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfStart!=null) IfStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(IfEnd!=null) IfEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseBlock!=null) ElseBlock.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfStart!=null) IfStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(IfEnd!=null) IfEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseBlock!=null) ElseBlock.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfStart!=null) IfStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(IfEnd!=null) IfEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseBlock!=null) ElseBlock.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(IfStart!=null)
            buffer.append(IfStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfEnd!=null)
            buffer.append(IfEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseBlock!=null)
            buffer.append(ElseBlock.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
