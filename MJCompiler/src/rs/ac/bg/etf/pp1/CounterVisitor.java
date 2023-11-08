package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.Array;
import rs.ac.bg.etf.pp1.ast.ArrayLast;
import rs.ac.bg.etf.pp1.ast.ArrayParam;
import rs.ac.bg.etf.pp1.ast.FormalParamDecl;
import rs.ac.bg.etf.pp1.ast.LastFormParamArray;
import rs.ac.bg.etf.pp1.ast.LastFormParamNoArray;
import rs.ac.bg.etf.pp1.ast.MFormParamArray;
import rs.ac.bg.etf.pp1.ast.MFormParamNoArray;
import rs.ac.bg.etf.pp1.ast.NoArray;
import rs.ac.bg.etf.pp1.ast.NoArrayLast;
import rs.ac.bg.etf.pp1.ast.NoArrayParam;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount(){
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
	
		public void visit(LastFormParamArray formParamDecl){
			count++;
		}
		
		public void visit(LastFormParamNoArray formParamDecl){
			count++;
		}
		
		public void visit(MFormParamArray formParamDecl){
			count++;
		}
		
		public void visit(MFormParamNoArray formParamDecl){
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(NoArray varDecl){
			count++;
		}
		
		public void visit(NoArrayLast varDecl){
			count++;
		}
		
		public void visit(Array varDecl){
			count++;
		}
		
		public void visit(ArrayLast varDecl){
			count++;
		}
	}
}
