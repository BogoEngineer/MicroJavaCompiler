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
		varDeclCount++;
		VarNoType varSignature = varDecl.getVarNoType();
		String varType = varDecl.getType().getTypeName();
		if(varSignature instanceof VarIdentSingle){
			report_info("Deklarisana promenljiva "+ ((VarIdentSingle)varSignature).getVarName(), varDecl);
			if(SymbolTable.findInThisScope(((VarIdentSingle)varSignature).getVarName()) != SymbolTable.noObj) report_error("Promenljiva moze biti deklarisana samo jednom!", varDecl);
			else {Obj varNode = SymbolTable.insert(Obj.Var, ((VarIdentSingle)varSignature).getVarName(), varDecl.getType().struct);}
		}
		if(varSignature instanceof VarIdentArray){
			Struct type = SymbolTable.nullType;
			if(varType.equals("int")) type = SymbolTable.intArrayType;
			if(varType.equals("char")) type = SymbolTable.charArrayType;
			if(varType.equals("bool")) type = SymbolTable.boolArrayType;
			report_info("Deklarisana (niz) promenljiva "+ ((VarIdentArray)varSignature).getVarName(), varDecl);
			if(SymbolTable.findInThisScope(((VarIdentArray)varSignature).getVarName()) != SymbolTable.noObj) report_error("Promenljiva moze biti deklarisana samo jednom!", varDecl);
			Obj varNode = SymbolTable.insert(Obj.Var, ((VarIdentArray)varSignature).getVarName(), type);
		}
	}
	
    public void visit(PrintStmt print) {
		printCallCount++;
	}
    
    public void visit(ProgName progName){
    	progName.obj = SymbolTable.insert(Obj.Prog, progName.getProgName(), SymbolTable.noType);
    	SymbolTable.openScope();
    }
    
    public void visit(Program program){
    	SymbolTable.chainLocalSymbols(program.getProgName().obj);
    	SymbolTable.closeScope();
    	if(!mainMethodCondition) report_error("Ne postoji main funkcija bez povratnog tipa i bez argumenata", null);
    }
    
    public void visit(SingleConstDecl singleConstDecl){
    	constType = singleConstDecl.getType();
    	Obj constTypeNode = SymbolTable.find(constType.getTypeName()); // objektni cvor tipa konstante
    	Constant con = singleConstDecl.getConstant();
    	Obj requiredType = null;
    	Struct structType = null;
    	if(con instanceof NumberCon){
    		 requiredType = SymbolTable.find("int");
    		 structType = SymbolTable.intType; 
    	}
    	if(con instanceof CharCon){
    		requiredType = SymbolTable.find("char"); 
    		structType = SymbolTable.charType;
    	}
    	if(con instanceof BoolCon){
    		requiredType = SymbolTable.find("bool"); 
    		structType = SymbolTable.boolType;
    	}
    	if(constTypeNode != requiredType){
    		report_error("Tip konstante " + singleConstDecl.getConstName() + " ne odgovara navedenom tipu! Na liniji: " + con.getLine(), null);
    	}else{
    		SymbolTable.insert(Obj.Con, singleConstDecl.getConstName(), structType);
    	}
    }
   
   	public void visit(MultiConstDecl multiConstDecl){
   		Obj constTypeNode = SymbolTable.find(constType.getTypeName()); // objektni cvor tipa konstante
   		Constant con = multiConstDecl.getConstant();
   		Obj requiredType = null;
    	Struct structType = null;
    	if(con instanceof NumberCon){
    		 requiredType = SymbolTable.find("int");
    		 structType = SymbolTable.intType; 
    	}
    	if(con instanceof CharCon){
    		requiredType = SymbolTable.find("char"); 
    		structType = SymbolTable.charType;
    	}
    	if(con instanceof BoolCon){
    		requiredType = SymbolTable.find("bool"); 
    		structType = SymbolTable.boolType;
    	}
   		if(constTypeNode != requiredType){
    		report_error("Tip konstante " + multiConstDecl.getConstName() + " ne odgovara navedenom tipu! Na liniji: " + con.getLine(), null);
    	}else{
    		SymbolTable.insert(Obj.Con, multiConstDecl.getConstName(), structType);
    	}
   	}
    
    public void visit(ConstDeclSemi constDeclSemi){
    	constType = null;
    }
   
    public void visit(Type type){
    	Obj typeNode = SymbolTable.find(type.getTypeName());
    	if(typeNode == SymbolTable.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = SymbolTable.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = SymbolTable.noType;
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
    	Obj obj = SymbolTable.find(designator.getName());
    	if(obj == SymbolTable.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName()+" nije deklarisano! ", null);
    	}
    	designator.struct = obj.getType();
    	// report_info("Prenosi se od SingleDesignatora do Designatora: " + designator.struct.getKind(), null);
    }
    
	public void visit(DesignatorIndex designator){
   		Obj obj = SymbolTable.find(designator.getName());
    	if(obj == SymbolTable.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName()+" nije deklarisano! ", null);
    	}
    	designator.struct = obj.getType();
    	// report_info("Prenosi se od DesignatorIndexa do Designatora: " + designator.struct.getKind(), null);
    }
    
    public void visit(AssignmentExpr assExpr){
    	Struct exprType = assExpr.getExpr().struct;
    	if(assExpr.getDesignator() instanceof DesignatorIndex){
    		Obj designator = SymbolTable.find(((DesignatorIndex)assExpr.getDesignator()).getName());
    		Struct leftOperandType = designator.getType().getElemType();
			// vec jeste promenljiva pa ne treba provera kao za ovo ispod
	    	if(designator.getType() != exprType){
	    		report_error("Operandi operacije dodele vrednosti moraju biti istog tipa!", assExpr);
	    		return;
	    	}
    	}else {
	   		Obj designator = SymbolTable.find(((SingleDesignator)assExpr.getDesignator()).getName());
	    	if(designator.getKind() != Obj.Var) {
	    		report_error("Naziv na levoj strani dodele mora oznacavati promenljivu!", assExpr);
	    		return;
	    	}
	    	if(designator.getType() != exprType){
	    		report_error("Operandi operacije dodele vrednosti moraju biti istog tipa!", assExpr);
	    		return;
	    	}
    	}
    }
    
    public void visit(Var var){
    	Struct designatorType = var.getDesignator().struct;
    	var.struct = SymbolTable.noType;
    	if(designatorType == SymbolTable.noType) return; // vec izbacena greska
    	var.struct = designatorType;
    	// report_info("Prenosi se od designatora do Factora: " + var.struct.getKind(), null);
    }
    
    public void visit(NumConst numCon){
    	numCon.struct = SymbolTable.intType;
    	// report_info("Number stavio u obj: " + numCon.struct.getKind(), null);
    }
    
	public void visit(CharConst charCon){
    	charCon.struct = SymbolTable.charType;
    	// report_info("Char stavio u obj od Factora: " + charCon.struct.getKind(), null);
    }
    
	public void visit(BoolConst boolCon){
    	boolCon.struct = SymbolTable.boolType;
    	// report_info("Bool stavio u obj od Factora: " + boolCon.struct.getKind(), null);
    }
    
    public void visit(New newFactor){
    	Struct newFactorType = newFactor.getNewFactor().struct;
    	newFactor.struct = SymbolTable.noType;
    	if(newFactorType == SymbolTable.noType) return; // vec izbacena greska
    	newFactor.struct = newFactorType;
    	// report_info("Prenosi se od NewFactora do Factora: " + newFactor.struct.getKind(), null);
    }
    
    public void visit(ParenExpr pExpr){
    	Struct exprType = pExpr.getExpr().struct;
    	pExpr.struct = SymbolTable.noType;
    	if(exprType == SymbolTable.noType) return; // vec izbacena greska
    	pExpr.struct = exprType;
    	// report_info("Prenosi se od ParenExpr do Factora: " + pExpr.struct.getKind(), null);
    }
    
    public void visit(NewFactorSingle newFactorSingle){
    	newFactorSingle.struct = SymbolTable.find(newFactorSingle.getType().getTypeName()).getType();
    	// report_info("Prenosi se od NewFactoraSingle do NewFactora: " + newFactorSingle.struct.getKind(), null);
    }
    
    public void visit(NewFactorArray newFactorArray){
    	newFactorArray.struct = SymbolTable.find(newFactorArray.getType().getTypeName()+"[]").getType(); // dodato [] jer je tako oznaceno u tabeli simbola, npr. int[] za tip niza intova
    	// report_info("Prenosi se od NewFactoraArray do NewFactora: " + newFactorArray.struct.getKind(), null);
    }    
    
    public void visit(FactorTerm factorTerm){
    	factorTerm.struct = factorTerm.getFactor().struct;
    	// report_info("Prenosi se od Factora do Terma: " + factorTerm.struct.getKind(), null);
    }
    
    public void visit(FactorMulopTerm factorMulopTerm){
    	Struct factorType = factorMulopTerm.getFactor().struct;
    	factorMulopTerm.struct = SymbolTable.noType;
    	if(factorType == SymbolTable.noType) return; // vec je izbacena greska
    	if(factorType != SymbolTable.intType){
    		report_error("Operandi mulop operacije moraju biti celobrojnog tipa! ", factorMulopTerm);
    		return;
    	}
    	if(factorMulopTerm.struct != factorType) {
    		report_error("Operandi mulop operacije moraju biti istog tipa! ", factorMulopTerm);
    		return;
    	}
    	factorMulopTerm.struct = factorType;
    }
    
    public void visit(AddExpr addExpr){
    	Struct termType = addExpr.getTerm().struct;
    	addExpr.struct = SymbolTable.noType;
    	if(termType == SymbolTable.noType) return; // vec je izbacena greska
    	if(termType != SymbolTable.intType) {
    		addExpr.getTerm().struct = SymbolTable.noType;
    		report_error("Operandi moraju biti celobrojnog tipa! ", addExpr);
    		return;
    	}
    	addExpr.struct = termType;
    }
    
    public void visit(TermExpr termExpr){
    	Struct termType = termExpr.getTerm().struct;
    	termExpr.struct = SymbolTable.noType;
    	if(termType == SymbolTable.noType) return; // vec je izbacena greska
  		// report_info("Prenosi se od TermExpra do UnsignedExpra: " + termType.getKind(), null);
  		termExpr.struct = termType;
    }
    
    public void visit(PosExpr1 posExpr1){
		Struct unsignedExprType = posExpr1.getUnsignedExpr().struct;
		posExpr1.struct = SymbolTable.noType;
		if(unsignedExprType == SymbolTable.noType) return; // vec je izbacena greska
		posExpr1.struct = unsignedExprType;
	}
	
	public void visit(NegExpr1 negExpr1){
		Struct unsignedExprType = negExpr1.getUnsignedExpr().struct;
		negExpr1.struct = SymbolTable.noType;
		if(unsignedExprType == SymbolTable.noType) return; // vec je izbacena greska
		if(unsignedExprType != SymbolTable.intType){
			negExpr1.struct = SymbolTable.noType;
			report_error("Operand mora biti celobrojnog tipa! ", negExpr1);
			return;
		}
		negExpr1.struct = unsignedExprType;
	}
	
	public void visit(NormalExpr normalExpr){
		Struct expr1Type = normalExpr.getExpr1().struct;
		normalExpr.struct = SymbolTable.noType;
		if(expr1Type == SymbolTable.noType) return; // vec je izbacena greska
		normalExpr.struct = expr1Type;
	}
	
	public void visit(TernaryExpr ternaryExpr){
		Struct condition = ternaryExpr.getExpr1().struct;
		Struct firstChoiceType = ternaryExpr.getExpr11().struct;
		Struct secondChoiceType = ternaryExpr.getExpr12().struct;
		if(firstChoiceType == SymbolTable.noType || secondChoiceType == SymbolTable.noType || condition == SymbolTable.noType) return; // vec izbacena greska
		if(firstChoiceType != secondChoiceType){
			report_error("Operandi ternarnog operatora moraju biti istog tipa! ", ternaryExpr);
			ternaryExpr.struct = SymbolTable.noType;
			return;
		}
		if(condition != SymbolTable.boolType){
			report_error("Uslov ternarnog operatora mora biti tipa bool! ", ternaryExpr);
			ternaryExpr.struct = SymbolTable.noType;
			return;
		}
		ternaryExpr.struct = firstChoiceType; // svejedno od kog operanda ce uzeti tip, isti su
	}
    
    /*public void visit(FuncCall funcCall){
    	Obj func = funcCall.getDesignator().obj;
    	if(Obj.Meth == func.getKind()){
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
    	}else{
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
    	}
    }
    
    public void visit(Term term){
    	term.struct = term.getFactor().struct;
    }
    
    public void visit(TermExpr termExpr){
    	termExpr.struct = termExpr.getTerm().struct;
    }
    
    public void visit(AddExpr addExpr){
    	Struct te = addExpr.getExpr().struct;
    	Struct t = addExpr.getTerm().struct;
    	if(te.equals(t) && te == Tab.intType){
    		addExpr.struct = te;
    	}else{
			report_error("Greska na liniji "+ addExpr.getLine()+" : nekompatibilni tipovi u izrazu za sabiranje.", null);
			addExpr.struct = Tab.noType;
    	}
    }
    
    public void visit(Const cnst){
    	cnst.struct = Tab.intType;
    }
    
    public void visit(Var var){
    	var.struct = var.getDesignator().obj.getType();
    }
    
    public void visit(ReturnExpr returnExpr){
    	returnFound = true;
    	Struct currMethType = currentMethod.getType();
    	if(!currMethType.compatibleWith(returnExpr.getExpr().struct)){
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
    	}
    }
    
    public void visit(Assignment assignment){
    	if(!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.getType()))
    		report_error("Greska na liniji " + assignment.getLine() + " : " + "nekompatibilni tipovi u dodeli vrednosti! ", null);
    }
    */
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
