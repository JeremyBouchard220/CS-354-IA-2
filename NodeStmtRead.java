import java.util.Scanner;

public class NodeStmtRead extends NodeStmt
{
    private String read;
    private Scanner scan;

    public NodeStmtRead(String id)
    {
        this.read = id;
    }

    public double eval (Environment env) throws EvalException
    {
        scan = new Scanner(System.in);
        return env.put(read, scan.nextDouble());
    }
}
