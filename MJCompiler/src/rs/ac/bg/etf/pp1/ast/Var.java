// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class Var extends VarList {

    private VarExprLast VarExprLast;

    public Var (VarExprLast VarExprLast) {
        this.VarExprLast=VarExprLast;
        if(VarExprLast!=null) VarExprLast.setParent(this);
    }

    public VarExprLast getVarExprLast() {
        return VarExprLast;
    }

    public void setVarExprLast(VarExprLast VarExprLast) {
        this.VarExprLast=VarExprLast;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarExprLast!=null) VarExprLast.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarExprLast!=null) VarExprLast.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarExprLast!=null) VarExprLast.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Var(\n");

        if(VarExprLast!=null)
            buffer.append(VarExprLast.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Var]");
        return buffer.toString();
    }
}
