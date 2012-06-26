package Gamestates;
import Engine.DebugConsole;
import Engine.InterfaceState;


/***************************************************************************
* StateNoState
* desc: 	just a dummy used for initializing the engine and if
*			we are running into some unknown states
***************************************************************************/
public class StateNoState implements InterfaceState
{
	public StateNoState()
	{
		// nothing here
	}
	
	@Override
	public void init() 
	{
		DebugConsole.Print("Initializing NOSTATE");
	}

	@Override
	public void main(long elapsed_time) 
	{
		DebugConsole.Print("Running NOSTATE");
	}

	@Override
	public void shutdown() 
	{
		DebugConsole.Print("Shutting down NOSTATE");
	}
}
