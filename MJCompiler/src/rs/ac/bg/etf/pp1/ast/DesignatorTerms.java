// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:47


package rs.ac.bg.etf.pp1.ast;

public class DesignatorTerms extends Designator {

    private DesignatorArray DesignatorArray;
    private DesignatorExpr DesignatorExpr;

    public DesignatorTerms (DesignatorArray DesignatorArray, DesignatorExpr DesignatorExpr) {
        this.DesignatorArray=DesignatorArray;
        if(DesignatorArray!=null) DesignatorArray.setParent(this);
        this.DesignatorExpr=DesignatorExpr;
        if(DesignatorExpr!=null) DesignatorExpr.setParent(this);
    }

    public DesignatorArray getDesignatorArray() {
        return DesignatorArray;
    }

    public void setDesignatorArray(DesignatorArray DesignatorArray) {
        this.DesignatorArray=DesignatorArray;
    }

    public DesignatorExpr getDesignatorExpr() {
        return DesignatorExpr;
    }

    public void setDesignatorExpr(DesignatorExpr DesignatorExpr) {
        this.DesignatorExpr=DesignatorExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArray!=null) DesignatorArray.accept(visitor);
        if(DesignatorExpr!=null) DesignatorExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArray!=null) DesignatorArray.traverseTopDown(visitor);
        if(DesignatorExpr!=null) DesignatorExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArray!=null) DesignatorArray.traverseBottomUp(visitor);
        if(DesignatorExpr!=null) DesignatorExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorTerms(\n");

        if(DesignatorArray!=null)
            buffer.append(DesignatorArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorExpr!=null)
            buffer.append(DesignatorExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorTerms]");
        return buffer.toString();
    }
}
