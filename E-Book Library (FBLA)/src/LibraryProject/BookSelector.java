package LibraryProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
//CLASS CODE
public class BookSelector extends JFrame{ //this class helped me ground some methods together.  This is used for assigning and returning books.
	public BookSelector() { // this is very similar to the window class.
		this.setUndecorated(true); //removes the resizing functions
		this.setVisible(true); // shows the screen
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //dispose on close will not kill the whole application when you close one tab.
		this.setSize(800, 600); //size 800 wide 600 tall
		this.setLayout(null); //allows me to set where i want things.
		this.getContentPane().setBackground(new Color(245,238, 205)); // sets background color

	
		addTopBar(this); // adds top bar which will allow us to hit the exit button
		
	}
	public static void clearBoard(BookSelector bs) { // This will clear the window of all components.
		bs.getContentPane().removeAll(); //removes all. 
		updateBoard(bs); //updates everything.
	}
	public static void updateBoard(BookSelector bs) { // this will update the window so the user can see the latest changes.
		// I call all three ways to update the screen just to make this method universal.
		bs.invalidate();
		bs.validate();
		bs.repaint();
	}
	//this method will be called when the user is returning a book.
	public void addReturnList(BookSelector bs, Student selected) { // passes the screen and a student into the method.

		String[] titles = new String[Student.getBooksFromAccount(selected).length]; //Here i am making a list of the books which the selected student has checked out.
		Book[] b = Student.getBooksFromAccount(selected);
		for (int i = 0; i < titles.length; i++) { // I loop through the books to get the titles to make the JLIst.

			titles[i] = b[i].title;
		}
		
			JList<String> list = new JList<String>(titles); // I create the JList.
			list.setBackground(new Color(245,238, 205));
			list.setFont(new Font("Verdana", Font.PLAIN, 16));
			// I create and set up a ScrollPane so that when the list is too big you can scroll.
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setBounds(0, 25, 300, 400);
			scrollPane.add(list); //add the list to the scrollpane

			JButton returnb = new JButton("Return"); // create the return book button
			returnb.setBounds(600, 500, 150, 25);
			returnb.setFont(new Font("Verdana", Font.PLAIN, 16));
			



			//Listener for the button
			returnb.addActionListener(new ActionListener() { //will fire when the button is clicked.

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//calls return book so the book is returned.
				Student.returnBook(selected, Book.getBookByTitle((String) list.getSelectedValue()), Book.getBookList());
				init.window.showStudentList(init.window);//This will update the StudentList to show the changes in the checkoutlist
				bs.dispatchEvent(new WindowEvent(bs, WindowEvent.WINDOW_CLOSING)); //This will close the window.
			}
			});

			
		

		list.addListSelectionListener(new ListSelectionListener() {//when an item from the JList is selcted this will fire.

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String title = (String) list.getSelectedValue(); //gets the title of the book.
				//this is a message that will display after the book is returned
				JLabel text = new JLabel("Return " +title + " from " + selected.firstname+ " " + selected.lastname);
				text.setFont(new Font("Verdana", Font.PLAIN, 16));
				
				text.setBounds(300, 500, 300, 25);
				bs.getContentPane().add(text); //adds the message to window
				updateBoard(bs); //updates
				
			}
			
		});
		bs.getContentPane().add(scrollPane);  //add everything to the window
		bs.getContentPane().add(returnb); //adds it to the window.
		
		
	}
	public void addBookList(BookSelector bs, Student selected) {// this method will be called when you are assigning a book to a student.

		String[] titles = new String[Book.getBookList().size()]; //creates a String[] that will hold he titles of every book.
		for (int i = 0; i < titles.length; i++) {
			titles[i] = Book.getBookList().get(i).title; //loops through every book and adds the title.
		}
			JList<String> list = new JList<String>(titles); // creates the JList with everything in it.
			list.setBounds(0, 25, 300, 400);
			list.setBackground(new Color(245,238, 205));
			list.setFont(new Font("Verdana", Font.PLAIN, 16));
			
			JScrollPane scrollPane = new JScrollPane(list);//scrollpane to allow you to scroll when the box is too big.
			scrollPane.setBounds(0, 25, 300, 400);
			
			bs.getContentPane().add(scrollPane); //adds it to the window.
			

			JButton assign = new JButton("Assign"); //This is the assign book button
			assign.setBounds(600, 500, 150, 25);
			assign.setFont(new Font("Verdana", Font.PLAIN, 16));
			bs.getContentPane().add(assign); //add it to the screen.
			assign.addActionListener(new ActionListener() { //this will trigger when the button is pressed.

			@Override
			public void actionPerformed(ActionEvent arg0) {//will trigger when the button is clicked.
				System.out.println(list.getSelectedValue());
				Student.checkOutBook(selected, Book.getBookByTitle((String) list.getSelectedValue()));
				Window.showStudentList(init.window); //This will update the StudentList to show the changes in the checkoutlist
				bs.dispatchEvent(new WindowEvent(bs, WindowEvent.WINDOW_CLOSING)); //This will close the window.
			}
			
			});
			JLabel text = new JLabel(); // init JLabel for the JList selector
			
			text.setBounds(300, 500, 300, 25);
			bs.getContentPane().add(text); //adds it to window.

			
		

		list.addListSelectionListener(new ListSelectionListener() { //triggers when an item from the list is selected

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String title = (String) list.getSelectedValue(); // gets the title of the book
				text.setText("Assign " +title + " to " + selected.firstname+ " " + selected.lastname); //Text for confirmation
				text.setFont(new Font("Verdana", Font.PLAIN, 16));
				bs.getContentPane().add(text); //adds the text to the window
				updateBoard(bs); //updates.
				
			}
			
		});
	}
	//this method adsd the top bar with the exit button.
	public static void addTopBar(BookSelector bs) {
		//Creates the JMenu and a seperator to keep the back button on the right.
		JMenu back = new JMenu("Back");
		JMenuBar top = new JMenuBar();
		top.add(new JSeparator());
		top.add(back);
		//this event will trigger when the JMenu is selected.
		back.addMenuListener(new MenuListener( ) {

			@Override
			public void menuCanceled(MenuEvent arg0) {
				//not needed but required.
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				//Not needed but required.
				
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				bs.dispatchEvent(new WindowEvent(bs, WindowEvent.WINDOW_CLOSING)); //This will close the window.
			}
			
		});
		bs.setJMenuBar(top); //adds it to the top
		updateBoard(bs); // updates.
	}

}
