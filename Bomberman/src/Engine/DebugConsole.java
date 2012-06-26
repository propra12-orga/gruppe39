package Engine;

/***************************************************************************
* DebugConsole
* desc: 	small class, providing simple calls to print some quick and
*			formated information to the java console, for debugging
*			purpose only, obviously
***************************************************************************/
public class DebugConsole 
{
	// #######################################################################
	// Variables
	// #######################################################################
	// our verbose level, defines how much we want to see on screen
	private static int verbose_level = 0;
	
	
	
	// #######################################################################
	// Public functions
	// #######################################################################

	/***************************************************************************
	* setVerboseLevel
	* desc: 	set the verbose level for our session
	* @param: 	int level: 	0 = nothing
	*						1 = print errors only with little additional information
	*						2 = print errors and normal text with little additional information
	*						3 = print errors only with much additional information
	*						4 = print errors and normal text with much additional information
	* @return:		void
	***************************************************************************/
	public static void setVerboseLevel(int level)
	{
		DebugConsole.verbose_level = level;
	}
	
	
	/***************************************************************************
	* PrintError
	* desc: 	prints an error formated massage to the screen
	* @param: 	String Text: our text we want ot get printed
	* @return:		void
	***************************************************************************/
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
	
	
	/***************************************************************************
	* Print
	* desc: 	prints a normal formated massage to the screen
	* @param: 	String Text: our text we want ot get printed
	* @return:		void
	***************************************************************************/
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
	
	
	
	// #######################################################################
	// Private functions
	// #######################################################################
	
	// nothing here
}
