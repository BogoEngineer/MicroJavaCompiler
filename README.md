# MicroJavaCompiler

Compiler for language MicroJava (subset of Java). The compiling is done in 4 stages: Lexer Analysis, Syntax Analysis, Semantic Analysis and Code Generation. 

## MicroJava specification

Supported: 
- primitive types (int, char, bool)
- array of primitive types
- global variables
- local variables (for main method)
- print and read methods
- operations +, -, *, /, %, ++, --

## Usage

Use ant build with build.xml or run manually src/rs/ac/bg/etf/pp1/Compiler.java <YourMicroJavaProgram.mj> <WantedObjFile.obj> to get .obj file that is runnable on MJVM (MicroJava virtual machine).


## Tools used
- JFlex (lexical analyzer generator) : https://jflex.de/
- CUP (LALR(1) parsing engine) : http://www2.cs.tum.edu/projects/cup/
