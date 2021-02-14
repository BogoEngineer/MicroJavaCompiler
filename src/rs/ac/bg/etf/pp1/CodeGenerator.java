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

/*System.out.println("POSTFIX IZRAZ U ASN: ");
int index = 0;
for(String postfixElem : postfixExpr) {
	System.out.println(index++ + ": " + postfixElem + " ");
}*/

public class CodeGenerator extends VisitorAdaptor {
	private String lastBeen = "";
	
	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	private Obj programObj;
	
	LinkedList<String> postfixExpr = new LinkedList<String>(); // koriscen kao struktura koja cuva ceo postfix izraz nakon prepoznavanja Expr
	Stack<String> operationStack = new Stack<String>(); // stek za operatore (prioriteti)
	
	LinkedList<Integer> negativePositions = new LinkedList<Integer>();
	LinkedList<Integer> addedToIndexes = new LinkedList<Integer>();
	
	LinkedList<Integer> insertNegatePositions = new LinkedList<Integer>(); // za postfixExprToStackExpr()
	
	// kodovi za operationStack
	
	
	int byteCodeFix = 0; // poslednja/e linije u bytecode koje treba ispraviti
	
	int printLen = -1; // ako je -1 znaci da treba default
	
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
		QMARK = "?",
		NEGATE= "-e"
	;
	
	Logger log = Logger.getLogger(getClass());
		
	private boolean pleaseFixMe = false;
	
	private int pomeraj = 1;
	
	private boolean charArray = false;
	
