//Handles situations where a Write is made

public class NodeStmtWrite extends NodeStmt
{
    private NodeExpr write;

    public NodeStmtWrite(NodeExpr expr)
    {
        this.write = expr;
    }

    public double eval (Environment env) throws EvalException
    {
        Double output = write.eval(env);
        System.out.println(output);
        return output;
    }
}
