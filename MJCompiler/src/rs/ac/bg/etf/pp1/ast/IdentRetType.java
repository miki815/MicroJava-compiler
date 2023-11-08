// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class IdentRetType extends ReturnType {

    private String retType;

    public IdentRetType (String retType) {
        this.retType=retType;
    }

    public String getRetType() {
        return retType;
    }

    public void setRetType(String retType) {
        this.retType=retType;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentRetType(\n");

        buffer.append(" "+tab+retType);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentRetType]");
        return buffer.toString();
    }
}
