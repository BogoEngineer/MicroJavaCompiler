package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.SymbolTable;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import org.apache.log4j.Logger;


public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	Logger log = Logger.getLogger(getClass());
	
	public void visit(PrintStmt printStmt){
		Obj exprObj = printStmt.getExpr().obj;
		if(printStmt.getExpr().obj.getType() == SymbolTable.intType){
			Code.loadConst(5);
			Code.put(Code.print);
		}else if(printStmt.getExpr().obj.getType() == SymbolTable.charType){
			Code.loadConst(1);
			Code.put(Code.bprint);
		}else {
			Code.loadConst(1);
			Code.put(Code.print);
		}
	}
	
	public void visit(MethodTypeName methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCnt.getCount());
	}
	
	public void visit(NumConst nc) {
		Obj con = SymbolTable.insert(Obj.Con, "$", SymbolTable.intType);
		con.setLevel(0);
		con.setAdr(nc.getNum());
		
		Code.load(con);
	}
	
	public void visit(CharConst nc) {
		Obj con = SymbolTable.insert(Obj.Con, "$", SymbolTable.intType);
		con.setLevel(0);
		con.setAdr(nc.getChr());
		
		Code.load(con);
	}
	
	public void visit(BoolConst nc) {
		Obj con = SymbolTable.insert(Obj.Con, "$", SymbolTable.intType);
		con.setLevel(0);
		con.setAdr(nc.getBl().equals("true") ? 1 : 0);
		
		Code.load(con);
	}
	
	public void visit(MethodDeclVars methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(MethodDeclNoVars methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorIndex designatorIndex) {
		SyntaxNode parent = designatorIndex.getParent();
		
		if(Expr.class == parent.getClass()){
			Code.load(SymbolTable.find(designatorIndex.getName()));
		}
	}
	
	public void visit(SingleDesignator singleDesignator) {
		SyntaxNode parent = singleDesignator.getParent();
		
		if(Expr.class == parent.getClass()){
			Code.load(SymbolTable.find(singleDesignator.getName()));
		}
	}
	
	
}
