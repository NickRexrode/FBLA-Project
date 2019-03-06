package LibraryProject;
//imports, Basically everything i used to create this project.
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

//CODE STARTS HERE
public class Window extends JFrame implements ActionListener{
	
	
	public Window() {  // This is the Window constructor.  In here I set the basics of what I want this window to be!
		this.setUndecorated(true);  // This removes the ability to resize and also removes the top Bar.
		this.setVisible(true);  //Allows you to see the window.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allows for more than one tab.  When you close one, it will check for others open.  If there are none, then it will completely close the program.
		this.setSize(800,600);  // Sets size 800 wide, 600 tall
		this.getContentPane().setBackground(new Color(245,238, 205)); // Sets background
		this.setLayout(null); //  Allows me to place items where I want.
		
		

		
	}
	//HERE ARE THE CLASS METHODS -- I chose to have the majority of them static methods so I could pass the window in as a parameter.
	public static void clearBoard(Window window) { // I made this method to clear off all components of the Frame.
		window.getContentPane().removeAll(); // clears board
		updateBoard(window); //updates board
	}
	public static void updateBoard(Window window) { //This method will update the Frame.  I made this method because I knew I would need to do this alot
		//I choose to use all three of the different ways to update the whole board so that this method would be fairly universal.
		window.invalidate();  
		window.validate();
		window.repaint();
	}
 	public static void showFront(Window window) { //This is the front page that you see when you first open the program.
		addExitBar(window); // This method adds the exit bar to the top so you can close the program.
		//The next three lines create the images for the page
		ImageIcon logo = new ImageIcon("resources/logoText.png");
		ImageIcon person = new ImageIcon("resources/logo.png");
		ImageIcon book = new ImageIcon("resources/book.png");
		//They are then added to JLabels so they can be seen.
		JLabel personLabel = new JLabel(person);
		JLabel imageBookLabel = new JLabel(book);
		JLabel imageLogoLabel = new JLabel(logo);
		//Following this, I set the coordinates and the sizes of the pictures.  I used .setBounds() instead of .setLocaton() and .setSize() because it takes less lines.
		imageLogoLabel.setBounds(100, 25, 600, 150);
		imageBookLabel.setBounds(75, 200, 250, 200);
		personLabel.setBounds(475, 200, 250, 200);
		//Finally, I add the images to the content pane.
		window.getContentPane().add(imageLogoLabel);
		window.getContentPane().add(imageBookLabel);
		window.getContentPane().add(personLabel);

		//Here I create the two main buttons on the front screen.  
		Button enter = new Button("Admin"); 
		enter.setBounds(525, 450, 150, 35);  //sets location
		enter.setFont(new Font("Verdana", Font.PLAIN, 16));  // I use the font Verdana throughout the program.
	    enter.addActionListener(new ActionListener() { // I added an Action Listener here so I can tell when the user clicks the button
	        public void actionPerformed(ActionEvent e) {
	        	showBookList(window); // I call this method to display the next screen.
	    		addTopBar(window); //adds the top bar with all of the tabs for the librarian
	        }
	    });
	    
		
		Button view = new Button("View Books");
		view.setBounds(125, 450, 150, 35); //sets location
		view.setFont(new Font("Verdana", Font.PLAIN, 16)); 
	    view.addActionListener(new ActionListener() {  //Action Listener to tell when the button is pressed
	        public void actionPerformed(ActionEvent e) {
	        	showBookList(window); //This method is similar to the other one; however, instead of showing everything that a librarian would need, only the books are shown.  More of a search function for customers.

	        }
	        
	    });

		
		//Lastly, the items are added to the window
		window.getContentPane().add(enter);
		window.getContentPane().add(view);

		

		



		updateBoard(window);// I update the window so the changes are shown.


	}


