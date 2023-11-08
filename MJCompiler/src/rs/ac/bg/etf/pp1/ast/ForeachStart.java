// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:46


package rs.ac.bg.etf.pp1.ast;

public class ForeachStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String elem;

    public ForeachStart (String elem) {
        this.elem=elem;
    }

    public String getElem() {
        return elem;
    }

    public void setElem(String elem) {
        this.elem=elem;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("ForeachStart(\n");

        buffer.append(" "+tab+elem);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachStart]");
        return buffer.toString();
    }
}
