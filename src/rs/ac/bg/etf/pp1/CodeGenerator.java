package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.SymbolTable;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import org.apache.log4j.Logger;
import java.util.*;


public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	private Obj programObj;
	
	LinkedList<String> postfixExpr = new LinkedList<String>(); // koriscen kao struktura koja cuva ceo postfix izraz nakon prepoznavanja Expr
	Stack<String> operationStack = new Stack<String>(); // stek za operatore (prioriteti)
	// kodovi za operationStack
	
	int byteCodeFix = 0; // poslednja/e linije u bytecode koje treba ispraviti
	
	private final static String 
		ADD = "+",
		SUB = "-",
		MUL = "*",
		DIV = "/",
		MOD = "%",
		LPAREN = "(",
		RPAREN = ")",
		LBRACKET = "[",
		RBRACKET = "]",
		COLON = ":",
		QMARK = "?"
	;
	
	Logger log = Logger.getLogger(getClass());
	
	private void postfixExprToExprStack() {
		// stavlja postfixExpr na ExprStack pozivanjem Code funkcija
		if(postfixExpr.isEmpty()) return;
		for(String postfixElem : postfixExpr) {
			switch(postfixElem) { // Java compiler is using .equals
			case ADD:
				Code.put(Code.add);
				break;
			case SUB:
				Code.put(Code.sub);
				break;
			case MUL:
				Code.put(Code.mul);
				break;
			case DIV:
				Code.put(Code.div);
				break;
			case MOD:
				Code.put(Code.rem); // valjda je rem za moduo?
				break;
			case LBRACKET: // dont do anything
				break;
			case RBRACKET: // dont do anything
				Code.put(Code.aload);
				break;
			case COLON:
				Code.put(Code.jmp); // bezuslovni skok da se preskoci izracunavanje drugog izraza
				Code.put2(0);
				Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
				Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
				byteCodeFix = Code.pc-2;
				break;
			case QMARK:
				Code.loadConst(1);
				Code.put(Code.jcc + Code.ne);
				byteCodeFix = Code.pc;
				Code.put2(0); // skok prima adresu od 16b koju treba zakrpiti 
				break;
			default:
				if(postfixElem.chars().allMatch( Character::isDigit )) { 
					// this element in postfix expr is numerical constant
					Obj con = SymbolTable.insert(Obj.Con, "$", SymbolTable.intType);
					con.setLevel(0);
					con.setAdr(Integer.parseInt(postfixElem)); // set value of constant
					Code.load(con);
					break;
				}
				// this element in postfix expr is a variable 
				Obj obj = SymbolTable.findInProgram(programObj, postfixElem);
				Code.load(obj);
			}
		}
		// kad si premestio na Expr Stack, ocisti ove strukture
		postfixExpr.clear();
		operationStack.clear();
	}
	
	public void visit(ProgName progName) {
		programObj = SymbolTable.find(progName.getProgName());
	}
	
	public void visit(PrintStmt printStmt){
		Obj exprObj = printStmt.getExpr().obj;
		if(exprObj.getType() == SymbolTable.intType){
			while(!operationStack.isEmpty()) {
				postfixExpr.add(operationStack.pop());
			}
			/* System.out.println("print arg: ");
			for(String postfixElem : postfixExpr) {
				System.out.println(postfixElem + " ");
			} */
			postfixExprToExprStack();
			Code.loadConst(5);
			Code.put(Code.print);
		}else if(exprObj.getType() == SymbolTable.charType){
			/* System.out.println("print arg: ");
			for(String postfixElem : postfixExpr) {
				System.out.println(postfixElem + " ");
			} */
			postfixExprToExprStack(); // u slucaju da char dolazi iz promenljive (koji potice iz expra - koji ide na stek)
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
		postfixExpr.add(Integer.toString(nc.getNum()));
		/* Obj con = SymbolTable.insert(Obj.Con, "$", SymbolTable.intType);
		con.setLevel(0);
		con.setAdr(nc.getNum());
		
		Code.load(con); */
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
		
		if(AssignmentExpr.class != parent.getClass()){ // ako je ovaj designator na levoj strani operacije ++ ili --
			//Code.load(SymbolTable.find(designatorIndex.getDesignatorName().getName())); // ne valja
			if(parent.getClass() == Var.class) { // ako je ovaj designator na desnoj strani operacije dodele vrednosti
				// pop till left bracket
				while(operationStack.peek() != LBRACKET) {
					postfixExpr.add(operationStack.pop());
				}
				operationStack.pop(); // pop left bracket
				// ovo moze pripadati nekom vecem expr pa ne treba stavljati odmah sve na Expr Stack
				postfixExpr.add(RBRACKET); 
				// dodaje se da bi se znalo da se na RBRACKET u generisanom kodu smesti aload instrukcija kako bi se dohvatio element niza na poziciji
				// koja je ukazana postfix expr-om izmedju LBRACKETa i RBRACKETa
			}
		}else {
			Obj array = SymbolTable.findInProgram(programObj, designatorIndex.getDesignatorName().getName());
			Code.load(array); // stavi na stek koja promenljiva
			postfixExprToExprStack(); // stavi na stek u koji element niza treba da se ucita nesto
		}
	}
	
	public void visit(SingleDesignator singleDesignator) {
		SyntaxNode parent = singleDesignator.getParent();
		
		/* if(AssignmentExpr.class != parent.getClass()){ // ako je ovaj designator na levoj strani operacije ++ ili --
			Code.load(SymbolTable.find(singleDesignator.getDesignatorName().getName()));
		} */
		
		// dont add variable into postfix expr if its on the left side of a = operator
		if(AssignmentExpr.class != parent.getClass()) postfixExpr.add(singleDesignator.getDesignatorName().getName()); 

	}
	
	public void visit(Addop addop) {
		if(operationStack.isEmpty()) operationStack.push(ADD);
		else {
			while(true) {
				String top = operationStack.peek();
				if(top.equals(LPAREN) || top.equals(RPAREN) || top.equals(LBRACKET) || top.equals(RBRACKET)) { // lower precedence
					break;
				}
				postfixExpr.add(operationStack.pop());
				if(operationStack.isEmpty()) break;
			}
			operationStack.push(ADD);
		}
	}
	
	public void visit(Subtractop subop) {
		if(operationStack.isEmpty()) operationStack.push(SUB);
		else {
			while(true) {
				String top = operationStack.peek();
				if(top.equals(LPAREN) || top.equals(RPAREN) || top.equals(LBRACKET) || top.equals(RBRACKET)) { // lower precedence
					break;
				}
				postfixExpr.add(operationStack.pop());
				if(operationStack.isEmpty()) break;
			}
			operationStack.push(SUB);
		}
	}
	
	public void visit(Mulop mulop) {
		if(operationStack.isEmpty()) operationStack.push(MUL);
		else {
			while(true) {
				String top = operationStack.peek();
				if(top.equals(LPAREN) || top.equals(RPAREN) || top.equals(LBRACKET) || top.equals(RBRACKET)
						|| top.equals(ADD) || top.equals(SUB)) { // lower precedence
					break;
				}
				postfixExpr.add(operationStack.pop());
				if(operationStack.isEmpty()) break;
			}
			operationStack.push(MUL);
		}
	}
	
	public void visit(Divideop divop) {
		if(operationStack.isEmpty()) operationStack.push(DIV);
		else {
			while(true) {
				String top = operationStack.peek();
				if(top.equals(LPAREN) || top.equals(RPAREN) || top.equals(LBRACKET) || top.equals(RBRACKET)
						|| top.equals(ADD) || top.equals(SUB)) { // lower precedence
					break;
				}
				postfixExpr.add(operationStack.pop());
				if(operationStack.isEmpty()) break;
			}
			operationStack.push(DIV);
		}
	}
	
	public void visit(Modop modop) {
		if(operationStack.isEmpty()) operationStack.push(MOD);
		else {
			while(true) {
				String top = operationStack.peek();
				if(top.equals(LPAREN) || top.equals(RPAREN) || top.equals(LBRACKET) || top.equals(RBRACKET)
						|| top.equals(ADD) || top.equals(SUB)) { // lower precedence
					break;
				}
				postfixExpr.add(operationStack.pop());
				if(operationStack.isEmpty()) break;
			}
			operationStack.push(MOD);
		}
	}

	
	public void visit(DesignatorName designatorName) {
		SyntaxNode parentNode = designatorName.getParent();
		if(DesignatorIndex.class == parentNode.getClass() && Var.class == parentNode.getParent().getClass()) {
		// dodaje se [ na stek samo ako se koristi var[expr] na desnoj strani izraza za dodelu vrednosti
			operationStack.push(LBRACKET);
			postfixExpr.add(designatorName.getName());
			postfixExpr.add(LBRACKET);
		}
	}
	
	public void visit(AssignmentExpr assExpr) {
		while(!operationStack.isEmpty()) {
			postfixExpr.add(operationStack.pop());
		}
		/* System.out.println("POSTFIX IZRAZ NAKON OPERACIJE DODELE VREDNOSTI: ");
		for(String postfixElem : postfixExpr) {
			System.out.println(postfixElem + " ");
		} */
		/* do something */
		postfixExprToExprStack(); // na stek ide desna strana izraza jednakosti, ako je bio izraz new int[2] onda na steku nece biti nista pa se ne desava nista
		Designator leftSideOperand = assExpr.getDesignator();
		if(leftSideOperand instanceof SingleDesignator) { // obicna promenljivaq
			Code.store(SymbolTable.findInProgram(programObj, ((SingleDesignator)leftSideOperand).getDesignatorName().getName()));
		}else { // dodeljivanje elementu niza
			// postfixExprToExprStack(); // na stek ide desna strana izraza za dodelu
			Code.put(Code.astore); // ucitavanje desne strane izraza u levu koja je ucitana u DesignatorIndexu
		}
	}
	
	public void visit(ParenExpr parenExpr) { // pop till right parenthesis
		while(operationStack.peek() != LPAREN) {
			postfixExpr.add(operationStack.pop());
		}
		operationStack.pop(); // pop left parenthesis
	}
	
	public void visit(LeftParen leftParen) {
		if(ParenExpr.class == leftParen.getParent().getClass()) { // mora biti al za svaki slucaj
			operationStack.push(LPAREN);
		}
	}
	
	public void visit(NewFactorArray newFactorArray) { // generisanje koda za alokaciju memorije na heapu za niz
		postfixExprToExprStack();
		Code.put(Code.newarray);
		if (newFactorArray.getType().obj.getType() == SymbolTable.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	public void visit(Increment inc) {
		Designator des = inc.getDesignator();
		if(des instanceof SingleDesignator) { // nije radio Code.inc iz nekog razloga za lokalnu prom
			Obj desObj = SymbolTable.findInProgram(programObj, ((SingleDesignator)des).getDesignatorName().getName());
			Code.load(desObj); // uvek mora da se stavi var svakako
			Code.loadConst(1); // mozda treba da se uradi nesto sa constObj
			Code.put(Code.add);
			Code.store(desObj);
		}else {
			Obj desObj = SymbolTable.findInProgram(programObj, ((DesignatorIndex)des).getDesignatorName().getName());
			Code.load(desObj); // uvek mora da se stavi var svakako
			postfixExprToExprStack();
			Code.put(Code.dup2);
			Code.put(Code.aload); // ucitaj element niza zadat sa dup2
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}
	
	public void visit(Decrement dec) {
		Designator des = dec.getDesignator();
		if(des instanceof SingleDesignator) { // nije radio Code.inc iz nekog razloga za lokalnu prom
			Obj desObj = SymbolTable.findInProgram(programObj, ((SingleDesignator)des).getDesignatorName().getName());
			Code.load(desObj); // uvek mora da se stavi var svakako
			Code.loadConst(1); // mozda treba da se uradi nesto sa constObj
			Code.put(Code.sub);
			Code.store(desObj);
		}else {
			Obj desObj = SymbolTable.findInProgram(programObj, ((DesignatorIndex)des).getDesignatorName().getName());
			Code.load(desObj); // uvek mora da se stavi var svakako
			postfixExprToExprStack();
			Code.put(Code.dup2);
			Code.put(Code.aload); // ucitaj element niza zadat sa dup2
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.put(Code.astore);
		}
	}
	
	public void visit(TernaryExpr terExpr) {
		while(!operationStack.isEmpty()) { // mora zbog poslednjeg Expr1
			postfixExpr.add(operationStack.pop());
		}
		for(String postfixElem : postfixExpr) {
			System.out.println(postfixElem + " ");
		}
		postfixExprToExprStack();
		System.out.println("FIX: "+ byteCodeFix + " " + Code.pc);
		Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
		Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
	}
	
	public void visit(Qmark qmark) {
		while(!operationStack.isEmpty()) {
			postfixExpr.add(operationStack.pop());
		}
		postfixExpr.add(QMARK);
	}
	
	public void visit(Colon colon) {
		while(!operationStack.isEmpty()) {
			postfixExpr.add(operationStack.pop());
		}
		postfixExpr.add(COLON);
	}
	
	public void visit(ReadStmt readStmt) {
		Designator des = readStmt.getDesignator();
		if(des instanceof SingleDesignator) {
			Obj obj = SymbolTable.findInProgram(programObj, ((SingleDesignator)des).getDesignatorName().getName());
			Code.put(Code.read);
			Code.store(obj);
		}else {
			Obj obj = SymbolTable.findInProgram(programObj, ((DesignatorIndex)des).getDesignatorName().getName());
			Code.load(obj);
			postfixExprToExprStack();
			Code.put(Code.read);
			Code.put(Code.astore);
		}
	}
}
