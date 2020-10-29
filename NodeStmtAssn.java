//Handles instances where an Assn is made.

public class NodeStmtAssn extends NodeStmt
{
    private NodeExpr expr;
    private String string;

    public NodeStmtAssn(NodeExpr expr2, String string)
    {
        this.expr = expr2;
        this.string = string;
    }

    public double eval (Environment env) throws EvalException
    {
        return env.put(string, expr.eval(env));
    }
}
