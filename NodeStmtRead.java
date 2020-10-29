import java.util.Scanner;

//Handles instances where a Read is made.

public class NodeStmtRead extends NodeStmt
{
    private String read;
    private static Scanner scan = null;

    public NodeStmtRead(String id)
    {
        this.read = id;
    }

    public double eval (Environment env) throws EvalException
    {
        if(scan == null)
        {
            scan = new Scanner(System.in);
        }
        return env.put(read, scan.nextDouble());
    }
}