	public static void showBookList(Window window) {
		clearBoard(window); //clears the board

		String[] titles = new String[Book.getBookList().size()];  // Creates a String Array for the titles.  It is the same length as the ammount of book that are in the library.  This way you will never get an error about indexes
		for (int i = 0; i < titles.length; i++) {
			titles[i] = Book.getBookList().get(i).title; //This sets the index of titles to the title of the book of matching index.
		}
		
		JList<String> list = new JList<String>(titles); //creates the JList that will display the titles of the books.
		list.setBackground(new Color(245,238, 205)); //Sets the background of the list to match the screen
		list.setFont(new Font("Verdana", Font.PLAIN, 16));
		JTextField searchBar = new JTextField("Search");  //Sets up a search bar.
		searchBar.setBounds(0, 0, 300, 25); // This sets the location and size of the Scroll Bar
		
		//This is the scrollpane that will allow you to scroll in the list of books
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(0, 25, 300, 400);

		//When you click a book the title author and topics will all show up.  I initialize the JLabels here so that I can call .setText() later.
		//All of these follow the same format.  Just location differs.
		JLabel title = new JLabel();
		title.setFont(new Font("Verdana", Font.PLAIN, 16));
		title.setBounds(350, 50 , 300, 25);

		JLabel author = new JLabel();
		author.setFont(new Font("Verdana", Font.PLAIN, 16));
		author.setBounds(350, 100, 300, 25);

		JLabel topics = new JLabel();
		topics.setFont(new Font("Verdana", Font.PLAIN, 16));
		topics.setBounds(350, 150, 300, 25);
		
		//This is the listener for the JList
		list.addListSelectionListener(new ListSelectionListener() {//The Listener will perform valueChanged() when you select and item

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String titleSelected = list.getSelectedValue(); // the title that is selected
				Book b = Book.getBookByTitle(titleSelected); //The book that matches the title
				//The next set the JLabel we initialized earlier to values.
				title.setText(b.title); 
				author.setText(b.author);
				topics.setText(Arrays.toString(b.topics)); // I used Array.toString() to maintain the comma like structure of an array
				
				updateBoard(window); //Updates the window to insure that you see the new changes.
				
				
			
				
				
			}
			
		});
		//This is the searchbar listener.  It is located lower in the code to make sure everything else was initilized.
		searchBar.addKeyListener(new KeyListener() { // The keylistener will tell us when a key is pressed.  Basically any keyboard interaction

			@Override
			public void keyPressed(KeyEvent arg0) {
				// This method is not needed for this process, however it needs to be here just incase!  
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) { 
				ArrayList<Book> listb = Book.getBooksFromSearch(searchBar.getText()); //This will get an ArrayList that will contain the books that match the search critera.  I used an arraylist because the size could be unknown.
				String[] titles = new String[listb.size()]; //This creates a array of titles.  Again I used the size of the list so you will never have an index error.
				for (int i = 0; i < titles.length; i++) {
					titles[i] = listb.get(i).title;
				}
				list.setListData(titles); //This resets the data of the JList to the searched Critera.  
				}

			@Override
			public void keyTyped(KeyEvent arg0) {
				//Both KeyReleased and KeyTyped will give the same result.  KeyReleased will technically be later in the sequence.  But I decided to use it.
				
			}
			
		});
		//I add everything to the Window right here.
		window.getContentPane().add(scrollPane);
		window.getContentPane().add(searchBar);
		window.getContentPane().add(title);
		window.getContentPane().add(author);
		window.getContentPane().add(topics);
		
