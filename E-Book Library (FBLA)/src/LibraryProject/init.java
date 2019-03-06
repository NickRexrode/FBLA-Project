package LibraryProject;


//This is the class where everything starts.
public class init{
	//every instance of init will have a window.  This is the main window.
		public static Window window = new Window();
	public init() { //constructor to make a new init().  Basically starts the program.
		//when the class is made this will be called which will start the program to the front screen.
		Window.showFront(window);
			
		}
		
	public static void main(String[] a) {
		//creates a new instance of this class
		new init();
	}


}
