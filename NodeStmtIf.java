public class NodeStmtIf extends NodeStmt
{
    private NodeStmt expr;
    private NodeStmt expr2;
    private NodeBoolExpr boolExpr;

    public NodeStmtIf(NodeStmt expr, NodeBoolExpr boolExp, NodeStmt expr2)
    {
        this.expr = expr;
        this.expr2 = expr2;
        this.boolExpr = boolExpr;
    }

    public double eval (Environment env) throws EvalException
    {
        if(boolExpr.eval(env) == 1)
        {
            return expr.eval(env);
        }
        if(expr2 == null)
        {
            return 0;
        }
        else
        {
            return expr2.eval(env);
        }
    }
}
