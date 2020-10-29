// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

import java.util.*;

public class Parser {

    private Scanner scanner;

    private void match(String s) throws SyntaxException {
	scanner.match(new Token(s));
    }

    private Token curr() throws SyntaxException {
	return scanner.curr();
    }

    private int pos() {
	return scanner.pos();
    }

	private NodeMulop parseMulop() throws SyntaxException {
	if (curr().equals(new Token("*"))) {
	    match("*");
	    return new NodeMulop(pos(),"*");
	}
	if (curr().equals(new Token("/"))) {
	    match("/");
	    return new NodeMulop(pos(),"/");
	}
	return null;
    }

    private NodeAddop parseAddop() throws SyntaxException {
	if (curr().equals(new Token("+"))) {
	    match("+");
	    return new NodeAddop(pos(),"+");
	}
	if (curr().equals(new Token("-"))) {
	    match("-");
	    return new NodeAddop(pos(),"-");
	}
	return null;
    }

    private NodeFact parseFact() throws SyntaxException {
	if (curr().equals(new Token("("))) {
	    match("(");
	    NodeExpr expr=parseExpr();
	    match(")");
	    return new NodeFactExpr(expr);
	}
	if (curr().equals(new Token("id"))) {
	    Token id=curr();
	    match("id");
	    return new NodeFactId(pos(),id.lex());
	}
	if(curr().equals(new Token("-")))
	{	
		match("-");
		NodeFact id = parseFact();
		return new NodeUnMin(id);
	}
	Token num=curr();
	match("num");
	return new NodeFactNum(num.lex());
    }

    private NodeTerm parseTerm() throws SyntaxException {
	NodeFact fact=parseFact();
	NodeMulop mulop=parseMulop();
	if (mulop==null)
	    return new NodeTerm(fact,null,null);
	NodeTerm term=parseTerm();
	term.append(new NodeTerm(fact,mulop,null));
	return term;
    }

    private NodeExpr parseExpr() throws SyntaxException {
	NodeTerm term=parseTerm();
	NodeAddop addop=parseAddop();
	if (addop==null)
	    return new NodeExpr(term,null,null);
	NodeExpr expr=parseExpr();
	expr.append(new NodeExpr(term,addop,null));
	return expr;
    }

	//May not be needed, we'll see.
	private NodeAssn parseAssn() throws SyntaxException {
	Token id=curr();
	match("id");
	match("=");
	NodeExpr expr=parseExpr();
	NodeAssn assn=new NodeAssn(id.lex(),expr);
	return assn;
	}
	
	//Might still need tweaking
	private NodeBoolExpr parseBoolExpr() throws SyntaxException
	{
		NodeExpr leftHandSide = parseExpr();
        NodeRelop relop = parseRelop();
        NodeExpr rightHandSide = parseExpr();
        return new NodeBoolExpr(leftHandSide, relop, rightHandSide);
	}

	//Might still need tweaking
	private NodeRelop parseRelop() throws SyntaxException
	{
		if(curr().equals(new Token("==")))
		{
			match("==");
			return new NodeRelop(pos(), "==");
		}
		else if(curr().equals(new Token("<>")))
		{
			match("<>");
			return new NodeRelop(pos(), "<>");
		}
		else if(curr().equals(new Token("<=")))
		{
			match("<=");
			return new NodeRelop(pos(), "<=");
		}
		else if(curr().equals(new Token(">=")))
		{
			match(">=");
			return new NodeRelop(pos(), ">=");
		}
		else if(curr().equals(new Token("<")))
		{
			match("<");
			return new NodeRelop(pos(), "<");
		}

		match(">");
		return new NodeRelop(pos(), ">");
	}

    // private NodeStmt parseStmt() throws SyntaxException {
	// NodeAssn assn=parseAssn();
	// match(";");
	// NodeStmtAssn stmt=new NodeStmtAssn(assn);
	// NodeStmtRead read=new NodeStmtRead(assn);
	// NoteStmtWrite
	// NodeStmtIf
	// NodeStmtWhile
	// NodeStmtBegin
	// return stmt;
	// }
	
	//Might still need tweaking.
	private NodeStmt parseStmt() throws SyntaxException
    {
		if(curr().equals(new Token("id"))) 
		{
			Token id = curr();
			match("id");
			match("=");
			NodeExpr expr = parseExpr();
			return new NodeStmtAssn(expr, id.lex());
		}
		if(curr().equals(new Token("rd")))
		{
			match("rd");
			Token id = curr();
			match("id");
			return new NodeStmtRead(id.lex());
		}

		if(curr().equals(new Token("wr")))
		{
			match("wr");
			NodeExpr expr = parseExpr();
			return new NodeStmtWrite(expr);
		}

		if(curr().equals(new Token("if")))
		{
			match("if");
			NodeBoolExpr boolexpr = parseBoolExpr();
			match("then");
			NodeStmt ifThenStmt = parseStmt();

			if(curr().lex().equals("else"))
			{
				match("else");
				NodeStmt elseStmt = parseStmt();
				return new NodeStmtIf(ifThenStmt, boolexpr, elseStmt);
			}
			else
			{
				return new NodeStmtIf(ifThenStmt, boolexpr, null);
			}
		}

		if(curr().equals(new Token("while")))
		{
			match("while");
			NodeBoolExpr whileBoolexpr = parseBoolExpr();
			match("do");
			NodeStmt whileStmt = parseStmt();
			return new NodeStmtWhile(whileBoolexpr, whileStmt);
		}

		match("begin");
		NodeBlock block  = parseBlock();
		match("end"); 

		return new NodeStmtBegin(block);
    }

	// public NodeProg parseProg() throws SyntaxException
	// {
	// 	NodeBlock assn = parseBlock();
	// 	if(!scanner.done())
	// 	{
	// 		throw new SyntaxException(pos(),new Token("EOF"), curr());
	// 	}
	// 	return new NodeProg(assn);
		
	// }

	//Might still need tweaking.
	public NodeBlock parseBlock() throws SyntaxException
	{
		NodeStmt parse = parseStmt();
		NodeBlock block = null;
		if(curr().equals(new Token(";")))
		{
			match(";");
			block = parseBlock();
		}
		return new NodeBlock(block, parse);
	}
	
	public Node parse(String program) throws SyntaxException {
	scanner=new Scanner(program);
	scanner.next();
	//NodeBlock block=parseBlock();
	//match("EOF");
	return parseBlock();
	}

}
