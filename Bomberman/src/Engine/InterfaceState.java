package Engine;

/***************************************************************************
* InterfaceState
* desc: 	an interface, which shows the basic structure of all our 
*			different gamestates. create a new gamestate by implementing
*			this interface and adding it to the class Gamestate as well
*			as to the main gameloop, so it gets called if we change
*			the global gamestate accordingly
***************************************************************************/
public interface InterfaceState 
{
	/***************************************************************************
	* init
	* desc: 	do all initializing for this gamestate here (create and setup
	*			entities, states etc)
	* @param: 	void
	* @return:		void
	***************************************************************************/ 
	public void init();
	
	
	/***************************************************************************
	* desc: 	main sub-gameloop for this exact state. here is where the real
	*			action happens, getting inputs, moving entities according to 
	*			input changes, event handling, collision handling etc etc
	* @param: 	long elapsed_time: 	elapsed time since last main gameloop update
	*								of the engine
	* @return:		void
	***************************************************************************/
	public void main(long elapsed_time);
	
	
	/****************************************************************************
	* shutdown
	* desc: 	this gets called if we switch to another game state, so do
	*			some cleanup here and dont leave a huge mess behind :P
	* @param: 	void
	* @return:		void
	***************************************************************************/
	public void shutdown();
}
