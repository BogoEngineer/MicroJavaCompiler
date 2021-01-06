package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;


class SymbolTable extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool),
			intArrayType = new Struct(Struct.Array, SymbolTable.intType),
			charArrayType = new Struct(Struct.Array, SymbolTable.charType),
			boolArrayType = new Struct(Struct.Array, SymbolTable.boolType);
	
	public static void init(){
		Tab.init();
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", boolType));	
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "int[]", intArrayType));
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "char[]", charArrayType));
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool[]", boolArrayType));
	}
	
	public static Obj findInThisScope(String name) {
		Obj resultObj = null;
		if (currentScope().getLocals() != null) {
			resultObj = currentScope().getLocals().searchKey(name);
			if (resultObj != null) return resultObj;
		}
		return noObj;
	}
}