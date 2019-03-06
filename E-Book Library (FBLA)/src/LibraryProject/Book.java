package LibraryProject;
//Imports
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//CLASS CODE
public class Book {
	//This sets up basic information for a book.
	//Every book will have its own title, author, topics, 
	String title;
	String author;
	String[] topics;
	int bookNum;
	
	public Book(String title, String author, String[] topics) { // this is the constructor.  When you make the book this will store the values accordingly
		this.title = title;
		this.author = author;
		this.topics = topics;
	}

//Both of these methods have been commented out because the program has a GUI and does not need the console.
	
//	public static void printBook(Book b) {
//		System.out.println("Title: " + b.title + "Author: "+b.author+ " Topics: " + Arrays.toString(b.topics));
//	}
//	public static void printBooks(ArrayList<Book> b) {
//		b.forEach(a -> printBook	(a));
//	}
	public static ArrayList<Book> getBookList() { //This method will return an updated list of every book.  I used an ArrayList because you may not know the size of the book list.
		ArrayList<Book> bookList = new ArrayList<Book>(); // creates a new list
			JSONParser parser = new JSONParser();  //creates a new parser to read the JSON in which the books are stored.
			Object objp; //temp object init
				try {
					objp = parser.parse(new FileReader("resources/books.json")); // this will start reading the JSON at resources/books.json
					JSONArray arr = (JSONArray) objp; // this will turn it into an array which is needed because there are more than one book.
					for (int i = 0; i < arr.size();i++) { // this will iterate over every item and then create a book based on the values.
						JSONObject singObj = (JSONObject) arr.get(i);
						String title = (String) singObj.get("title"); // gets the value based on key title
						String author = (String) singObj.get("author"); // gets the value based on key author
						JSONArray a = (JSONArray) singObj.get("topics"); //gets the array values based on key topics
						String[] topics = new String[a.size()];  //iterates over every topics to make one array of topiccs
						for (int j = 0; j < topics.length; j++) {
							topics[j] = (String) a.get(j);
						}
						Book work = new Book(title, author, topics); // creates new book
 						bookList.add(work); // adds it to list of books.
					}
					
		            
				} catch (IOException | ParseException e) {//this checks for errors in reading the JSON>
					e.printStackTrace();
				}

		return bookList; // finally the list of books is returned.
		
	}
	public static Book getBookByTitle(String t) { // this method allows you to search for book by title.  I use this to match book to title from JLists.
		Book ret = null; // init new book to null so if nothing is found null is returned
		ArrayList<Book> books = getBookList(); //gets a fresh, updated bookList
		for (int i = 0; i < books.size();i++) { //loops over every item of the book list.
			if (books.get(i).title.equals(t)) { // checks to see if the titles are the same.
				System.out.println(books.get(i).title + " "+ t + " " + i);
				ret = books.get(i); // sets the book we return to the book found
				break; //breaks the loop to save time
			}
		}
		return ret; //returns the book
		
		
	}
	public static Book getBookByID(int id) { //this will return a book based on its id in the list
		ArrayList<Book> list = getBookList(); //gets new list
		Book book = list.get(id); //retrieves the book

		
		return book; // returns the book
	}
	@SuppressWarnings("unchecked")
	public static void writeBook(Book book) { // this will write the booklist to the JSON file for storate.

		try {
			JSONParser parser = new JSONParser();  //creates new reader.
			Object objp;  //temp opbject
			try {
				objp = parser.parse(new FileReader("resources/books.json"));//opens reader / writer in resources/ books.json
				JSONArray arr = (JSONArray) objp;  //turns it into an array so we can append the the end of it.
				
				FileWriter fw = new FileWriter("resources/books.json");  // new file writer so we can write to file.
				//creates json object to write.
				JSONObject obj = new JSONObject();
				obj.put("title", book.title);
				obj.put("author", book.author);
				JSONArray arrt = new JSONArray();
				for (int i = 0; i < book.topics.length; i++) { //loops over topics to make an array of the topics in the JSON.
					arrt.add(i, book.topics[i]);
				}
				obj.put("topics", arrt);
				arr.add(arr.size(), obj);// this will store the id number of the book.  Because the size will always increase this way is reliable.
				fw.write(arr.toJSONString()); // writes the JSON to the file
				//closes the writer.
				fw.flush();	
				fw.close();
				
			} catch (ParseException e) { //catches and reading errors.
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) { //catches any opening errors.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public static ArrayList<Book> getBooksFromSearch(String str) {// This will return books that match search criteria.
		ArrayList<Book> newSearch = new ArrayList<Book>(); //init new book ArrayList cause size is unknown.
		ArrayList<Book> fullBooks = getBookList(); //gets full book list
		if (str.equals("Search") || str.equals(null)) { //if nothing is typed in or nothing is changed in the search term.  It will return all books.
			newSearch = fullBooks;
		}
		for (Book book : fullBooks) { //for each book: if any match, add that book to list.
			if (book.title.toLowerCase().contains(str.toLowerCase()) || book.author.toLowerCase().contains(str.toLowerCase())) {
				newSearch.add(book);
			}
			
		}
		return newSearch; //returns list.
		
	}
	public boolean equals(Book other){ // I made this method to override the other equals.  This fixed and error I had.   It goes deaper into the equals.  It checks for the same string, not only the same memory location.  Which kept returning false.
	    boolean result;
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } // end if
	    else{
	        result = title.equals(other.title) &&  author.equals(other.author);
	    } // end else

	    return result;
	} // end equals

}
