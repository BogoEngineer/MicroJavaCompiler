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
    	designator.obj = obj;
    }
    
	public void visit(DesignatorIndex designator){
   		Obj obj = SymbolTable.find(designator.getName());
    	if(obj == SymbolTable.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName()+" nije deklarisano! ", null);
    	}
    	designator.obj = obj;
    }
    
    public void visit(AssignmentExpr assExpr){
    	if(assExpr.getDesignator() instanceof DesignatorIndex){
    		Obj designator = SymbolTable.find(((DesignatorIndex)assExpr.getDesignator()).getName());
    		Struct leftOperandType = designator.getType().getElemType();
    		
    	};
    	Obj designator = SymbolTable.find(((SingleDesignator)assExpr.getDesignator()).getName());
    	if(designator.getKind() != Obj.Var) {
    		report_error("Naziv na levoj strani dodele mora oznacavati promenljivu!", assExpr);
    		return;
    	}
    	
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
