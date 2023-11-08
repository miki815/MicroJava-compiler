package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;


public class SemanticPass extends VisitorAdaptor {
	int printCallCount = 0;
	int varDeclCount = 0;
	Obj currentMethod = null;
	boolean errorDetected = false;
	private Struct currentType = null;
	private int formParsCount = 0;
	private int blockDepth = 0; // increment in while and foreach statement
	private boolean mainFound = false; // flag to indicate main method found
	private Stack<List<Struct>> actParsStack = new Stack<List<Struct>>();
	int nVars;
	public static final Struct boolType = new Struct(Struct.Bool);
	
	enum RelopEnum {EQ, NEQ, LE, LT, GE, GT}
	private RelopEnum currentRelop = null;
	
	public SemanticPass() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	private boolean checkMultipleDeclaration(String constantName, SyntaxNode info) {
	    Obj constNameNode = Tab.find(constantName);
	    if(constNameNode != Tab.noObj) {
	    // multiple declaration found
			report_error("Ime " + constantName + " je vec deklarisano!", info);    		
			return true;
	    }
	    return false; 
	}
	
	Logger log = Logger.getLogger(getClass());

    
    // semanticka
    
    public void visit(ProgName pName) {
    	pName.obj = Tab.insert(Obj.Prog, pName.getPName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(Program program) {
    	if(mainFound == false)
			report_error("Glavna funkcija void main() ne postoji.", null);
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj); 
    	Tab.closeScope();
    }
    
	public void visit(VarDecl vardecl){
		varDeclCount++;
	}
	
	public void visit(MethodTypeName methodTypeName){
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getReturnType().struct);
		methodTypeName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
	}
	
