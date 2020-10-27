public class NodeStmtWrite extends NodeStmt
{
    private NodeExpr write;

    public NodeStmtWrite(NodeExpr expr)
    {
        this.write = expr;
    }

    public double eval (Environment env) throws EvalException
    {
        System.out.println(write.eval(env));
        return write.eval(env);
    }
}
