// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class SingleConst extends ConstList {

    private ConstExpr ConstExpr;

    public SingleConst (ConstExpr ConstExpr) {
        this.ConstExpr=ConstExpr;
        if(ConstExpr!=null) ConstExpr.setParent(this);
    }

    public ConstExpr getConstExpr() {
        return ConstExpr;
    }

    public void setConstExpr(ConstExpr ConstExpr) {
        this.ConstExpr=ConstExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstExpr!=null) ConstExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstExpr!=null) ConstExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstExpr!=null) ConstExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConst(\n");

        if(ConstExpr!=null)
            buffer.append(ConstExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConst]");
        return buffer.toString();
    }
}