	public void visit(MethodDeclGood methodDecl) {
		currentMethod.setLevel(formParsCount);
		if("main".equals(currentMethod.getName()) && (currentMethod.getLevel() == 0) && currentMethod.getType() == Tab.noType)
			mainFound = true;
		report_info(currentMethod.getName()+" definisana", null);
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
		formParsCount = 0;
	}
	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    	currentType = type.struct;
	}
	
	public void visit(IdentRetType identType) {
		Obj typeNode = Tab.find(identType.getRetType());
		if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + identType.getRetType() + " u tabeli simbola! ", null);
    		identType.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			identType.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + identType.getRetType() + " ne predstavlja tip!", identType);
    			identType.struct = Tab.noType;
    		}
    	}
    	currentType = identType.struct;
	}
	
	public void visit(VoidRetType voidType) {
		voidType.struct = Tab.noType;
	}
	
	public void visit(DesignatorIdent designator) {
		Obj obj = Tab.find(designator.getDesName());
		if(obj == Tab.noObj) {
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getDesName()+" nije deklarisano! ", null);
		}
		designator.obj = obj;
	}
	
	public void visit(NoArray varDecl) {
		varDeclCount++;
		report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
		Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), currentType);
	}

	public void visit(Array varDecl) {
		varDeclCount++;
		report_info("Deklarisan niz "+ varDecl.getArrayName(), varDecl);
		Struct arrayStruct = new Struct(Struct.Array, currentType);
		Obj varNode = Tab.insert(Obj.Var, varDecl.getArrayName(), arrayStruct);
	}
	
	public void visit(NoArrayLast varDecl) {
		varDeclCount++;
		report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
		Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), currentType);
	}

	public void visit(ArrayLast varDecl) {
		varDeclCount++;
		report_info("Deklarisan niz "+ varDecl.getArrayName(), varDecl);
		Struct arrayStruct = new Struct(Struct.Array, currentType);
		Obj varNode = Tab.insert(Obj.Var, varDecl.getArrayName(), arrayStruct);
	}
	
	public void visit(NumConst numConst) {
		if(checkMultipleDeclaration(numConst.getConstName(), numConst)) return;
		if(currentType != Tab.intType) {report_error("'const' tip podatka nije int", numConst); return;}
		Obj constNode = Tab.insert(Obj.Con, numConst.getConstName(), Tab.intType);
		report_info("Kreirana je konstanta int" + " " + numConst.getConstName() + " = " + numConst.getNumConst(), numConst);
		constNode.setAdr(numConst.getNumConst());
	}
	
	public void visit(CharConst charConst) {
		if(checkMultipleDeclaration(charConst.getConstName(), charConst)) return;
		if(currentType != Tab.charType){report_error("'const' tip podatka nije char", charConst); return;}
		Obj constNode = Tab.insert(Obj.Con, charConst.getConstName(), Tab.charType);
		report_info("Kreirana je konstanta char" + " " + charConst.getConstName() + " = " + charConst.getCharConst(), charConst);
		constNode.setAdr(charConst.getCharConst());
	}
	
	public void visit(BoolConst boolConst) {
		if(checkMultipleDeclaration(boolConst.getConstName(), boolConst)) return;
		int constValue = boolConst.getBoolConst() == true ? 1 : 0;
		Obj constNode = Tab.insert(Obj.Con, boolConst.getConstName(), boolType);
		report_info("Kreirana je konstanta bool" + " " + boolConst.getConstName() + " = " + boolConst.getBoolConst(), boolConst);
		constNode.setAdr(constValue);
	}
	
	public void visit(LastFormParamNoArray formParam) {
		formParsCount++;
		Obj formParamNode = Tab.find(formParam.getParamName());
		if(formParamNode != Tab.noObj) {
			if (Tab.currentScope.findSymbol(formParam.getParamName()) != null){
				report_error("Ime formalnog parametra " + formParam.getParamName() + " je vec deklarisano u listi formalnih parametara!", formParam);    		
				return;
			}
		}	
		Tab.insert(Obj.Var, formParam.getParamName(), currentType);
		report_info("Funkcija prima formalni parametar " + formParam.getParamName(), formParam);
	}
	
	public void visit(MFormParamNoArray formParam) {
		formParsCount++;
		Obj formParamNode = Tab.find(formParam.getParamName());
		if(formParamNode != Tab.noObj) {
			if (Tab.currentScope.findSymbol(formParam.getParamName()) != null){
				report_error("Ime formalnog parametra " + formParam.getParamName() + " je vec deklarisano u listi formalnih parametara!", formParam);    		
				return;
			}
		}	
		Tab.insert(Obj.Var, formParam.getParamName(), currentType);
		report_info("Funkcija prima formalni parametar " + formParam.getParamName(), formParam);
	}
	
	public void visit(LastFormParamArray formParam) {
		formParsCount++;
		Obj formParamNode = Tab.find(formParam.getParamName());
		if(formParamNode != Tab.noObj) {
			if (Tab.currentScope.findSymbol(formParam.getParamName()) != null){
				report_error("Ime formalnog parametra " + formParam.getParamName() + " je vec deklarisano u listi formalnih parametara!", formParam);    		
				return;
			}
		}	
		Struct formParamType = new Struct(Struct.Array, currentType);
		Tab.insert(Obj.Var, formParam.getParamName(), formParamType);
		report_info("Funkcija prima formalni parametar niz " + formParam.getParamName(), formParam);
	}
	
	public void visit(MFormParamArray formParam) {
		formParsCount++;
		Obj formParamNode = Tab.find(formParam.getParamName());
		if(formParamNode != Tab.noObj) {
			if (Tab.currentScope.findSymbol(formParam.getParamName()) != null){
				report_error("Ime formalnog parametra " + formParam.getParamName() + " je vec deklarisano u listi formalnih parametara!", formParam);    		
				return;
			}
		}	
		Struct formParamType = new Struct(Struct.Array, currentType);
		Tab.insert(Obj.Var, formParam.getParamName(), formParamType);
		report_info("Funkcija prima formalni parametar niz " + formParam.getParamName(), formParam);
	}
	
	// statements
	
	public void visit(BreakStatement breakStatement) {
		if(blockDepth == 0) {
			report_error("Naredba 'break' mora biti unutar 'while' ili 'foreach' petlje!", breakStatement);        	
			return;
		}
	}
	
	public void visit(ContinueStatement continueStatement) {
		if(blockDepth == 0) {
			report_error("Naredba 'continue' mora biti unutar 'while' ili 'foreach' petlje!", continueStatement);        	
			return;
		}
	}
	
	public void visit(PrintStatement printStatement) {
		Struct exprType = printStatement.getExpr().struct;
		if(exprType != Tab.intType && exprType != Tab.charType && exprType != boolType) {
    		report_error("'Expr' kod 'print' funkcije mora biti tipa int, char ili bool", printStatement);        	
			return;
    	}
	}
	
	public void visit(PrintNumberStatement printStatement) {
		Struct exprType = printStatement.getExpr().struct;
		if(exprType != Tab.intType && exprType != Tab.charType && exprType != boolType) {
    		report_error("'Expr' kod 'print' funkcije mora biti tipa int, char ili bool", printStatement);        	
			return;
    	}
	}
	
	public void visit(WhileStart whileStart) {
		blockDepth++;
	}
	

	public void visit(ReturnExprStatement returnStatement) {
		if(currentMethod == null) {
			report_error("'return' naredba ne sme postojati izvan tela metoda i globalnih funkcija", returnStatement);        	
			return;
		}
		Struct returnType = returnStatement.getExpr().struct;
		Struct currentMethodType = currentMethod.getType();
		if(currentMethodType == Tab.noType) {
			report_error("'void' funkcije ne smeju vracati izraz u return naredbi", returnStatement);        	
			return;
		}
		if(!currentMethodType.equals(returnType)) {
			report_error("Tip izraza u 'return' naredbi"+returnType.getKind()+" nije ekvivalentan sa tipom povratne vrednosti funkcije.", returnStatement);        	
			return;
		}
	}
	
	public void visit(ReturnVoidStatement returnStatement) {
		if(currentMethod == null) {
			report_error("'return' naredba ne sme postojati izvan tela metoda i globalnih funkcija", returnStatement);        	
			return;
		}
		Struct currentMethodType = currentMethod.getType();
		if(currentMethodType != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName()+" ne vraca izraz u return naredbi", returnStatement);        	
			return;
		}
	}
	
	// factor
	
	public void visit(NumFactor numFactor) {
		numFactor.struct = Tab.intType;
	}
	
	public void visit(CharFactor charFactor) {
		charFactor.struct = Tab.charType;
	}
	
	public void visit(ExprFactor exprFactor) {
		exprFactor.struct = exprFactor.getExpr().struct;
	}
	
	public void visit(BoolFactor boolFactor) {
		boolFactor.struct = boolType;
	}
	
	// term
	
	 public void visit(NoMulopTerm singleTerm) {
	    singleTerm.struct = singleTerm.getFactor().struct;
	 }
	 
	 public void visit(MulopTerm mulopTerm) {
		 if(mulopTerm.getFactor().struct != Tab.intType || mulopTerm.getTerm().struct != Tab.intType) {
			report_error("Term i factor moraju biti tipa int!", mulopTerm);        	
			mulopTerm.struct = Tab.noType;
	    	return;
		 }
		 mulopTerm.struct = Tab.intType;
	 }
		 
	public void visit(AddopExprList addopExpr) {
		 Struct termStruct = addopExpr.getTerm().struct; 
		 Struct exprStruct = addopExpr.getExpr().struct;
		 if(termStruct != Tab.intType || exprStruct != Tab.intType) {
			report_error("Expr i term moraju biti tipa int!", addopExpr);        	
			addopExpr.struct = Tab.noType;
		    return;
		 }
		 else if(!termStruct.compatibleWith(exprStruct)) {
			report_error("Expr i term moraju biti kompatibilni!", addopExpr);        	
			addopExpr.struct = Tab.noType;
			return;
		 }
		 addopExpr.struct = Tab.intType;
	 }
	 
	 public void visit(NoMinExpr noMinExpr) {
		 noMinExpr.struct = noMinExpr.getTerm().struct;
	 }
	 
	 public void visit(MinExpr minExpr) {
		 if(minExpr.getTerm().struct != Tab.intType) {
			report_error("Tip izraza sa '-' mora biti int!", minExpr);        	
			minExpr.struct = Tab.noType;
			return;
		 }
		 minExpr.struct = Tab.intType;
	 }
	 
	 public void visit(AssignOption assignDesignatorStatement) {
		 Obj designator = assignDesignatorStatement.getDesignator().obj;
		 int kind = designator.getKind();
		 if(kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error("Leva strana jednakosti mora biti promenljiva, "
					+ "element niza ili polje objekta", assignDesignatorStatement); 
			return;
		 }
		 Struct source = assignDesignatorStatement.getAssignDesignatorOption().struct;
		 Struct destination = designator.getType();
		 if(!source.assignableTo(destination)) {
			 report_error("Desna" + source.getKind()+" i leva" +destination.getKind() +" strana nisu kompatibilne pri dodeli vrednosti", assignDesignatorStatement);
			 return;
		 }		 
	 }
	 
	 public void visit(AssignDesignator assignOption) {
		 assignOption.struct = assignOption.getExpr().struct;
	 }
	 
	 public void visit(IncOption incDesignatorStatement) {
		 Obj designator = incDesignatorStatement.getDesignator().obj;
		 int kind = designator.getKind();
		 if(kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error("Operand mora biti promenljiva, "
					+ "element niza ili polje objekta", incDesignatorStatement); 
			return;
		 }
		 if(designator.getType() != Tab.intType) {
			 report_error("Operand kod inkrementiranja mora biti tipa int", incDesignatorStatement);
		 }
	 }
	 
	 public void visit(DecOption decDesignatorStatement) {
		 Obj designator = decDesignatorStatement.getDesignator().obj;
		 int kind = designator.getKind();
		 if(kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error("Operand mora biti promenljiva, "
					+ "element niza ili polje objekta", decDesignatorStatement); 
			return;
		 }
		 if(designator.getType() != Tab.intType) {
			 report_error("Operand kod dekrementiranja mora biti tipa int", decDesignatorStatement);
		 }
	 }
	 
	 public void visit(DesignatorArrayIndex arrayList) {
		 Obj designator = arrayList.getDesignator().obj;
		 int kind = designator.getKind();
		 if(kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error("Svaki operand na levoj strani mora biti promenljiva, "
					+ "element niza ili polje objekta", arrayList); 
			return;
		 }
	 }
	 
	 public void visit(DesignatorStatementList multipleAssignOperation) {
		 Obj designatorSrc = multipleAssignOperation.getDesignator().obj;
		 if(designatorSrc.getType().getKind() != Struct.Array) {
			 report_error("Src operand kod viseznacne dodele vrednosti mora biti array.", multipleAssignOperation);
			 return;
		 }
	 }
	 

	 
	 public void visit(ReadStatement readStatement) {
		 Obj designator = readStatement.getDesignator().obj;
		 int kind = designator.getKind();
		 Struct desType = designator.getType();
		 if(kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error("Parametar 'read' funkcije mora biti promenljiva, "
					+ "element niza ili polje objekta", readStatement); 
			return;
		 }
		 if(desType != Tab.intType && desType != Tab.charType && desType != boolType) {
			 report_error("Parametar 'read' funkcije mora biti tipa "
						+ "int, char ili bool.", readStatement); 
		 }
	 }
	 
	 public void visit(IfStatement ifStatement) {
		 Struct conditionKind = ifStatement.getCondition().struct;
		 if(conditionKind != boolType) { 
			 report_error("Uslov 'if' naredbe mora biti tipa Bool", ifStatement);
			 return;
		 }
	 }
	 
	 public void visit(IfElseStatement ifElseStatement) {
		 Struct conditionKind = ifElseStatement.getCondition().struct;
		 if(conditionKind != boolType) { 
			 report_error("Uslov 'if' naredbe mora biti tipa Bool", ifElseStatement);
			 return;
		 }
	 }
	 
	public void visit(WhileStatement whileStatement) {
		 blockDepth--;
		 Struct conditionKind = whileStatement.getCondition().struct;
		 if(conditionKind != boolType) {
			 report_error("Uslov 'if' naredbe mora biti tipa Bool", whileStatement);
			 return;
		 }
	}
	
	public void visit(ForeachStatement foreachStatement) {
		blockDepth--;
		Obj designatorSrc = foreachStatement.getForeachArray().getDesignator().obj;
		if(designatorSrc.getType().getKind() != Struct.Array) {
			 report_error("Foreach pozivalac mora biti array.", foreachStatement);
			 return;
		}
	/*	if(foreachStatement.getForeachStart().struct != designatorSrc.getType().getElemType()) {
			report_error("Promenljiva i niz 'foreach' naredbe nisu istog tipa.", foreachStatement);
			return;
		}*/
	}
	
	public void visit(ForeachStart foreachStart) {
		blockDepth++;
		Obj elem = Tab.find(foreachStart.getElem());
		if(elem == Tab.noObj) {
			report_error("Greska na liniji " + foreachStart.getLine()+ " : ime "+ foreachStart.getElem()+" nije deklarisano! ", foreachStart);
			foreachStart.obj = Tab.noObj;
			return;
		}
		foreachStart.obj = elem;
	}
	
		
	public void visit(FuncCallStart funcCallStart) {
		actParsStack.push(new ArrayList<Struct>());
	}
	
    public void visit(ActualParam actParam) {
    	actParsStack.peek().add(actParam.getExpr().struct);
    }
    
    public void visit(ActualParams actParams) {
    	report_info("dodajem act", null);
    	actParsStack.peek().add(actParams.getExpr().struct);
    }
    
	private List<Obj> getFormPars(Obj myFunction, int actParsCnt) {	    	
	    List<Obj> formPars = new ArrayList<Obj>(); 
	    int formParsCnt = 0;
	    Collection<Obj> localSymbols = myFunction.getLocalSymbols();
	    for(Obj formParam: localSymbols) {
	    	if(formParsCnt == myFunction.getLevel()) break;
	    	formPars.add(formParam);
	    	formParsCnt++;
	    }  	
	    if(actParsCnt != formParsCnt) {	    	
	    	report_error("Broj stvarnih(" +actParsCnt+") i formalnih(" +formParsCnt+")parametara mora biti isti", null);        	    		    	
	    	return null;
	    }
	    return formPars;
	}
    
	public void visit(DesignatorFuncCall funcCall) {
		Obj func = funcCall.getDesignator().obj;
		if(Obj.Meth == func.getKind()) {
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
		} else {
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
		}
		List<Struct> actPars = actParsStack.pop();
    	int actParsCnt = actPars.size();
    	List<Obj> formPars = getFormPars(func, actParsCnt);
    	if(formPars != null) {
    		for(int i = 0;i < actParsCnt; i++) {
    			if(!actPars.get(i).assignableTo(formPars.get(i).getType())) {
    				report_error("Formalnom argumentu na poziciji " + (i + 1) + 
    						" se ne moze dodeliti stvarni argument na toj poziciji", null);
    				return;
    			}
    		}
    	}
	}
	
	public void visit(DesignatorVar designatorVar) {
		designatorVar.struct = designatorVar.getDesignator().obj.getType();
	}
	
	public void visit(RelopFact relopFact) {
		Struct leftType = relopFact.getExpr().struct;
		Struct rightType = relopFact.getExpr().struct;
		if(!leftType.compatibleWith(rightType)) {
			report_error("Izrazi u uslovu za poredjenje nisu kompatibilni (ne mogu se porediti).", null);
			return;
		}
		else if(leftType.getKind() == Struct.Array || rightType.getKind() == Struct.Array ||
				leftType.getKind() == Struct.Class || rightType.getKind() == Struct.Class) {
			if(!currentRelop.equals(RelopEnum.EQ) || !currentRelop.equals(RelopEnum.NEQ)) {
				report_error("Operacija poredjenja izmedju nizova ili klasa moze biti samo '==' ili '!='", null);
			}
		}
		relopFact.struct = boolType;
	}
	
	public void visit(Relopeq eq) {
		currentRelop = RelopEnum.EQ;
	}
	
	public void visit(Relopneq neq) {
		currentRelop = RelopEnum.NEQ;
	}
	
	public void visit(Relopgt gt) {
		currentRelop = RelopEnum.GT;
	}
	
	public void visit(Relopge ge) {
		currentRelop = RelopEnum.GE;
	}
	
	public void visit(Reloplt lt) {
		currentRelop = RelopEnum.LT;
	}
	
	public void visit(Relople le) {
		currentRelop = RelopEnum.LE;
	}
	
	public void visit(NewArrayFactor newFactor) {
		if(newFactor.getExpr().struct != Tab.intType) {
			report_error("Izraz prilikom koriscenja 'New' operatora mora biti tipa int.", null);
			newFactor.struct = Tab.noType;
			return;
		}
		newFactor.struct = new Struct(Struct.Array, newFactor.getType().struct);
	}
	
	public void visit(DesignatorExpr designatorExpr) {
		Struct exprStruct = designatorExpr.getExpr().struct;
		if(exprStruct != Tab.intType) {
			report_error("Los pristup elementu niza, izraz u zagradi nije int.", null);
			designatorExpr.struct = Tab.noType;
			return;
		}
		designatorExpr.struct = Tab.intType;
	}
	
	public void visit(DesignatorTerms designatorTerms) {
		Obj designator = designatorTerms.getDesignatorArray().obj;
		if(designator.getType().getKind() != Struct.Array) {
			report_error("Pokusaj pristupa elementu niza, promenljiva nije niz.", null);
			designatorTerms.obj = Tab.noObj;
        	return;
		}
		else if(designatorTerms.getDesignatorExpr().struct != Tab.intType) {
    		report_error("Indeks za pristup elementu niza mora biti tipa int.", designatorTerms);        	
    		designatorTerms.obj = Tab.noObj;
        	return;
    	}
		designatorTerms.obj = new Obj(Obj.Elem, designator.getName(), designator.getType().getElemType());
	}
	
	public void visit(DesignatorArray desArray) {
		desArray.obj = desArray.getDesignator().obj;
	}
	
	public void visit(Fact fact) {
		fact.struct = fact.getExpr().struct;
	}
	
	public void visit(SingleCondTerm condTerm) {
		condTerm.struct = condTerm.getCondFact().struct;
	}
	
	public void visit(CondTerms condTerms) {
		if(condTerms.getCondTerm().struct == boolType && condTerms.getCondFact().struct == boolType) {
			condTerms.struct = boolType;
		} else condTerms.struct = Tab.noType;
	}
	
	public void visit(SingleCondition singleCondition) {
		singleCondition.struct = singleCondition.getCondTerm().struct;
	}
	
	public void visit(Conditions conditions) {
		if(conditions.getCondition().struct == boolType && conditions.getCondTerm().struct == boolType) {
			conditions.struct = boolType;
		} else conditions.struct = Tab.noType;
	}
	
	public boolean passed() {
    	return !errorDetected;
    }
	  
}