	private void postfixExprToExprStack() {
		int positionCount = 0;
		
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
				if(!charArray) Code.put(Code.aload);
				else Code.put(Code.baload);
				// charArray = false;
				
				if(pleaseFixMe) { // fix za ternarni expr u indeksu
					Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+pomeraj)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
					Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+pomeraj);  // pa ne treba pc + 1
					pomeraj = 1;
					pleaseFixMe = false;
				}
				break;
			case COLON:
				Code.put(Code.jmp); // bezuslovni skok da se preskoci izracunavanje drugog izraza
				Code.put2(0);
				
				// popravljanje adrese za skok nakon izraza pre ?
				Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
				Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
				
				byteCodeFix = Code.pc-2;
				break;
			case QMARK:
				Code.loadConst(0);
				Code.put(Code.jcc + Code.eq);
				byteCodeFix = Code.pc;
				Code.put2(0); // skok prima adresu od 16b koju treba zakrpiti 
				break;
			case LPAREN:
				break;
			case RPAREN:
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
				if(!postfixElem.equals("eol")) {
					Obj obj = SymbolTable.findInProgram(programObj, postfixElem);
					Code.load(obj);
				}else {
					Code.loadConst('\n');
				}
				
			}
			positionCount++;
			for(int i = 0; i < insertNegatePositions.size(); i++) {
				if(insertNegatePositions.get(i) == positionCount) {
					// System.out.println("PositionCount: " + positionCount);
					insertNegatePositions.remove(i);
					Code.put(Code.neg);
				}
			}
		}
		
		// kad si premestio na Expr Stack, ocisti ove strukture
		postfixExpr.clear();
		operationStack.clear();
	}
		
	public void visit(Neg neg) {		
		negativePositions.add(postfixExpr.size());
	}
	
	private Integer checkIfArrayIndex(Integer position) {
		// returns input position if its not array index, or position of last ] if it is	
		int brackets = 0;
		for(int i = position; i < postfixExpr.size(); i++) {
			String op = postfixExpr.get(i);
			if(op.equals(ADD) || op.equals(SUB) || op.equals(MUL) || op.equals(DIV) || op.equals(MOD)) break;
			if(op.equals(LBRACKET)) {
				brackets++;
			}
			if(op.equals(RBRACKET)) {
				brackets--;
				if(brackets == 0) return i+1;
			}
		}
		
		if(brackets == 1) return postfixExpr.size(); // hardcode
		
		return position;		
	}
	
	public void visit(NegExpr1 neg) {
		/*System.out.println("POSTFIX IZRAZ U NEGEXPR: ");
		for(String postfixElem : postfixExpr) {
			System.out.println(postfixElem + " ");
		}*/
				
		Integer nextNegative = negativePositions.pollLast();
				
		boolean waitEqual = postfixExpr.get(nextNegative).equals(LPAREN);
		
		if(!waitEqual) {
			Integer position = checkIfArrayIndex(nextNegative);
			
			if(position == nextNegative) insertNegatePositions.add(position+1);
			else insertNegatePositions.add(position);
			//insertNegatePositions.add(position+1);
			return;
		}
		
		int paren = 1;
		int ind = 0;
		
		String pe;
		for(int i = nextNegative+1; paren!=0; i++) {
			pe = postfixExpr.get(i);
			if(pe.equals(LPAREN)) paren++;
			if(pe.equals(RPAREN)) paren--;
			// System.out.println("DEBUG: " + pe + " " + paren);
			ind = i;
		}	
		
		insertNegatePositions.add(ind+1);
	}
	
	public void visit(ProgName progName) {
		programObj = SymbolTable.find(progName.getProgName());
	}
	
	public void visit(PrintStmt printStmt){
		Obj exprObj = printStmt.getExpr().obj;
		
		if(postfixExpr.contains(QMARK)) pomeraj = 0;
		
		if(exprObj.getType() == SymbolTable.intType){
			while(!operationStack.isEmpty()) {
				postfixExpr.add(operationStack.pop());
			}
			/*System.out.println("print arg: ");
			for(String postfixElem : postfixExpr) {
				System.out.println(postfixElem + " ");
			}*/
			postfixExprToExprStack();
			Code.loadConst(printLen == -1 ? 5 : printLen);
			Code.put(Code.print);
		}else if(exprObj.getType() == SymbolTable.charType){
			/* System.out.println("print arg: ");
			for(String postfixElem : postfixExpr) {
				System.out.println(postfixElem + " ");
			} */
			postfixExprToExprStack(); // u slucaju da char dolazi iz promenljive (koji potice iz expra - koji ide na stek)
			Code.loadConst(printLen == -1 ? 1 : printLen);
			Code.put(Code.bprint);
		}else {
			postfixExprToExprStack();
			Code.loadConst(printLen == -1 ? 1 : printLen);
			Code.put(Code.print);
		}
		printLen = -1;
	}
	
	public void visit(PExtension pextension) {
		printLen = pextension.getN1();
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
	}
	
	public void visit(CharConst nc) {
		postfixExpr.add(Integer.toString(nc.getChr()));
	}
	
	public void visit(BoolConst nc) {
		if(nc.getBl().equals("true")) postfixExpr.add(Integer.toString(1));
		else postfixExpr.add(Integer.toString(0));
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
		if(ReadStmt.class == parent.getClass()) return;
		
	
		if(AssignmentExpr.class != parent.getClass()){ // ako je ovaj designator na levoj strani operacije ++ ili --
			if(parent.getClass() == Var.class) { // ako je ovaj designator npr. argument print metode ili unutar indeksera nekog niza
				// pop till left bracket
				
				// System.out.println("Posl: "+ lastBeen);
				/*System.out.println("print arg: ");
				for(String postfixElem : operationStack) {
					System.out.println(postfixElem + " ");
				}*/
				
				if(!operationStack.contains(LBRACKET)) operationStack.add(0, LBRACKET);
				while(operationStack.peek() != LBRACKET) {
					postfixExpr.add(operationStack.pop());
				}
				
				if(postfixExpr.contains(QMARK)) pleaseFixMe = true; // u slucaju da se ternarni izraz nalazi unutar indeksa nekon niza koji je unutar indeksa nekog niza...
				
				operationStack.pop(); // pop left bracket
				// ovo moze pripadati nekom vecem expr pa ne treba stavljati odmah sve na Expr Stack
				postfixExpr.add(RBRACKET);
				// dodaje se da bi se znalo da se na RBRACKET u generisanom kodu smesti aload instrukcija kako bi se dohvatio element niza na poziciji
				// koja je ukazana postfix expr-om izmedju LBRACKETa i RBRACKETa
				
				// postfixExprToExprStack() se za ovo poziva tek u AssignmentExpr visist-u
				
				lastBeen = "DesIndex1";
			}
		}else {
			Obj array = SymbolTable.findInProgram(programObj, designatorIndex.getDesignatorName().getName());
			Code.load(array); // stavi na stek koja promenljiva
			
			// za svaki slucaj
			while(!operationStack.empty()) {
				postfixExpr.add(operationStack.pop());
			}
			
			if(array.getType() == SymbolTable.charArrayType) charArray = true; 
			else charArray = false;
			postfixExprToExprStack(); // stavi na stek u koji element niza treba da se ucita nesto
			
			if(designatorIndex.getExpr().getClass() == TernaryExpr.class) { // fix za ternarni expr u indeksu
				Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
				Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
			}
			lastBeen = "DesIndex2";
		}
		
	}
	
	public void visit(SingleDesignator singleDesignator) {
		SyntaxNode parent = singleDesignator.getParent();
		
		/* if(AssignmentExpr.class != parent.getClass()){ // ako je ovaj designator na levoj strani operacije ++ ili --
			Code.load(SymbolTable.find(singleDesignator.getDesignatorName().getName()));
		} */
		
		// dont add variable into postfix expr if its on the left side of a = operator
		if(AssignmentExpr.class != parent.getClass() && ReadStmt.class != parent.getClass()) postfixExpr.add(singleDesignator.getDesignatorName().getName()); 

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
		/*System.out.println("POSTFIX IZRAZ U ASN: ");
		int index = 0;
		for(String postfixElem : postfixExpr) {
			System.out.println(index++ + ": " + postfixElem + " ");
		}*/
		if(postfixExpr.contains(QMARK)) pomeraj = 0;		
		
		Designator leftSideOperandDesignator = assExpr.getDesignator();

		if(leftSideOperandDesignator instanceof SingleDesignator) { // obicna promenljivaq
			Obj leftSideOperand = SymbolTable.findInProgram(programObj, ((SingleDesignator)leftSideOperandDesignator).getDesignatorName().getName());
			
			postfixExprToExprStack(); // na stek ide desna strana izraza jednakosti, ako je bio izraz new int[2] onda na steku nece biti nista pa se ne desava nista
			Code.store(leftSideOperand);
		}else { // dodeljivanje elementu niza
			Obj leftSideOperand = SymbolTable.findInProgram(programObj, ((DesignatorIndex)leftSideOperandDesignator).getDesignatorName().getName());

			if(leftSideOperand.getType() == SymbolTable.charArrayType) charArray = true;
			else charArray = false;
			postfixExprToExprStack(); // na stek ide desna strana izraza jednakosti, ako je bio izraz new int[2] onda na steku nece biti nista pa se ne desava nista
			
			Code.store(new Obj(Obj.Elem, leftSideOperand.getName(), new Struct(leftSideOperand.getType().getElemType().getKind()))); // Code stavlja bastore ili astore u zavisnosti od toga da li je char ili ne
		}
		lastBeen = "AssExpr";
	}
	
	public void visit(ParenExpr parenExpr) { // pop till right parenthesis
		while(operationStack.peek() != LPAREN) {
			postfixExpr.add(operationStack.pop());
		}
		postfixExpr.add(RPAREN);
		operationStack.pop(); // pop left parenthesis 
		// ne popujem zbog negacije
	}
	
	public void visit(LeftParen leftParen) {
		if(ParenExpr.class == leftParen.getParent().getClass()) { // mora biti al za svaki slucaj
			operationStack.push(LPAREN);
		}
		postfixExpr.add(LPAREN);
	}
	
	public void visit(NewFactorArray newFactorArray) { // generisanje koda za alokaciju memorije na heapu za niz
		postfixExprToExprStack();
		Code.put(Code.newarray);
		if (newFactorArray.getType().obj.getType() == SymbolTable.charType) {
			Code.put(0);
		} else { // i bool smestas kao reci a ne kao bajtove
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
			postfixExpr.clear();
			operationStack.clear();
		}else {
			Obj desObj = SymbolTable.findInProgram(programObj, ((DesignatorIndex)des).getDesignatorName().getName());
			Code.load(desObj); // uvek mora da se stavi var svakako
			postfixExprToExprStack();
			
			if(((DesignatorIndex)des).getExpr().getClass() == TernaryExpr.class) { // fix za ternarni expr u indeksu
				Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
				Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
			}
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
			postfixExpr.clear();
			operationStack.clear();
		}else {
			Obj desObj = SymbolTable.findInProgram(programObj, ((DesignatorIndex)des).getDesignatorName().getName());
			Code.load(desObj); // uvek mora da se stavi var svakako
			postfixExprToExprStack();
			
			if(((DesignatorIndex)des).getExpr().getClass() == TernaryExpr.class) { // fix za ternarni expr u indeksu
				Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
				Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
			}
			
			Code.put(Code.dup2);
			Code.put(Code.aload); // ucitaj element niza zadat sa dup2
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.put(Code.astore);
		}
	}
	
	public void visit(TernaryExpr terExpr) {
		boolean giveLBack = false;
		while(!operationStack.isEmpty()) { // mora zbog poslednjeg Expr1
			String op = operationStack.pop();
			if(op != LBRACKET) postfixExpr.add(op);
			else giveLBack = true;
		}
		
		if(giveLBack) operationStack.add(LBRACKET);

		if(terExpr.getParent().getClass() != DesignatorIndex.class) {
			postfixExprToExprStack(); // mora if da ne bi prvo stavio terExpr na ExprStack pa onda adresu niza
			Code.buf[byteCodeFix] = (byte)((Code.pc-byteCodeFix+1)>>8); // prvo stavljam visi pa nizi bajt u bajtkod, pc vec pokazuje pocetak drugog Expr
			Code.buf[byteCodeFix+1] = (byte)(Code.pc-byteCodeFix+1);  // pa ne treba pc + 1
		}
		lastBeen = "TernExpr";
	}
	
	public void visit(Qmark qmark) {
		boolean giveLBack = false;
		while(!operationStack.isEmpty()) {
			String op = operationStack.pop();
			if(op != LBRACKET) postfixExpr.add(op);
			else giveLBack = true;
		}
		
		if(giveLBack) operationStack.add(LBRACKET);
		
		postfixExpr.add(QMARK);
		lastBeen = "Qmark";
	}
	
	public void visit(Colon colon) {
		boolean giveLBack = false;
		while(!operationStack.isEmpty()) {
			String op = operationStack.pop();
			if(op != LBRACKET) postfixExpr.add(op);
			else giveLBack = true;
		}
		
		if(giveLBack) operationStack.add(LBRACKET);
		
		postfixExpr.add(COLON);
		lastBeen = "Colon";
	}
	
	public void visit(ReadStmt readStmt) {
		Designator des = readStmt.getDesignator();
		
		if(postfixExpr.contains(QMARK)) pomeraj = 0;
		
		if(des instanceof SingleDesignator) {
			Obj obj = SymbolTable.findInProgram(programObj, ((SingleDesignator)des).getDesignatorName().getName());
			if(obj.getType() == SymbolTable.charType) Code.put(Code.bread);
			else Code.put(Code.read);
			Code.store(obj);
			
		}else {
			Obj obj = SymbolTable.findInProgram(programObj, ((DesignatorIndex)des).getDesignatorName().getName());
			Code.load(obj);
			
			while(!operationStack.empty()) {
				postfixExpr.add(operationStack.pop());
			}
			
			if(obj.getType() == SymbolTable.charArrayType) charArray = true;
			else charArray = false;
			postfixExprToExprStack();
			if(obj.getType().getElemType() == SymbolTable.charType) Code.put(Code.bread);
			else Code.put(Code.read);
			if(obj.getType().getElemType() == SymbolTable.charType) Code.put(Code.bastore);
			else Code.put(Code.astore); // i bool smestas kao reci
		}
	}
}
