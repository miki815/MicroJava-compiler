// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class NewVarDecl extends Vars {

    private VarExpr VarExpr;

    public NewVarDecl (VarExpr VarExpr) {
        this.VarExpr=VarExpr;
        if(VarExpr!=null) VarExpr.setParent(this);
    }

    public VarExpr getVarExpr() {
        return VarExpr;
    }

    public void setVarExpr(VarExpr VarExpr) {
        this.VarExpr=VarExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarExpr!=null) VarExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarExpr!=null) VarExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarExpr!=null) VarExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewVarDecl(\n");

        if(VarExpr!=null)
            buffer.append(VarExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewVarDecl]");
        return buffer.toString();
    }
}
