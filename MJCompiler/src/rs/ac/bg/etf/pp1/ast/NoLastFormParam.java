// generated with ast extension for cup
// version 0.8
// 7/1/2023 2:57:45


package rs.ac.bg.etf.pp1.ast;

public class NoLastFormParam extends FormalParamList {

    private MultipleFormParam MultipleFormParam;
    private FormalParamList FormalParamList;

    public NoLastFormParam (MultipleFormParam MultipleFormParam, FormalParamList FormalParamList) {
        this.MultipleFormParam=MultipleFormParam;
        if(MultipleFormParam!=null) MultipleFormParam.setParent(this);
        this.FormalParamList=FormalParamList;
        if(FormalParamList!=null) FormalParamList.setParent(this);
    }

    public MultipleFormParam getMultipleFormParam() {
        return MultipleFormParam;
    }

    public void setMultipleFormParam(MultipleFormParam MultipleFormParam) {
        this.MultipleFormParam=MultipleFormParam;
    }

    public FormalParamList getFormalParamList() {
        return FormalParamList;
    }

    public void setFormalParamList(FormalParamList FormalParamList) {
        this.FormalParamList=FormalParamList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleFormParam!=null) MultipleFormParam.accept(visitor);
        if(FormalParamList!=null) FormalParamList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleFormParam!=null) MultipleFormParam.traverseTopDown(visitor);
        if(FormalParamList!=null) FormalParamList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleFormParam!=null) MultipleFormParam.traverseBottomUp(visitor);
        if(FormalParamList!=null) FormalParamList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoLastFormParam(\n");

        if(MultipleFormParam!=null)
            buffer.append(MultipleFormParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParamList!=null)
            buffer.append(FormalParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoLastFormParam]");
        return buffer.toString();
    }
}
