// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:47


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementList extends DesignatorStatement {

    private DesignatorAdd DesignatorAdd;
    private Designator Designator;

    public DesignatorStatementList (DesignatorAdd DesignatorAdd, Designator Designator) {
        this.DesignatorAdd=DesignatorAdd;
        if(DesignatorAdd!=null) DesignatorAdd.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public DesignatorAdd getDesignatorAdd() {
        return DesignatorAdd;
    }

    public void setDesignatorAdd(DesignatorAdd DesignatorAdd) {
        this.DesignatorAdd=DesignatorAdd;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorAdd!=null) DesignatorAdd.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorAdd!=null) DesignatorAdd.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorAdd!=null) DesignatorAdd.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementList(\n");

        if(DesignatorAdd!=null)
            buffer.append(DesignatorAdd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementList]");
        return buffer.toString();
    }
}
