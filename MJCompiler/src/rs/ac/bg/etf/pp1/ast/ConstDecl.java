// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ConstDeclType ConstDeclType;
    private ConstList ConstList;

    public ConstDecl (ConstDeclType ConstDeclType, ConstList ConstList) {
        this.ConstDeclType=ConstDeclType;
        if(ConstDeclType!=null) ConstDeclType.setParent(this);
        this.ConstList=ConstList;
        if(ConstList!=null) ConstList.setParent(this);
    }

    public ConstDeclType getConstDeclType() {
        return ConstDeclType;
    }

    public void setConstDeclType(ConstDeclType ConstDeclType) {
        this.ConstDeclType=ConstDeclType;
    }

    public ConstList getConstList() {
        return ConstList;
    }

    public void setConstList(ConstList ConstList) {
        this.ConstList=ConstList;
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
        if(ConstDeclType!=null) ConstDeclType.accept(visitor);
        if(ConstList!=null) ConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclType!=null) ConstDeclType.traverseTopDown(visitor);
        if(ConstList!=null) ConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclType!=null) ConstDeclType.traverseBottomUp(visitor);
        if(ConstList!=null) ConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(ConstDeclType!=null)
            buffer.append(ConstDeclType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstList!=null)
            buffer.append(ConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
