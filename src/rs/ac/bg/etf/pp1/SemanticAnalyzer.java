package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.SymbolTable;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	Obj currentMethod = null;
	boolean errorDetected = false;
	boolean mainMethodCondition = false;
	Obj currentCalledMethod = null;
	int nVars;
	
	Type constType = null;
	
	Logger log = Logger.getLogger(getClass());

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
	
	public void visit(VarWithType varDecl){
		VarNoType varSignature = varDecl.getVarNoType();
		String varType = varDecl.getType().getTypeName();
		if(varSignature instanceof VarIdentSingle){
			report_info("Deklarisana promenljiva "+ ((VarIdentSingle)varSignature).getVarName(), varDecl);
			if(SymbolTable.findInThisScope(((VarIdentSingle)varSignature).getVarName()) != SymbolTable.noObj) report_error("Promenljiva moze biti deklarisana samo jednom!", varDecl);
			else {
				Obj varNode = SymbolTable.insert(Obj.Var, ((VarIdentSingle)varSignature).getVarName(), varDecl.getType().obj.getType());
				varNode.setAdr(varDeclCount++);
			}
		}
		if(varSignature instanceof VarIdentArray){
			Struct type = SymbolTable.nullType;
			if(varType.equals("int")) type = SymbolTable.intArrayType;
			if(varType.equals("char")) type = SymbolTable.charArrayType;
			if(varType.equals("bool")) type = SymbolTable.boolArrayType;
			report_info("Deklarisana (niz) promenljiva "+ ((VarIdentArray)varSignature).getVarName(), varDecl);
			if(SymbolTable.findInThisScope(((VarIdentArray)varSignature).getVarName()) != SymbolTable.noObj) report_error("Promenljiva moze biti deklarisana samo jednom!", varDecl);
			Obj varNode = SymbolTable.insert(Obj.Var, ((VarIdentArray)varSignature).getVarName(), type);
			varNode.setAdr(varDeclCount++);
		}
	}
	
    public void visit(ProgName progName){
    	progName.obj = SymbolTable.insert(Obj.Prog, progName.getProgName(), SymbolTable.noType);
    	SymbolTable.openScope();
    }
    
    public void visit(Program program){
    	nVars = SymbolTable.currentScope.getnVars();
    	SymbolTable.chainLocalSymbols(program.getProgName().obj);
    	SymbolTable.closeScope();
    	if(!mainMethodCondition) report_error("Ne postoji main funkcija bez povratnog tipa i bez argumenata", null);
    }
    
    public void visit(SingleConstDecl singleConstDecl){
    	constType = singleConstDecl.getType();
    	Obj constTypeNode = SymbolTable.find(constType.getTypeName()); // objektni cvor tipa konstante
    	Constant con = singleConstDecl.getConstant();
    	Obj requiredType = SymbolTable.noObj;
    	Struct structType = SymbolTable.noType;
    	int val = 0;
    	if(con instanceof NumberCon){
    		 requiredType = SymbolTable.find("int");
    		 structType = SymbolTable.intType; 
    		 val = ((NumberCon)con).getNum();
    	}
    	if(con instanceof CharCon){
    		requiredType = SymbolTable.find("char"); 
    		structType = SymbolTable.charType;
    		val = ((CharCon)con).getChr();
    	}
    	if(con instanceof BoolCon){
    		requiredType = SymbolTable.find("bool"); 
    		structType = SymbolTable.boolType;
    		val = ((BoolCon)con).getBol().equals("true") ? 1 : 0;
    	}
    	if(constTypeNode != requiredType){
    		report_error("Tip konstante " + singleConstDecl.getConstName() + " ne odgovara navedenom tipu! Na liniji: " + con.getLine(), null);
    	}else{
    		Obj in = SymbolTable.insert(Obj.Con, singleConstDecl.getConstName(), structType);
    		in.setAdr(val);
    	}
    }
   
   	public void visit(MultiConstDecl multiConstDecl){
   		Obj constTypeNode = SymbolTable.find(constType.getTypeName()); // objektni cvor tipa konstante
   		Constant con = multiConstDecl.getConstant();
   		Obj requiredType = SymbolTable.noObj;
    	Struct structType = SymbolTable.noType;
    	int val = 0;
    	if(con instanceof NumberCon){
    		 requiredType = SymbolTable.find("int");
    		 structType = SymbolTable.intType; 
    		 val = ((NumberCon)con).getNum();
    	}
    	if(con instanceof CharCon){
    		requiredType = SymbolTable.find("char"); 
    		structType = SymbolTable.charType;
    		val = ((CharCon)con).getChr();
    	}
    	if(con instanceof BoolCon){
    		requiredType = SymbolTable.find("bool"); 
    		structType = SymbolTable.boolType;
    		val = ((BoolCon)con).getBol().equals("true") ? 1 : 0;
    	}
   		if(constTypeNode != requiredType){
    		report_error("Tip konstante " + multiConstDecl.getConstName() + " ne odgovara navedenom tipu! Na liniji: " + con.getLine(), null);
    	}else{
    		Obj in = SymbolTable.insert(Obj.Con, multiConstDecl.getConstName(), structType);
    		in.setAdr(val);
    	}
   	}
    
    public void visit(ConstDeclSemi constDeclSemi){
    	constType = null;
    }
   
    public void visit(Type type){
    	Obj typeNode = SymbolTable.find(type.getTypeName());
    	if(typeNode == SymbolTable.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.obj = SymbolTable.noObj;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.obj = typeNode;
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.obj = SymbolTable.noObj;
    		}
    	}
    }
    
    public void visit(MethodTypeName methodTypeName){
    	Struct methodType;
    	if(methodTypeName.getMethodType() instanceof NonVoidType) methodType = SymbolTable.find(((NonVoidType)methodTypeName.getMethodType()).getType().getTypeName()).getType();
    	else methodType = SymbolTable.noType;
    	currentMethod = SymbolTable.insert(Obj.Meth, methodTypeName.getMethName(), methodType); // mora biti void funkcija
    	methodTypeName.obj = currentMethod;
    	SymbolTable.openScope();
    	varDeclCount = 0; // resetujes pracenje rednog broja promenljive za main metodu
		report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
    }
    
    public void visit(MethodDeclNoVars methodDeclNoVars){
    	if(currentMethod.getName().equals("main") && currentMethod.getType()==SymbolTable.noType 
    		&& (methodDeclNoVars.getFormPars() instanceof NoFormParam)) mainMethodCondition = true;
    	SymbolTable.chainLocalSymbols(currentMethod);
    	SymbolTable.closeScope();
    	currentMethod = null;
    }
    
    public void visit(MethodDeclVars methodDecl){
    	if(currentMethod.getName().equals("main") && currentMethod.getType()==SymbolTable.noType 
    		&& (methodDecl.getFormPars() instanceof NoFormParam)) mainMethodCondition = true;
    	SymbolTable.chainLocalSymbols(currentMethod);
    	SymbolTable.closeScope();
    	currentMethod = null;
    }
    
    public void visit(SingleDesignator designator){
    	Obj obj = SymbolTable.find(designator.getDesignatorName().getName());
    	if(obj == SymbolTable.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getDesignatorName().getName()+" nije deklarisano! ", null);
    	}
    	designator.obj = obj;
    }
    
	public void visit(DesignatorIndex designator){
		Obj exprObj = designator.getExpr().obj;
   		Obj obj = SymbolTable.find(designator.getDesignatorName().getName());
    	if(obj == SymbolTable.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getDesignatorName().getName()+" nije deklarisano! ", null);
    	}
    	if(obj.getType().getKind() != Struct.Array){
    		report_error("Ne moze se indeksirati promenljiva " + obj.getName() + " jer nije tipa niz!", designator);
    	}
    	if(exprObj.getType() != SymbolTable.intType){
    		report_error("Ne moze se indeksirati promenljiva " + obj.getName() + " jer izraz za indeksiranje nije tipa int!", designator);
    	}
    	designator.obj = new Obj(Obj.Var, "", obj.getType().getElemType()); // uzima se tip jednog elementa u nizu
    }
    
    public void visit(AssignmentExpr assExpr){
    	Obj exprObj = assExpr.getExpr().obj;
    	if(assExpr.getDesignator() instanceof DesignatorIndex){
    		Obj designator = SymbolTable.find(((DesignatorIndex)assExpr.getDesignator()).getDesignatorName().getName());
    		Struct leftOperandType = designator.getType().getElemType(); // uzima se tip promenljive elementa niza
			// vec jeste promenljiva pa ne treba provera kao za ovo ispod
	    	if(leftOperandType != exprObj.getType() && exprObj.getType() != SymbolTable.nullType){ // ako se nizu (referenci) dodeljuje null to je legalno
	    		report_error("Operandi operacije dodele vrednosti moraju biti istog tipa! ", assExpr);
	    		return;
	    	}
    	}else {
	   		Obj designator = SymbolTable.find(((SingleDesignator)assExpr.getDesignator()).getDesignatorName().getName());
	    	if(designator.getKind() != Obj.Var) {
	    		report_error("Naziv na levoj strani dodele mora oznacavati promenljivu!", assExpr);
	    		return;
	    	}
	    	if(designator.getType() != exprObj.getType()){
	    		report_error("Operandi operacije dodele vrednosti moraju biti istog tipa!", assExpr);
	    		return;
	    	}
    	}
    }
    
    public void visit(Var var){
    	Obj designatorObj = var.getDesignator().obj;
    	var.obj = SymbolTable.noObj;
    	if(designatorObj == SymbolTable.noObj) return; // vec izbacena greska
    	var.obj = designatorObj;
    }
    
    public void visit(NumConst numCon){
    	numCon.obj = new Obj(Obj.Con, "$", SymbolTable.intType);
    }
    
	public void visit(CharConst charCon){
    	charCon.obj = new Obj(Obj.Con, "$", SymbolTable.charType);
    }
    
	public void visit(BoolConst boolCon){
    	boolCon.obj = new Obj(Obj.Con, "$", SymbolTable.boolType);
    }
    
    public void visit(New newFactor){
    	Obj newFactorObj = newFactor.getNewFactor().obj;
    	newFactor.obj = SymbolTable.noObj;
    	if(newFactorObj == SymbolTable.noObj) return; // vec izbacena greska
    	newFactor.obj = newFactorObj;
    }
    
    public void visit(ParenExpr pExpr){
    	Obj exprObj = pExpr.getExpr().obj;
    	pExpr.obj = SymbolTable.noObj;
    	if(exprObj == SymbolTable.noObj) return; // vec izbacena greska
    	pExpr.obj = exprObj;
    }
    
    public void visit(NewFactorSingle newFactorSingle){
    	newFactorSingle.obj = SymbolTable.find(newFactorSingle.getType().getTypeName());
    }
    
    public void visit(NewFactorArray newFactorArray){
    	Obj exprObj = newFactorArray.getExpr().obj;
    	if(exprObj.getType() != SymbolTable.intType) report_error("Izraz unutar [ ] mora biti int!", newFactorArray);
    	Struct arrayElemType = newFactorArray.getType().obj.getType();
    	String typeName = "";
    	if(arrayElemType == SymbolTable.intType) typeName = "int[]";
    	if(arrayElemType == SymbolTable.charType) typeName = "char[]";
    	if(arrayElemType == SymbolTable.boolType) typeName = "bool[]";
    	newFactorArray.obj = SymbolTable.find(typeName);
    }    
    
    public void visit(FactorTerm factorTerm){
    	factorTerm.obj = factorTerm.getFactor().obj;
    }
    
    public void visit(FactorMulopTerm factorMulopTerm){
    	Obj factorObj = factorMulopTerm.getFactor().obj;
    	if(factorMulopTerm.obj == null) factorMulopTerm.obj = factorMulopTerm.getTerm().obj; // ako nema obj uzmi obj od prvog Terma
    	if(factorObj == SymbolTable.noObj) return; // vec je izbacena greska
    	if(factorObj.getType() != SymbolTable.intType){
    		report_error("Operandi mulop operacije moraju biti celobrojnog tipa! ", factorMulopTerm);
    		return;
    	}
    	if(factorMulopTerm.obj.getType() != factorObj.getType()) {
    		report_error("Operandi mulop operacije moraju biti istog tipa! " + factorObj.getType().getKind() + factorMulopTerm.obj.getType().getKind(), factorMulopTerm);
    		return;
    	}
    	factorMulopTerm.obj = factorObj;
    }
    
    public void visit(AddExpr addExpr){
    	Obj termObj = addExpr.getTerm().obj;
    	addExpr.obj = SymbolTable.noObj;
    	if(termObj == SymbolTable.noObj) return; // vec je izbacena greska
    	if(termObj.getType() != SymbolTable.intType) {
    		addExpr.getTerm().obj = SymbolTable.noObj;
    		report_error("Operandi moraju biti celobrojnog tipa! ", addExpr);
    		return;
    	}
    	addExpr.obj = termObj;
    }
    
    public void visit(TermExpr termExpr){
    	Obj termObj = termExpr.getTerm().obj;
    	termExpr.obj = SymbolTable.noObj;
    	if(termObj == SymbolTable.noObj) return; // vec je izbacena greska
  		termExpr.obj = termObj;
    }
    
    public void visit(PosExpr1 posExpr1){
		Obj unsignedExprObj = posExpr1.getUnsignedExpr().obj;
		posExpr1.obj = SymbolTable.noObj;
		if(unsignedExprObj == SymbolTable.noObj) return; // vec je izbacena greska
		posExpr1.obj = unsignedExprObj;
	}
	
	public void visit(NegExpr1 negExpr1){
		Obj unsignedExprObj = negExpr1.getUnsignedExpr().obj;
		negExpr1.obj = SymbolTable.noObj;
		if(unsignedExprObj == SymbolTable.noObj) return; // vec je izbacena greska
		if(unsignedExprObj.getType() != SymbolTable.intType){
			negExpr1.obj = SymbolTable.noObj;
			report_error("Operand mora biti celobrojnog tipa! ", negExpr1);
			return;
		}
		negExpr1.obj = unsignedExprObj;
	}
	
	public void visit(NormalExpr normalExpr){
		Obj expr1Obj = normalExpr.getExpr1().obj;
		normalExpr.obj = SymbolTable.noObj;
		if(expr1Obj == SymbolTable.noObj) return; // vec je izbacena greska
		normalExpr.obj = expr1Obj;
		
		/* if(currentCalledMethod == null) return;
		if(currentCalledMethod.getName() == "chr" && expr1Type!=SymbolTable.intType || 
		   currentCalledMethod.getName() == "ord" && expr1Type!=SymbolTable.charType || 
		   currentCalledMethod.getName() == "len" && expr1Type.getKind()!=Struct.Array
		){
			report_error("Argument ugradjene funkcije nije zadovoljavajuc! ", normalExpr);
		} */ // u slucaju da moze da se pozivaju ugradjene metode otkomentarisi ovo
	}
	
	public void visit(TernaryExpr ternaryExpr){
		Obj conditionObj = ternaryExpr.getExpr1().obj;
		Obj firstChoiceObj = ternaryExpr.getExpr11().obj;
		Obj secondChoiceObj = ternaryExpr.getExpr12().obj;
		if(firstChoiceObj == SymbolTable.noObj || secondChoiceObj == SymbolTable.noObj || conditionObj == SymbolTable.noObj) return; // vec izbacena greska
		if(firstChoiceObj.getType() != secondChoiceObj.getType()){
			report_error("Operandi ternarnog operatora moraju biti istog tipa! ", ternaryExpr);
			ternaryExpr.obj = SymbolTable.noObj;
			return;
		}
		if(conditionObj.getType() != SymbolTable.boolType){
			report_error("Uslov ternarnog operatora mora biti tipa bool! ", ternaryExpr);
			ternaryExpr.obj = SymbolTable.noObj;
			return;
		}
		ternaryExpr.obj = firstChoiceObj; // svejedno od kog operanda ce uzeti tip, isti su
	}
    
    public void visit(PrintStmt prnt){
    	Struct exprType = prnt.getExpr().obj.getType();
    	if(exprType != SymbolTable.intType && exprType != SymbolTable.boolType && exprType != SymbolTable.charType) 
    		report_error("Argument funkcije print mora biti int, char ili bool! ", prnt);
    }
    
    public void visit(ReadStmt rd){
    	if(rd.getDesignator() instanceof DesignatorIndex) return; // sve je vec u redu ako je element niza
    	Obj obj = SymbolTable.find(((SingleDesignator)rd.getDesignator()).getDesignatorName().getName());
    	if(obj.getType().getKind() == Struct.Array || obj.getKind() != Obj.Var) report_error("Argument funkcije read mora biti promenljiva ili element niza! ", rd);
    }
    
    public void visit(Increment inc){
	    if(inc.getDesignator() instanceof DesignatorIndex){
	    	Obj obj = SymbolTable.find(((DesignatorIndex)inc.getDesignator()).getDesignatorName().getName());
		    if(obj.getType().getElemType() != SymbolTable.intType) report_error("Operand operacije ++ mora biti tipa int! ", inc);
	    }
	    else{
	    	Obj obj = SymbolTable.find(((SingleDesignator)inc.getDesignator()).getDesignatorName().getName());
		    if(obj.getKind() != Obj.Var) report_error("Operand operacije ++ mora biti promenljiva ili element niza ", inc);
		    if(obj.getType() != SymbolTable.intType) report_error("Operand operacije ++ mora biti tipa int! ", inc);
	    }
    }
    
    public void visit(Decrement dec){
    	if(dec.getDesignator() instanceof DesignatorIndex){
	    	Obj obj = SymbolTable.find(((DesignatorIndex)dec.getDesignator()).getDesignatorName().getName());
		    if(obj.getType().getElemType() != SymbolTable.intType) report_error("Operand operacije -- mora biti tipa int! ", dec);
	    }
	    else{
	    	Obj obj = SymbolTable.find(((SingleDesignator)dec.getDesignator()).getDesignatorName().getName());
		    if(obj.getKind() != Obj.Var) report_error("Operand operacije -- mora biti promenljiva ili element niza ", dec);
		    if(obj.getType() != SymbolTable.intType) report_error("Operand operacije -- mora biti tipa int! ", dec);
	    }
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
