
public class DebugConsole 
{
	static int verbose_level = 0;
	
	public static void setVerboseLevel(int level)
	{
		DebugConsole.verbose_level = level;
	}
	
	public static void PrintError(String Text)
	{
		if (verbose_level >= 1)
		{
			// print custom error text
			StackTraceElement[] ste = Thread.currentThread().getStackTrace();
			System.out.println(">>>>> ERROR " + ste[2]+ ": " + Text);
		
			// print stack trace as well
			if (verbose_level >= 3)
			{
				System.out.print("Stacktrace: ");
				// print stack trace, leave out first two elements, we dont need them
				for (int i = 2; i < ste.length; i++) 
				{
					System.out.print(ste[i]);
					if (!(i == ste.length - 1))
						System.out.print(" -> ");
				}
				System.out.println("");
			}
		}
	}
	
	public static void Print(String Text)
	{
		if (verbose_level >= 2)
		{
			// print custom text
			StackTraceElement[] ste = Thread.currentThread().getStackTrace();
			System.out.println(">>>>> " + ste[2]+ ": " + Text);
		
			// print stack trace as well
			if (verbose_level >= 4)
			{
				System.out.print("Stacktrace: ");
				// print stack trace, leave out first two elements, we dont need them
				for (int i = 2; i < ste.length; i++) 
				{
					System.out.print(ste[i]);
					if (!(i == ste.length - 1))
						System.out.print(" -> ");
				}
				System.out.println("");
			}
		}
	}
}