		updateBoard(window); // Updates the window
	}
	public static void addBookScreen(Window window) { //This screen is where you will add a new book.
		clearBoard(window); // window is cleared
		//Here i set up the main criteria for a book.
		JTextField bookTitle = new JTextField("Book's Title");
		JTextField bookAuthor = new JTextField("Book's Author");
		JTextField bookTopics = new JTextField("Book's Topics (Seperated by comma)");
		//Now I set the sizes and locations.
		bookTitle.setBounds(50, 50, 225, 25);
		bookAuthor.setBounds(50, 100, 225, 25);
		bookTopics.setBounds(50, 150, 225, 25);
		//This is the submit button
		JButton submit = new JButton("Create new book");
		submit.setBounds(50, 200, 150, 50);
		JLabel conf = new JLabel();
		conf.setBounds(250, 200, 500, 50);
		conf.setFont(new Font("Verdana", Font.PLAIN, 16));
		window.getContentPane().add(conf);
		//This is the action listener for the button
		submit.addActionListener(new ActionListener() {// this will trigger actionPerformed() whenever the button is clicked

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// I check to make sure that all of the Option have been chanaged from their original state.
				if (bookTitle.getText().equals("Book's Title") || bookAuthor.getText().equals("Book's Author") || bookTopics.getText().equals("Book's Topics (Seperated by comma)")) {
				} else {
					//When it is changed this will happen
					conf.setText("Book "+ bookTitle.getText() + " has been created");
					String[] splitBookTopics = bookTopics.getText().split(",");//This splits the topics into an array.
					Book.writeBook(new Book(bookTitle.getText(), bookAuthor.getText(), splitBookTopics)); //Calls writeBook with a new book.  This is how a book is created in the system.
					
					
				}
			}
			
		});
		//I add everything to the page.
		window.getContentPane().add(bookTitle);
		window.getContentPane().add(bookAuthor);
		window.getContentPane().add(bookTopics);
		window.getContentPane().add(submit);
		

		
	}
	//This is the method that will put the top bar with all of the Librarian Features.
	public static void addTopBar(Window window) {
		clearBoard(window); // resets the window;
		// the JMenus are the actual items that you click.  I initilize them here.
		JMenu viewBooks = new JMenu("View Books");
		JMenu addBook = new JMenu("New Book");
		JMenu viewStudents = new JMenu("View Students");
		JMenu addStudent = new JMenu("Add Student");
		JMenuBar topBar = new JMenuBar();
		//I set the font for them.
		viewBooks.setFont(new Font("Verdana", Font.PLAIN, 12));
		addBook.setFont(new Font("Verdana", Font.PLAIN, 12));
		viewStudents.setFont(new Font("Verdana", Font.PLAIN, 12));
		addStudent.setFont(new Font("Verdana", Font.PLAIN, 12));
		//Here are the listeners that will perform a method when the JMenu is selected.
		viewStudents.addMenuListener(new MenuListener() {

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				showStudentList(window); // This will call this method which will display the StudentList, it switches the page.
			}
			
		});
		addStudent.addMenuListener(new MenuListener() {

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				viewAddStudent(window); // this will show the page to add a new student.
			}
			
		});
		viewBooks.addMenuListener(new MenuListener() {

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				showBookList(window); //This will show the page with the bookList
				
			}
			
		});
		addBook.addMenuListener(new MenuListener() {

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				addBookScreen(window); //This will show the page to add a book.
				
			}
			
		});
		//These are all added to the JMenuBar.
		topBar.add(addBook);
		topBar.add(viewBooks);
		topBar.add(viewStudents);
		topBar.add(addStudent);
		//I add the Logout button at the top
		JMenu logout = new JMenu("Log Out");
		logout.setFont(new Font("Verdana", Font.PLAIN, 12));
		logout.addMenuListener(new MenuListener() {

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				//This removes all of the admin features and then brings the window back to the front page.
				window.setJMenuBar(null); //clears top bar.
				clearBoard(window);//clears window completely
				addExitBar(window); //adds the original bar with only the exit button.
				showFront(window);
			}
			
		});
		
		topBar.add(new JSeparator());// This JSeparator allows for a gap between the logout and the rest of the buttons.
		topBar.add(logout); //adds it to JMenuBar

		window.setJMenuBar(topBar); //Finally adds the bar to the window
		updateBoard(window); //updates window
		
	}
	//This method adds the orginal exit bar.
	public static void addExitBar(Window window) {
		JMenuBar top = new JMenuBar(); //Makes a JMenuBar to hold the JMenus
		top.add(new JSeparator()); //Adds separator to keep the "Exit" button on the far right.
		JMenu close = new JMenu("Exit"); //Adds JMenu to exit
		close.setFont(new Font("Verdana", Font.PLAIN, 12));
		close.addMenuListener(new MenuListener() {//Listener tells me when the JMenu is selected

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//This is not needed for this use.  But is required.
				
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				System.exit(0); // Terminates program without error.
				
			}
			
		});
		top.add(close);//adds the JMenu to the JMenuBar
		window.setJMenuBar(top); //adds everything to window
		updateBoard(window); //updates the window.
	}
	//this method shows the page where the students list is located.
	public static void showStudentList(Window window) {
		clearBoard(window); //clears the window
		ArrayList<Student> studentList = Student.getStudentList();  //Gets a complete list of students.  I use an ArrayList because of its unknown size.
		//I want to display the students by first and last name.  I make a String[] to store their first and last name.
		String[] firstLast = new String[studentList.size()];// I use the size of the student list to ensure that everyone is in the list and that it doesn't give me an index error.
		for (int i = 0; i< firstLast.length; i++) {
			firstLast[i] = studentList.get(i).firstname + " "+ studentList.get(i).lastname; // the indexes of the two arrays will match up.
		}
		//here I create the Jlist and establish the information about it.
		JList<String> list = new JList<String>(firstLast);
		list.setBackground(new Color(245,238, 205));
		list.setFont(new Font("Verdana", Font.PLAIN, 16));
		//I make a scrollPane so that when the list is too big, you can see more.
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(0, 25, 300, 400);
		
		//Here i create another JList that will show what books the Selected student has checked out.
		JList<String> checkedList = new JList<String>(); // Make new JList of type String
		checkedList.setBackground(new Color(245,238, 205)); //set color to background
		checkedList.setFont(new Font("Verdana", Font.PLAIN, 16)); 
		JScrollPane jsp = new JScrollPane(checkedList); // a new scroll pane is made and the JList of checked out books is put into it
		jsp.setBounds(500, 325, 275, 300);
		//This puts text above the JList to help the user know what it is.
		JLabel checkedOutBooks = new JLabel("Checked Out Books:");
		checkedOutBooks.setFont(new Font("Verdana", Font.PLAIN, 16));
		checkedOutBooks.setBounds(500, 300, 300, 25);
		
		JTextField searchBar = new JTextField("Search");
		searchBar.setBounds(0, 0, 300, 25);
		window.getContentPane().add(searchBar);
		searchBar.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {//this will trigger every time the user types in the box.
				ArrayList<Student> students = Student.getStudentsbySearch(searchBar.getText()); //gets the students that apply to the search term.
				String[] names = new String[students.size()]; // this array and for loop is creating the list of names.
				for (int i = 0; i< names.length; i++) {
					names[i] = students.get(i).firstname + " " + students.get(i).lastname;
				}
				
				list.setListData(names); //sets list to the new names
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
		});
		//Here i initialize some Labels so that later i can call .setText() on them to update them.  This will show the checked out books information
		JLabel title = new JLabel();
		JLabel author = new JLabel();
		JLabel topics = new JLabel();
		
		title.setBounds(325, 300, 150, 25);
		author.setBounds(325, 350, 150, 25);
		topics.setBounds(325, 400, 150, 25);
		
		title.setFont(new Font("Verdana", Font.PLAIN, 12));
		author.setFont(new Font("Verdana", Font.PLAIN, 12));
		topics.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		//This button will export all the the students data to a csv file!
		JButton export = new JButton("Export Report");
		JLabel exported = new JLabel();
		exported.setFont(new Font("Verdana", Font.PLAIN, 16));
		window.getContentPane().add(exported);
		exported.setBounds(200, 500, 300, 50);
		export.setBounds(0, 500, 150, 50);
		 
		JTextField firstname = new JTextField();	//sets up firstname box 
		JTextField lastname = new JTextField();		//sets up lastname box 
		JTextField grade = new JTextField();		//sets up grade box
		JTextField email = new JTextField();		//sets up email box
		JTextField classVal = new JTextField();		//sets up classVal box
		
		firstname.setBounds(600, 25, 150, 25);
		lastname.setBounds(600, 75, 150, 25);
		email.setBounds(600, 225, 150, 25);
		grade.setBounds(600, 125, 150, 25);
		classVal.setBounds(600, 175, 150, 25);
		
		window.getContentPane().add(export);
		window.getContentPane().add(title);
		window.getContentPane().add(author);
		window.getContentPane().add(topics);
		window.getContentPane().add(checkedOutBooks);
		window.getContentPane().add(jsp);
		
		//These are listeners that tell me when something happened.
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { //This will occur when the button export is clicked.
				exported.setText("Exported!");
				Student.exportAll();
				
			}
			
		});
		checkedList.addListSelectionListener(new ListSelectionListener() { //This will happen when a book is selected.

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Book b = Book.getBookByTitle(checkedList.getSelectedValue()); // gets a book based on the title selected
				//Then I set the value of the already initilized variables so they display a new text.
				title.setText(b.title);
				author.setText(b.author);
				topics.setText(Arrays.toString(b.topics));
				
				updateBoard(window); //update the window to show changes.
				 
				
			}
			
		});

		list.addListSelectionListener(new ListSelectionListener( ) { //This will happen when a student is selcted

			@Override
			public void valueChanged(ListSelectionEvent e) { 
				Student student = Student.getStudentByFirstLast(list.getSelectedValue().toString()); //This will return a student based on their first and last name.
				//Now i will set up the JTextFields so you can view and edit a Students information
				


				firstname.setText(student.firstname);
				lastname.setText(student.lastname);
				grade.setText("" +student.grade); // cast to string by adding ""
				classVal.setText(""+ student.classVal); //cast to string by adding
				email.setText(student.email);
				 //this displays the students email

				
				

				
				
				ArrayList<Book> a = Student.getCheckedOut(student); // This gets the books that are checked out by the user.  It is an ArrayList because the size could be unknown
				
				String[] b = new String[a.size()];  // This creates a String[] of the titles of the books.  The size is the same as the length of the book arrayList so no index errors can occur
				for (int i = 0; i < a.size(); i++) {
					b[i] = a.get(i).title;
				}
				checkedList.setListData(b); // this updates the list of checked out books.


				JButton edit = new JButton("Edit Account"); //This adds the edit account button.
				edit.setBounds(600, 275, 150, 25);
				edit.addActionListener(new ActionListener() {// Will trigger when the edit Account button is pressed.

					@Override
					public void actionPerformed(ActionEvent e) {
						//creates a new student so that when addUpdate() is called it can find the old student in the list, and then replace it with the new one.
						Student newS = new Student(firstname.getText(), lastname.getText(), Integer.parseInt(grade.getText()), Integer.parseInt(classVal.getText()), student.bookIds, email.getText());
						Student.addUpdate(student, newS); //calls function to replace the old student with the new one.
						showStudentList(window);  //refreshes the page.

						
					}
					
				});

				//These are the sign and return book buttons.
				JButton assignBook = new JButton("Assign Book");
				assignBook.setBounds(325, 100, 150, 25);
				assignBook.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {//This will trigger when the assign book button is pressed.
						BookSelector a = new BookSelector(); // creates a new instance of the class BookSelector.
						a.addBookList(a,Student.getStudentByFirstLast(list.getSelectedValue())); //calls .addBookList with the appropriate data.
						BookSelector.updateBoard(a); // then it updates the new window.
						
						
						
					}
					
				});
				
				JButton returnBook = new JButton("Return Book");
				returnBook.setBounds(325, 200, 150, 25);
				
				returnBook.addActionListener(new ActionListener( ) {//triggers when the return book button is pressed.

					@Override
					public void actionPerformed(ActionEvent arg0) {
						BookSelector a = new BookSelector();//creates a new instance of BookSelector
						a.addReturnList(a, Student.getStudentByFirstLast(list.getSelectedValue())); // calls .addReturnList with the appropriate data
						BookSelector.updateBoard(a); // updates the new window
						
					}
					
				});
				//adds everything to the window.
				window.getContentPane().add(returnBook);
				window.getContentPane().add(assignBook);
				window.getContentPane().add(edit);
				window.getContentPane().add(firstname);
				window.getContentPane().add(lastname);
				window.getContentPane().add(grade);
				window.getContentPane().add(classVal);
				window.getContentPane().add(email);

				
				updateBoard(window);
				

				
				
			}
			
			
		});
		window.getContentPane().add(scrollPane);
		//updates the window to make sure you see everything.
		updateBoard(window);
		
	}
	public static void viewAddStudent(Window window) {//This function shows the addNewSTudent page.
		clearBoard(window); //this will clear the window
		//Creates all of the input fields for creating a new Student
		JTextField firstname = new JTextField("First Name");
		firstname.setBounds(25, 25, 150, 25);
		
		JTextField lastname = new JTextField("Last Name");
		lastname.setBounds(25, 75, 150, 25);
		
		JLabel grade = new JLabel("Grade:");
		grade.setBounds(25, 125, 150, 25);
		
		JTextField classVal = new JTextField("Graduation Year");
		classVal.setBounds(350, 125, 150, 25);
		

		JTextField email = new JTextField("Email Address");
		email.setBounds(25, 200, 150, 25);

		JButton submit = new JButton("Submit");
		submit.setBounds(50, 250, 100, 25);
		//I made radio buttons to select grade number because I knew it could only be 4 possibilities,
		JRadioButton ninthGrade = new JRadioButton("9th");
		ninthGrade.setBounds(100, 115, 50, 50);
		JRadioButton tenthGrade = new JRadioButton("10th");
		tenthGrade.setBounds(150, 115, 50, 50);
		JRadioButton eleventhGrade = new JRadioButton("11th");
		eleventhGrade.setBounds(200, 115, 50, 50);
		JRadioButton twelthGrade = new JRadioButton("12th");
		twelthGrade.setBounds(250, 115, 50, 50);
		//I changed the background color to match the window background.
		ninthGrade.setBackground(new Color(245,238, 205));
		tenthGrade.setBackground(new Color(245,238, 205));
		eleventhGrade.setBackground(new Color(245,238, 205));
		twelthGrade.setBackground(new Color(245,238, 205));
		//I create a new radio Group so that only one Radio button can be selected at once.
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(ninthGrade);
		radioGroup.add(tenthGrade);
		radioGroup.add(eleventhGrade);
		radioGroup.add(twelthGrade);
		//This is where i set up the error messages.  I am just initizing the errors with blank text.
		JLabel error = new JLabel("");
		error.setBounds(25, 160, 300, 25);
		error.setFont(new Font("Verdana", Font.PLAIN, 12));
		error.setForeground(new Color(255, 0, 0)); //color red
		window.getContentPane().add(error);
		JLabel classError = new JLabel();
		classError.setBounds(350, 150, 300, 25);
		classError.setFont(new Font("Verdana", Font.PLAIN, 12));
		classError.setForeground(new Color(255, 0, 0));//color red
		window.getContentPane().add(classError);
		
		JLabel emailError = new JLabel();
		emailError.setBounds(25, 225, 300, 25);
		emailError.setFont(new Font("Verdana", Font.PLAIN, 12));
		emailError.setForeground(new Color(255, 0, 0));//color red
		window.getContentPane().add(emailError);
		
		JLabel firstError = new JLabel();
		firstError.setBounds(25, 45, 300, 25);
		firstError.setFont(new Font("Verdana", Font.PLAIN, 12));
		firstError.setForeground(new Color(255, 0, 0));//color red
		window.getContentPane().add(firstError);
		
		JLabel lastError = new JLabel();
		lastError.setBounds(25, 95, 300, 25);
		lastError.setFont(new Font("Verdana", Font.PLAIN, 12));
		lastError.setForeground(new Color(255, 0, 0));//color red
		window.getContentPane().add(lastError);
		
		submit.addActionListener(new ActionListener() {
	

			@Override
			public void actionPerformed(ActionEvent e) {
				//here i be sure to reset the error values everytime so that when an error is fixed the error text goes away.
				error.setText("");
				classError.setText("");
				emailError.setText("");
				firstError.setText("");
				lastError.setText("");

				
				
				
				boolean good = true;//this boolean will determine if the student should be created.  If it stays true throughout the tests then its a good student account.
				int grade= -1; // I set grade to -1 so i know it will never match anything.
				if (ninthGrade.isSelected()) {
					grade = 9;
				} else if (tenthGrade.isSelected()) {
					grade = 10;
				} else if (eleventhGrade.isSelected()) {
					grade = 11;
				}else if (twelthGrade.isSelected()) {
					grade = 12;
				} else {
					error.setText("You need to select a grade"); // the error message is displayed
					good = false; // turns boolean to false.  The new account cannot be made.
					updateBoard(window); // updates window to show the new error
				}
				try {
					Integer temp = Integer.parseInt(classVal.getText());//checks to make sure the input is a number and not a string.
				} catch (NumberFormatException a)  {
					classError.setText("This field requires a number"); // if it is not a number then the error will appear.
					good = false; // will stop it from being created.
					
					
				}
				if (!email.getText().contains("@")) { // every email has @ so if it doesn't its not a valid email.
					emailError.setText("This is not a valid email address");

					good = false;
				}
				if (firstname.getText().equalsIgnoreCase("First Name")) { // makes sure the user has changed the firstname category.
					firstError.setText("Please enter a name");
					good = false;
				}
				if (lastname.getText().equalsIgnoreCase("Last Name")) { // makes sure the user has changed the lastname category.
					lastError.setText("Please enter a name");
					good = false;
					
				}

				if (good) { // if the boolean has not changed the code will execute.
				//this displays a success message.
				JLabel success = new JLabel("Successfully created an account for "+firstname.getText() + " "+ lastname.getText());
				success.setFont(new Font("Verdana", Font.PLAIN, 12));
				success.setBounds(50, 300, 500, 25);
				window.getContentPane().add(success); //adds to window
				updateBoard(window); // updates the window
				//creates the new student.
				Student.addNewStudent(new Student(firstname.getText(), lastname.getText(), grade, Integer.parseInt(classVal.getText()), new ArrayList<Integer>(), email.getText()));
				}
			}
			
		});
		
		
		//adds everything to the window.
		window.getContentPane().add(submit);
		window.getContentPane().add(classVal);
		window.getContentPane().add(grade);
		window.getContentPane().add(ninthGrade);
		window.getContentPane().add(tenthGrade);
		window.getContentPane().add(eleventhGrade);
		window.getContentPane().add(twelthGrade);
		window.getContentPane().add(firstname);
		window.getContentPane().add(email);
		window.getContentPane().add(lastname);

		
		updateBoard(window);//updates the window.
		
		
	} 
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//This method does nothing but I have to put it here.
		
	}
}

