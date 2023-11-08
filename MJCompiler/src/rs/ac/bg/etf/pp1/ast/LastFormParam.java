// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class LastFormParam extends FormalParamList {

    private SingleFormParam SingleFormParam;

    public LastFormParam (SingleFormParam SingleFormParam) {
        this.SingleFormParam=SingleFormParam;
        if(SingleFormParam!=null) SingleFormParam.setParent(this);
    }

    public SingleFormParam getSingleFormParam() {
        return SingleFormParam;
    }

    public void setSingleFormParam(SingleFormParam SingleFormParam) {
        this.SingleFormParam=SingleFormParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleFormParam!=null) SingleFormParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleFormParam!=null) SingleFormParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleFormParam!=null) SingleFormParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LastFormParam(\n");

        if(SingleFormParam!=null)
            buffer.append(SingleFormParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LastFormParam]");
        return buffer.toString();
    }
}
