// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:44


package rs.ac.bg.etf.pp1.ast;

public class ConstructorDeclList extends ConstrunctorDeclList {

    private ConstrunctorDeclList ConstrunctorDeclList;
    private ConstrunctorDecl ConstrunctorDecl;

    public ConstructorDeclList (ConstrunctorDeclList ConstrunctorDeclList, ConstrunctorDecl ConstrunctorDecl) {
        this.ConstrunctorDeclList=ConstrunctorDeclList;
        if(ConstrunctorDeclList!=null) ConstrunctorDeclList.setParent(this);
        this.ConstrunctorDecl=ConstrunctorDecl;
        if(ConstrunctorDecl!=null) ConstrunctorDecl.setParent(this);
    }

    public ConstrunctorDeclList getConstrunctorDeclList() {
        return ConstrunctorDeclList;
    }

    public void setConstrunctorDeclList(ConstrunctorDeclList ConstrunctorDeclList) {
        this.ConstrunctorDeclList=ConstrunctorDeclList;
    }

    public ConstrunctorDecl getConstrunctorDecl() {
        return ConstrunctorDecl;
    }

    public void setConstrunctorDecl(ConstrunctorDecl ConstrunctorDecl) {
        this.ConstrunctorDecl=ConstrunctorDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstrunctorDeclList!=null) ConstrunctorDeclList.accept(visitor);
        if(ConstrunctorDecl!=null) ConstrunctorDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstrunctorDeclList!=null) ConstrunctorDeclList.traverseTopDown(visitor);
        if(ConstrunctorDecl!=null) ConstrunctorDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstrunctorDeclList!=null) ConstrunctorDeclList.traverseBottomUp(visitor);
        if(ConstrunctorDecl!=null) ConstrunctorDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorDeclList(\n");

        if(ConstrunctorDeclList!=null)
            buffer.append(ConstrunctorDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstrunctorDecl!=null)
            buffer.append(ConstrunctorDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorDeclList]");
        return buffer.toString();
    }
}
