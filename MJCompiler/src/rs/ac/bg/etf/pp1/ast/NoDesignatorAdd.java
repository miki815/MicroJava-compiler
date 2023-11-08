// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:47


package rs.ac.bg.etf.pp1.ast;

public class NoDesignatorAdd extends DesignatorAdd {

    public NoDesignatorAdd () {
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
        buffer.append("NoDesignatorAdd(\n");

        buffer.append(tab);
        buffer.append(") [NoDesignatorAdd]");
        return buffer.toString();
    }
}
