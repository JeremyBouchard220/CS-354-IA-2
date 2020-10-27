//Handles situations where a While is made

public class NodeStmtWhile extends NodeStmt 
{
    private NodeBoolExpr whileBool;
    private NodeStmt stmt;

    public NodeStmtWhile(NodeBoolExpr whileBool, NodeStmt stmt)
    {
        this.whileBool = whileBool;
        this.stmt = stmt;
    }

    public double eval (Environment env) throws EvalException
    {
        while(whileBool.eval(env) == 1)
        {
            stmt.eval(env);
        }
        return 0;
    }
}
