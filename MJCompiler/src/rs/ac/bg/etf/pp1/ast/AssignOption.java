// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:47


package rs.ac.bg.etf.pp1.ast;

public class AssignOption extends DesignatorStatement {

    private Designator Designator;
    private AssignDesignatorOption AssignDesignatorOption;

    public AssignOption (Designator Designator, AssignDesignatorOption AssignDesignatorOption) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.AssignDesignatorOption=AssignDesignatorOption;
        if(AssignDesignatorOption!=null) AssignDesignatorOption.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public AssignDesignatorOption getAssignDesignatorOption() {
        return AssignDesignatorOption;
    }

    public void setAssignDesignatorOption(AssignDesignatorOption AssignDesignatorOption) {
        this.AssignDesignatorOption=AssignDesignatorOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(AssignDesignatorOption!=null) AssignDesignatorOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(AssignDesignatorOption!=null) AssignDesignatorOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(AssignDesignatorOption!=null) AssignDesignatorOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignOption(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AssignDesignatorOption!=null)
            buffer.append(AssignDesignatorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignOption]");
        return buffer.toString();
    }
}
