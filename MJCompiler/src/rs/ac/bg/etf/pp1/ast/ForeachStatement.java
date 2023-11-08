// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:46


package rs.ac.bg.etf.pp1.ast;

public class ForeachStatement extends Statement {

    private ForeachArray ForeachArray;
    private ForeachStart ForeachStart;
    private Statement Statement;
    private ForeachFinish ForeachFinish;

    public ForeachStatement (ForeachArray ForeachArray, ForeachStart ForeachStart, Statement Statement, ForeachFinish ForeachFinish) {
        this.ForeachArray=ForeachArray;
        if(ForeachArray!=null) ForeachArray.setParent(this);
        this.ForeachStart=ForeachStart;
        if(ForeachStart!=null) ForeachStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ForeachFinish=ForeachFinish;
        if(ForeachFinish!=null) ForeachFinish.setParent(this);
    }

    public ForeachArray getForeachArray() {
        return ForeachArray;
    }

    public void setForeachArray(ForeachArray ForeachArray) {
        this.ForeachArray=ForeachArray;
    }

    public ForeachStart getForeachStart() {
        return ForeachStart;
    }

    public void setForeachStart(ForeachStart ForeachStart) {
        this.ForeachStart=ForeachStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ForeachFinish getForeachFinish() {
        return ForeachFinish;
    }

    public void setForeachFinish(ForeachFinish ForeachFinish) {
        this.ForeachFinish=ForeachFinish;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForeachArray!=null) ForeachArray.accept(visitor);
        if(ForeachStart!=null) ForeachStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ForeachFinish!=null) ForeachFinish.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachArray!=null) ForeachArray.traverseTopDown(visitor);
        if(ForeachStart!=null) ForeachStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ForeachFinish!=null) ForeachFinish.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachArray!=null) ForeachArray.traverseBottomUp(visitor);
        if(ForeachStart!=null) ForeachStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ForeachFinish!=null) ForeachFinish.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachStatement(\n");

        if(ForeachArray!=null)
            buffer.append(ForeachArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachStart!=null)
            buffer.append(ForeachStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachFinish!=null)
            buffer.append(ForeachFinish.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachStatement]");
        return buffer.toString();
    }
}
