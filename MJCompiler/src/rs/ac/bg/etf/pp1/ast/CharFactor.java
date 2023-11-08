// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:47


package rs.ac.bg.etf.pp1.ast;

public class CharFactor extends Factor {

    private Character facValue;

    public CharFactor (Character facValue) {
        this.facValue=facValue;
    }

    public Character getFacValue() {
        return facValue;
    }

    public void setFacValue(Character facValue) {
        this.facValue=facValue;
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
        buffer.append("CharFactor(\n");

        buffer.append(" "+tab+facValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharFactor]");
        return buffer.toString();
    }
}
