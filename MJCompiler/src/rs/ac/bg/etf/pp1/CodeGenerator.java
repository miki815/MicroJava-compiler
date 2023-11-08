package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	private Stack<Obj> multipleAssignmentStack = new Stack<Obj>();
	private Stack<List<Integer>> andConditionFixupStack = new Stack<List<Integer>>();
	private Stack<List<Integer>> orConditionFixupStack = new Stack<List<Integer>>();
	private Stack<List<Integer>> continueStatementFixupStack = new Stack<List<Integer>>();
	private Stack<List<Integer>> breakStatementFixupStack = new Stack<List<Integer>>();
	private Stack<List<Integer>> ifStatementFixupStack = new Stack<List<Integer>>();
	private Stack<Integer> whileStartStack =  new Stack<Integer>(); 
	private Obj arrayLength = new Obj(Obj.Con, "arrayLength", new Struct(1));
	private Stack<Obj> foreachArrayIndex = new Stack<Obj>();
	private Stack<Integer> foreachStartStack = new Stack<Integer>();
	private Stack<Integer> foreachEndFixup = new Stack<Integer>();
	private Stack<Obj> foreachArrayAddress = new Stack<Obj>();
	Collection<Obj> locals;
	private Stack<Obj> actuals = new Stack<Obj>();
	private int jump1;

	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(PrintStatement printStatement) {
		if(printStatement.getExpr().struct == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}
	
	public void visit(PrintNumberStatement printStatement) { 
		Code.loadConst(printStatement.getWidth());
		if(printStatement.getExpr().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}
	
	public void visit(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);			
		} else {
			Code.put(Code.read);
		}		
		Code.store(readStatement.getDesignator().obj);
	}
	
	public void visit(NumFactor numConst) {
		Obj con = Tab.insert(Obj.Con, "$", numConst.struct);
		con.setLevel(0);
		con.setAdr(numConst.getFacValue());	
		Code.load(con); // put constant on stack
	}
	
	public void visit(CharFactor factorCharConst) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.charType);
		con.setLevel(0);
		con.setAdr(factorCharConst.getFacValue());			
		Code.load(con);
	}
	
	public void visit(BoolFactor boolConst) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.charType);
		con.setLevel(0);
		int constValue = boolConst.getFacValue() == true ? 1 : 0;
		con.setAdr(constValue);			
		Code.load(con);
	}
	
	public void visit(MethodTypeName methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
	/*	VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);*/
		
		// Generate the entry


		Code.put(Code.enter);
	    // Code.put(fpCnt.getCount());
		// Code.put(fpCnt.getCount() + varCnt.getCount());
		Code.put(methodTypeName.obj.getLevel()); // level is number of formal arguments
		Code.put(methodTypeName.obj.getLocalSymbols().size()); 
		//System.out.println(methodTypeName.getMethName() + " " + fpCnt.getCount() + " " +  varCnt.getCount());
		System.out.println(methodTypeName.getMethName() + " " + methodTypeName.obj.getLevel() + " " +  methodTypeName.obj.getLocalSymbols().size());

	}
	
	public void visit(MethodDeclGood methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AssignOption assignOption){
		// insert calculated value into designator (var, array elem)
		Code.store(assignOption.getDesignator().obj);
	}
	
	/*public void visit(DesignatorIdent designator){
		SyntaxNode parent = designator.getParent();	
		if(AssignOption.class != parent.getClass() &&
				DesignatorFuncCall.class != parent.getClass() && 
				FuncCallOption.class != parent.getClass()){
			Code.load(designator.obj);
		}
	}*/
	
	public void visit(DesignatorArray desArrayElem) {
		// push array elem on expression stack
		Code.load(desArrayElem.obj);
	}
	
	public void visit(DesignatorFuncCall funcCall){
		Obj functionObj = funcCall.getDesignator().obj;		
		if("ord".equals(functionObj.getName())) {
			System.out.println("ord found");
			return;
		}

		if("chr".equals(functionObj.getName())) {
			return;
		}
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	

	
	public void visit(FuncCallOption procCall){
		Obj functionObj = procCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if(procCall.getDesignator().obj.getType() != Tab.noType){
			Code.put(Code.pop);
		}
	}
	

	
	public void visit(ReturnExprStatement returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ReturnVoidStatement returnNoExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
		
	public void visit(IncOption incOption) { 
		Obj incDesignator = incOption.getDesignator().obj;
		if(incDesignator.getKind() == Obj.Var) {		
			Code.load(incDesignator);	
		}  
		else if(incDesignator.getKind() == Obj.Elem) {
			Code.put(Code.dup2); 
			Code.load(incDesignator);
		}
		Code.loadConst(1); 
		Code.put(Code.add);
		Code.store(incDesignator);
	}
	
	public void visit(DecOption decOption) {
		Obj decDesignator = decOption.getDesignator().obj;
		if(decDesignator.getKind() == Obj.Var) {
			Code.load(decDesignator);
		}
		else if(decDesignator.getKind() == Obj.Elem) {
			Code.put(Code.dup2); 
			Code.load(decDesignator);
		}
		Code.loadConst(1); 
		Code.put(Code.sub);
		Code.store(decDesignator);
	}
	
	public void visit(AddopExprList addopExpr) {
		if(addopExpr.getAddop() instanceof AddopPlus) {
			Code.put(Code.add); // +
		} else {
			Code.put(Code.sub); // -
		}
	}
	
	public void visit(MulopTerm mulopExpr) {
		if(mulopExpr.getMulop() instanceof MulopDiv) Code.put(Code.div); // \
		else if (mulopExpr.getMulop() instanceof MulopMul) Code.put(Code.mul); // *
		else Code.put(Code.rem); // %
	}
	
	public void visit(MinExpr minExpr) {
		Code.put(Code.neg);
	}
	
	public void visit(NewArrayFactor newArray) {
		Code.put(Code.newarray);		
		if(newArray.struct.getElemType() == Tab.charType) Code.put(0);
		else Code.put(1);	
	}
	
	public void visit(DesignatorVar desVar) {
		Code.load(desVar.getDesignator().obj);
	}
	
	// multiple assignment
	
	public void visit(DesignatorArrayIndex desElem) {
		multipleAssignmentStack.push(desElem.getDesignator().obj);
	}
	
	public void visit(NoDesignatorArrayIndex skipDesElem) {
		multipleAssignmentStack.push(new Obj(Obj.Var,"dummyObj", new Struct(0)));
	}
	
	public void visit(NoDesignatorAdd skipDesElem) {
		multipleAssignmentStack.push(new Obj(Obj.Var,"dummyObj", new Struct(0)));
	}
	
	public void visit(FirstElem firstElem) {
		multipleAssignmentStack.push(firstElem.getDesignator().obj);
	}
		
	/*public void visit(DesignatorStatementList desMulti) {
		Obj arrayDesignator = desMulti.getDesignator().obj;
		int arrayIndex = multipleAssignmentStack.size() - 1;
		while(!multipleAssignmentStack.empty()) {
			Code.load(arrayDesignator);
			Code.loadConst(arrayIndex--);
			Code.load(new Obj(Obj.Elem, "elemValue", new Struct(arrayDesignator.getType().getKind())));
			Code.store(multipleAssignmentStack.pop());
		}
	}*/
	
	public void visit(DesignatorStatementList desMulti) {
		Obj arrayDesignator = desMulti.getDesignator().obj;
		int arrayIndex = multipleAssignmentStack.size() - 1;
		Code.load(arrayDesignator);
		Code.put(Code.arraylength);
		Code.loadConst(multipleAssignmentStack.size());
		Code.putFalseJump(Code.lt ,0);
		jump1 = Code.pc - 2;
		Code.put(Code.trap);
		Code.put(3);
		Code.fixup(jump1);
		while(!multipleAssignmentStack.empty()) {
			Code.load(arrayDesignator);
			Code.loadConst(arrayIndex--);
			Code.load(new Obj(Obj.Elem, "elemValue", new Struct(arrayDesignator.getType().getKind())));
			Code.store(multipleAssignmentStack.pop());
		}
	}
	
	// level B
	
	// If AND condition expression is false jump to next OR condition if exists 
	// If next OR condition does not exist jump behind this code block or in else statement if exists
	public void visit(RelopFact relopFact) {
		if(relopFact.getRelop() instanceof Relopeq) 
			Code.putFalseJump(Code.eq, 0); 			
		else if(relopFact.getRelop() instanceof Relopneq) 
			Code.putFalseJump(Code.ne, 0); 			
		else if(relopFact.getRelop() instanceof Reloplt) 
			Code.putFalseJump(Code.lt, 0); 			
		else if(relopFact.getRelop() instanceof Relople) 
			Code.putFalseJump(Code.le, 0); 
		else if(relopFact.getRelop() instanceof Relopgt) 
			Code.putFalseJump(Code.gt, 0); 			
		else if(relopFact.getRelop() instanceof Relopge) 
			Code.putFalseJump(Code.ge, 0); 	
		andConditionFixupStack.peek().add(Code.pc - 2); 
	}

	public void visit(Fact fact) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); 
		andConditionFixupStack.peek().add(Code.pc - 2); 
	}
	// This is begining of while loop
	// Initialize all fixup lists and push starting address on stack
	public void visit(WhileStart whileStart) {
		whileStartStack.push(Code.pc);
		andConditionFixupStack.push(new ArrayList<Integer>());
		orConditionFixupStack.push(new ArrayList<Integer>());
		continueStatementFixupStack.push(new ArrayList<Integer>());
		breakStatementFixupStack.push(new ArrayList<Integer>());
	}
	
	// This is end of while loop
	// Unconditionaly jump to the begining of while loop
	// Pop starting address and all fixup lists from stack
	// Now we have jumping address for break and false condition statements 
	public void visit(WhileStatement whileStatement) { // while end
		Code.putJump(whileStartStack.peek());
		for(int fixupWaiting: breakStatementFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		for(int fixupWaiting: andConditionFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		orConditionFixupStack.pop();
		andConditionFixupStack.pop();
		continueStatementFixupStack.pop();
		breakStatementFixupStack.pop();
		whileStartStack.pop();
	}
	
	/*public void visit(WhileConditionStart whileConditionStart) {
		for(int fixupWaiting: continueStatementFixupStack.peek()) 
			Code.fixup(fixupWaiting);	
		continueStatementFixupStack.peek().clear();
	}*/
	
	// While block starts here 
	// If single OR condition is true then we should jump here and execute statements in while block
	public void visit(WhileConditionEnd whileConditionEnd) {
		for(int fixupWaiting: orConditionFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		orConditionFixupStack.peek().clear(); 
	}
	
	public void visit(IfStart ifStart) {
		andConditionFixupStack.push(new ArrayList<Integer>());
		orConditionFixupStack.push(new ArrayList<Integer>());
		ifStatementFixupStack.push(new ArrayList<Integer>());
	}
	
	public void visit(IfStatement ifElseStatement) {
		andConditionFixupStack.pop();
		orConditionFixupStack.pop();
		ifStatementFixupStack.pop();
	}
	
	public void visit(IfEnd ifEnd) {
		for(int fixupWaiting: orConditionFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		orConditionFixupStack.peek().clear(); 
	}
	
	public void visit(ElseBlock elseBlock) {
		SyntaxNode parent = elseBlock.getParent();
		if(parent instanceof IfElseStatement) {
			Code.putJump(0); 		
			ifStatementFixupStack.peek().add(Code.pc - 2); 
		}		
		for(int fixupWaiting: andConditionFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		andConditionFixupStack.peek().clear(); 
	}
	
	// Continue statement unconditionaly jumps to the begining of while or foreach loop 
	// We already have jumping address so there is no need for fixup
	public void visit(ContinueStatement continueStatement) {
		Code.putJump(whileStartStack.peek());
		// continueStatementFixupStack.peek().add(Code.pc - 2);
	}
	
	// Break statement should jump out of while or foreach loop
	// We will found jumping address after loop ends
	public void visit(BreakStatement breakStatement) {
		Code.putJump(0); // 1b instruction code, 2b address
		breakStatementFixupStack.peek().add(Code.pc - 2); // address start pointer
	}
	
	public void visit(IfElseStatement ifElseStatement) {
		for(int fixupWaiting: ifStatementFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		
		ifStatementFixupStack.peek().clear(); 
		ifStatementFixupStack.pop();
		andConditionFixupStack.pop();
		orConditionFixupStack.pop();
	}
	
	// If we are here then single OR condition is true 
	// We jump in block of code after this condition
	public void visit(OrNext orNext) {
		Code.putJump(0); 		
		orConditionFixupStack.peek().add(Code.pc - 2);
				
		for(int fixupWaiting: andConditionFixupStack.peek()) 
			Code.fixup(fixupWaiting);
		
		andConditionFixupStack.peek().clear(); 
	}
	
	
	// Foreach implementation
	
	public void visit(ForeachArray foreachArray) {
		foreachArrayIndex.push(new Obj(Obj.Var, "index", Tab.intType));
		Code.loadConst(0);
		Code.store(foreachArrayIndex.peek());
		Code.load(foreachArray.getDesignator().obj);
		foreachArrayAddress.push(foreachArray.getDesignator().obj);
		foreachStartStack.push(Code.pc);

	//	Code.store(foreachArrayLength.peek());
	}
	
	public void visit(ForeachStart foreachStart) {
		Code.put(Code.dup);
		Code.put(Code.arraylength);
		Code.load(foreachArrayIndex.peek());
		Code.putFalseJump(Code.ne, 0);
		foreachEndFixup.push(Code.pc - 2);
		Code.load(foreachArrayIndex.peek());
		Code.put(Code.dup);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(foreachArrayIndex.peek());
//		Code.put(Code.dup2);
		Obj dummyObj = new Obj(Obj.Elem, "dummy", foreachStart.obj.getType());
		Code.load(dummyObj);
		Code.store(foreachStart.obj);
	}
	
	public void visit(ForeachStatement foreachFinish) {
		foreachStartStack.pop();
		foreachArrayIndex.pop();
		foreachEndFixup.pop();
		foreachArrayAddress.pop();

	}
	
	public void visit(ForeachFinish foreachStatementFinish) {
		Code.load(foreachArrayAddress.peek());
		Code.putJump(foreachStartStack.peek());
		Code.fixup(foreachEndFixup.peek());
		Code.put(Code.pop);
	}
	
}
