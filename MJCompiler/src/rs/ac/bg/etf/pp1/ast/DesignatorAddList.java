// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:47


package rs.ac.bg.etf.pp1.ast;

public class DesignatorAddList extends DesignatorAdd {

    private DesignatorAdd DesignatorAdd;
    private DesignatorArrayIndexWant DesignatorArrayIndexWant;

    public DesignatorAddList (DesignatorAdd DesignatorAdd, DesignatorArrayIndexWant DesignatorArrayIndexWant) {
        this.DesignatorAdd=DesignatorAdd;
        if(DesignatorAdd!=null) DesignatorAdd.setParent(this);
        this.DesignatorArrayIndexWant=DesignatorArrayIndexWant;
        if(DesignatorArrayIndexWant!=null) DesignatorArrayIndexWant.setParent(this);
    }

    public DesignatorAdd getDesignatorAdd() {
        return DesignatorAdd;
    }

    public void setDesignatorAdd(DesignatorAdd DesignatorAdd) {
        this.DesignatorAdd=DesignatorAdd;
    }

    public DesignatorArrayIndexWant getDesignatorArrayIndexWant() {
        return DesignatorArrayIndexWant;
    }

    public void setDesignatorArrayIndexWant(DesignatorArrayIndexWant DesignatorArrayIndexWant) {
        this.DesignatorArrayIndexWant=DesignatorArrayIndexWant;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorAdd!=null) DesignatorAdd.accept(visitor);
        if(DesignatorArrayIndexWant!=null) DesignatorArrayIndexWant.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorAdd!=null) DesignatorAdd.traverseTopDown(visitor);
        if(DesignatorArrayIndexWant!=null) DesignatorArrayIndexWant.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorAdd!=null) DesignatorAdd.traverseBottomUp(visitor);
        if(DesignatorArrayIndexWant!=null) DesignatorArrayIndexWant.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorAddList(\n");

        if(DesignatorAdd!=null)
            buffer.append(DesignatorAdd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorArrayIndexWant!=null)
            buffer.append(DesignatorArrayIndexWant.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorAddList]");
        return buffer.toString();
    }
}
