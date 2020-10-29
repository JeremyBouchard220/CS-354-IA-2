public class NodeBlock extends NodeStmt
{
    private NodeBlock block;
    private NodeStmt check;

    public NodeBlock(NodeBlock block, NodeStmt check)
    {
        this.block = block;
        this.check = check;
    }

    public double eval (Environment env) throws EvalException
    {
        Double eval = check.eval(env);
        if(block != null)
        {
            return block.eval(env);
        }
        else
        {
            return eval;
        }
    }
}
