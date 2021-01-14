// generated with ast extension for cup
// version 0.8
// 14/0/2021 16:52:18


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(DeclarationList DeclarationList);
    public void visit(Extending Extending);
    public void visit(DesignatorFactor DesignatorFactor);
    public void visit(MethodDecl MethodDecl);
    public void visit(VarDeclSemi VarDeclSemi);
    public void visit(Constant Constant);
    public void visit(Relop Relop);
    public void visit(MethodType MethodType);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(MethodDeclBlock MethodDeclBlock);
    public void visit(BraceMethodDeclBlock BraceMethodDeclBlock);
    public void visit(UnsignedExpr UnsignedExpr);
    public void visit(StatementBlock StatementBlock);
    public void visit(StatementList StatementList);
    public void visit(VarNoType VarNoType);
    public void visit(Factor Factor);
    public void visit(MinusSign MinusSign);
    public void visit(CondTerm CondTerm);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(SwitchCaseList SwitchCaseList);
    public void visit(ActualParamList ActualParamList);
    public void visit(VarDeclList VarDeclList);
    public void visit(FormalParamList FormalParamList);
    public void visit(Expr Expr);
    public void visit(Expr1 Expr1);
    public void visit(PrintExtension PrintExtension);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ActualPars ActualPars);
    public void visit(NewFactor NewFactor);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(VarDeclarationBlock VarDeclarationBlock);
    public void visit(CondFact CondFact);
    public void visit(Declaration Declaration);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormPars FormPars);
    public void visit(Modop Modop);
    public void visit(Divideop Divideop);
    public void visit(Mulop Mulop);
    public void visit(Subtractop Subtractop);
    public void visit(Addop Addop);
    public void visit(LessEqualop LessEqualop);
    public void visit(Lessop Lessop);
    public void visit(GreaterEqualsop GreaterEqualsop);
    public void visit(Greaterop Greaterop);
    public void visit(NotEqualop NotEqualop);
    public void visit(Equalop Equalop);
    public void visit(Assignop Assignop);
    public void visit(DesignatorName DesignatorName);
    public void visit(SingleDesignator SingleDesignator);
    public void visit(DesignatorIndex DesignatorIndex);
    public void visit(NewFactorArray NewFactorArray);
    public void visit(NewFactorSingle NewFactorSingle);
    public void visit(DesignatorActPars DesignatorActPars);
    public void visit(LeftParen LeftParen);
    public void visit(ParenExpr ParenExpr);
    public void visit(New New);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(Var Var);
    public void visit(FactorTerm FactorTerm);
    public void visit(FactorMulopTerm FactorMulopTerm);
    public void visit(TermExpr TermExpr);
    public void visit(AddExpr AddExpr);
    public void visit(NegExpr1 NegExpr1);
    public void visit(PosExpr1 PosExpr1);
    public void visit(Colon Colon);
    public void visit(Qmark Qmark);
    public void visit(TernaryExpr TernaryExpr);
    public void visit(NormalExpr NormalExpr);
    public void visit(ERECondFact ERECondFact);
    public void visit(ExprCondFact ExprCondFact);
    public void visit(SingleCondFact SingleCondFact);
    public void visit(MultiCondFact MultiCondFact);
    public void visit(SingleCondTerm SingleCondTerm);
    public void visit(MultiCondTerm MultiCondTerm);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(Decrement Decrement);
    public void visit(Increment Increment);
    public void visit(DesignatorStatementDerived1 DesignatorStatementDerived1);
    public void visit(AssignmentExpr AssignmentExpr);
    public void visit(SingleCase SingleCase);
    public void visit(MultiCase MultiCase);
    public void visit(NoPExtension NoPExtension);
    public void visit(PExtension PExtension);
    public void visit(ReadStmt ReadStmt);
    public void visit(DoWhileStmt DoWhileStmt);
    public void visit(PrintStmt PrintStmt);
    public void visit(StatementDerived1 StatementDerived1);
    public void visit(Assignment Assignment);
    public void visit(SingleStatement SingleStatement);
    public void visit(MultiStatement MultiStatement);
    public void visit(NoStmtBlck NoStmtBlck);
    public void visit(StmtBlck StmtBlck);
    public void visit(FormalParamDecl2 FormalParamDecl2);
    public void visit(FormalParamDecl1 FormalParamDecl1);
    public void visit(SingleFormalParamDecl SingleFormalParamDecl);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(SingleVarDeclSemi SingleVarDeclSemi);
    public void visit(MultiVarDeclSemi MultiVarDeclSemi);
    public void visit(VoidType VoidType);
    public void visit(NonVoidType NonVoidType);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(MethodDeclNoVars MethodDeclNoVars);
    public void visit(MethodDeclVars MethodDeclVars);
    public void visit(SingleMethodDeclaration SingleMethodDeclaration);
    public void visit(MultiMethodDeclarations MultiMethodDeclarations);
    public void visit(NoMethodBlock NoMethodBlock);
    public void visit(MethodBlock MethodBlock);
    public void visit(BMDBlock BMDBlock);
    public void visit(NotExtended NotExtended);
    public void visit(Extended Extended);
    public void visit(ClassDecl ClassDecl);
    public void visit(Type Type);
    public void visit(VarIdentArray VarIdentArray);
    public void visit(VarIdentSingle VarIdentSingle);
    public void visit(VarWithType VarWithType);
    public void visit(VarDeclListDerived1 VarDeclListDerived1);
    public void visit(SingleVar SingleVar);
    public void visit(MultiVarDecl MultiVarDecl);
    public void visit(VarDeclSemicolon VarDeclSemicolon);
    public void visit(BoolCon BoolCon);
    public void visit(CharCon CharCon);
    public void visit(NumberCon NumberCon);
    public void visit(SingleConstDecl SingleConstDecl);
    public void visit(MultiConstDecl MultiConstDecl);
    public void visit(ConstDeclSemi ConstDeclSemi);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(ConstDeclaration ConstDeclaration);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(SingleDeclaration SingleDeclaration);
    public void visit(MultiDeclaration MultiDeclaration);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
