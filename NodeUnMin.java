//This subclass checks for a minus sign that is used to dictate if a value is negative rather than the subtract operation

public class NodeUnMin extends NodeFact
{
    private NodeFact expr;

    public NodeUnMin (NodeFact expr) 
    {
	    this.expr=expr;
    }

    public double eval(Environment env) throws EvalException 
    {
	    return -expr.eval(env);
    }

}