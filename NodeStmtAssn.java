//Handles instances where an Assn is made.

public class NodeStmtAssn extends NodeStmt
{
    private NodeExpr expr;

    public NodeStmtAssn(NodeExpr expr2)
    {
        this.expr = expr2;
    }

    public double eval (Environment env) throws EvalException
    {
        return expr.eval(env);
    }
}
