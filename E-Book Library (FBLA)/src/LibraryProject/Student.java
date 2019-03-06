package LibraryProject;
//Imports
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//CLASS CODE
public class Student { // Every student is created with a name(first and last) ,grade, classVal(grad year), an ArrayList of the books they have, and an email.
	String firstname;
	String lastname;
	int grade;
	int classVal;
	ArrayList<Integer> bookIds; // I used an arrayList because the size of the books they have checked out is unknown.
	String email;
	
	public Student(String firstname, String lastname, int grade, int classVal, ArrayList<Integer> books, String email) { // This is a constructor.  When a new student is made this sets their values.
		this.firstname = firstname;
		this.lastname = lastname;
		this.grade = grade;
		this.classVal = classVal;
		this.bookIds = books;
		this.email = email;
	}
	//these are the methods associated witht the Student class
	public static Book[] getBooksFromAccount(Student student) { // this method will retrive the books that a Student has checked out.  the return type is Book[] and not ArrayList<Book> because the size is a known value.
		Book[] books = new Book[student.bookIds.size()]; //Creates  new array with the size of the ammount of books checked out.
		ArrayList<Book> b = Book.getBookList();
		for (int i =0; i < student.bookIds.size(); i++) { //iteratated to set the book to the book matching the id number.
			books[i] = b.get(Integer.parseInt(String.valueOf(student.bookIds.get(i)))); // I used Interger.parseInt(String.ValueOf()) because.get(i) was returning a long, and this was the only way i could find to cast it.
		}
		
		
		return books; // finally returns the book[]
	}
	// this method is called when a student returns a book.
	public static void returnBook(Student s, Book b, ArrayList<Book> list) {// I bring in the ArrayList from the place where this was called so the memory locations are the same.
		
		Student old = s; // this makes a copy of the student which we will use in the addUpdate() call
		for (int i = 0; i < list.size(); i++) { // this loops over every book
			if (list.get(i).title.equals(b.title)) { // finds a copy with the same title.

				
				int[] a = new int[s.bookIds.size()]; // creates a new int[] with the books that the student has checked out
				for (int j = 0; j < a.length; j++) { // this loops to fill the array
					a[j] = Integer.parseInt(String.valueOf(s.bookIds.get(j))); // I used the integer.parseInt() and STring. valueOf() to cast a long to an int.
				}

				for (int k = 0; k < a.length; k++) { //this loops over every id
					System.out.println(a[k] + " " + i);
					if (a[k] == i) { // if the book matches one that is checked out and the one that is trying to be returned;
						System.out.println("jdkslal");
						s.bookIds.remove(k); // removes that number
						addUpdate(old, s); // updates the file.
						return; // ends the code once one is found.
					}
				}

			
				
			}
		}


		
	}
	public static ArrayList<Student> getStudentList() { // this method will get an updated student list when it is called.
		ArrayList<Student> list = new ArrayList<Student>(); //creates a blank ArrayList.  arraylist was used because size is unknown.
		JSONParser parser = new JSONParser(); // this is the parser that will read through the JSON
		Object objp;
		try {

			objp = parser.parse(new FileReader("resources/students.json")); //opens file resources/students.json and reads.
			JSONArray arr = (JSONArray) objp;	//creates array of the data.  Which is what we want cause the students are stored in a list.
			for (int i = 0; i < arr.size(); i++) { // loops over every item.
				//gets all the values needed from the JSON to make a new student.
				//I stuggled with long issues but .intValue() seemed to work.
				JSONObject temp = (JSONObject) arr.get(i);
				String firstname = (String) temp.get("firstname");
				String lastname = (String) temp.get("lastname");
				int grade = ((Long) temp.get("grade")).intValue();
				int classVal = ((Long) temp.get("classVal")).intValue();
				ArrayList<Integer> books = (ArrayList<Integer>) temp.get("books");
				String email = (String) temp.get("email");
				System.out.println(firstname + " "+lastname + " "+ grade + " "+ classVal);
				list.add(new Student(firstname, lastname, grade, classVal, books, email)); // finally creates a new student and adds them to the list.
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	

		
		return list; //returns the full list.
	}
	//this method is for creating a new student.
	public static void addNewStudent(Student student) { // takes in the parameter of a student.

		try { // this code is writing the students data to the JSON
			JSONParser parser = new JSONParser();
			Object objp;
			try {
				objp = parser.parse(new FileReader("resources/students.json")); // opens JSON file
				JSONArray arr = (JSONArray) objp;			
				
				FileWriter fw = new FileWriter("resources/students.json"); //file writer will write the data.
				JSONObject obj = new JSONObject(); // creates the object
				// adds the data that we will store to the object
				obj.put("firstname", (String)student.firstname);
				obj.put("lastname", (String)student.lastname);
				obj.put("grade", Integer.valueOf(student.grade));
				obj.put("classVal", Integer.valueOf(student.classVal));
				JSONArray books = new JSONArray(); // creates an array to store the int[]which the checked out books are in.
					for(int i = 0; i < student.bookIds.size(); i++) { // loops over every one
					books.add(student.bookIds.get(i));
					}
				//adds everything together.
				obj.put("books", books);
				obj.put("email", student.email);
				arr.add(arr.size(), obj);
				fw.write(arr.toJSONString()); //writes it to JSON
				//closes
				fw.flush();	
				fw.close();
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
	//This method will allow us to get the Student by their firstl and last name.  This is helpful to get the Selcted item of a JList.
	public static Student getStudentByFirstLast(String firstlast) {
		Student student = null; //creates a student that is null.  If no student is found, null will be returned
		String[] firstLastSplit = firstlast.split(" ");  // because firstlast is all one string, we need to split them into two
		ArrayList<Student> list = getStudentList(); // this gets a list of all students.
		for (int i = 0; i < list.size(); i++) {  //loops over every student.
			if (list.get(i).firstname.equals(firstLastSplit[0]) && list.get(i).lastname.equals(firstLastSplit[1])) { // checks for the same first and last name.
				student = list.get(i); // sets student to the matching student
			}
		}
		return student; // returns the student or null
	}
	//This method was for printing students to console.  Is not needed.
//	public static void printStudent (Student student) {
//		System.out.println(student.firstname+ " "+ student.lastname + " "+ student.grade + " "+ student.classVal);
//	}
	
	//This method will  let students check out books
	public static void checkOutBook(Student s, Book b) { //takes in student and book as parameters.
			Student old = s; //creates a copy of the old student so we can find it with addupdate()
			ArrayList<Book> list=  Book.getBookList();
			for (int i = 0; i < list.size(); i ++) {
				if (list.get(i).equals(b)) {
					s.bookIds.add(s.bookIds.size(), i); // adds the new books id to the students list of checked out books.
					addUpdate(old, s); // addUpdate is called to overwrite the old student.
				}
			}

	}
	public static void addUpdate(Student old, Student newS) { // this method will overwrite an old student with a new one.  To "Update " them
		
		try {
			JSONParser parser = new JSONParser();
			Object objp;
			try {
				objp = parser.parse(new FileReader("resources/students.json")); // starts reading the file resouces/students.json
				JSONArray arr = (JSONArray) objp;			 //creates an array of the info	
				for (int i = 0; i < arr.size(); i++) { // loops over array
					JSONObject object = (JSONObject)arr.get(i);
					//gets basic info to find the student
					String first = (String) object.get("firstname");
					String last = (String) object.get("lastname");
					String email= (String) object.get("email");
					// checks to find the student that needs to be replaced.
					if(old.firstname.equals(first) && old.lastname.equals(last)&& old.email.equals(email)) {
						//creates a new JSON object with the new data.
						object = new JSONObject();
						object.put("firstname", newS.firstname);
						object.put("lastname", newS.lastname);
						object.put("grade", newS.grade);
						object.put("classVal", newS.classVal);
						object.put("email", newS.email);
						JSONArray books = new JSONArray();
						for (int j = 0; j < newS.bookIds.size(); j++) {
							books.add(newS.bookIds.get(j));
							
						}
						object.put("books", books);
						//once all the data has been added to our temp object we remove the old one which is at the index of i because the if statement.
						arr.remove(i);
						arr.add(object); // then the new data is added.
						FileWriter fw = new FileWriter("resources/students.json"); // I add a file writer so i can write the JSON
						fw.write(arr.toJSONString()); // Write the JSON
						//closing
						fw.flush();	
						fw.close();
						break;
						

					}
					
					
				}
				return;

				

				
			} catch (ParseException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//This method will get the books that are checked out by a student.
	public static ArrayList<Book>getCheckedOut(Student student) {
		Student real = null; // creates a new student  I need to find the student in the list and I had an issue finding it.
		ArrayList<Student> fullList = getStudentList(); // gets full list
		for (int i = 0; i< fullList.size(); i++) {//loops over every student
			String firstLast = student.firstname+ " "+student.lastname; //gets every first last
			if (student.firstname.equals(fullList.get(i).firstname)) { //find is they match.
				real = fullList.get(i); // sets the real student to the one we found.
				
			}
			
		}
		ArrayList<Integer> ids = real.bookIds; //gets ids of checked out books.
		ArrayList<Book> bookList = new ArrayList<Book>(); //creats a new booklist which will store the books
		for (int j = 0; j < ids.size(); j++) { // loops over the ids
			System.out.println(ids.get(j));
			bookList.add(Book.getBookByID(Integer.parseInt(String.valueOf(ids.get(j))))); //adds the book of corespoding id.
		}
		
		return bookList;
	}
	//ths method will allow you to generate reports
	public static void exportAll() {
		JFileChooser chooser = new JFileChooser(); // this will create the chooser that lets you choose your own directory to export to.
	    chooser.setCurrentDirectory(new java.io.File(".")); //this starts the directory in this program.
	    chooser.setDialogTitle("Please choose a location for the file"); // sets the title
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //this will only show folders.
	    chooser.setAcceptAllFileFilterUsed(false);
	    File directory = null; // creates a File called directory.  It will get assigned a value later.
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // basically checks if a real location is selected
	      directory = chooser.getSelectedFile(); // this will get the directory selected
	    } 
	    FileWriter writer = null; // creates a new file writer to write the data.

	    try {

	        writer = new FileWriter(directory+ "/export.csv"); // makes a exports.csv file where you selected.
	        //sets up the top bar.  This is useful because when you open the csv in excel or good sheets these will be the columns
	        writer.append("LastName,");
	        writer.append("FirstName,");
	        writer.append("Grade,");
	        writer.append("Class,");
	        writer.append("email,");
	        writer.append("Books");
	        writer.append("\n"); // to start the next line or set of data, i use \n (newline excape character.)
	        
	        ArrayList<Student>  list= Student.getStudentList(); // gets a updated list of every student.
	        
	        for (int i = 0; i < Student.getStudentList().size(); i++) { //loops over every book.
	        	//adds all the data needed, seperated by commas.
	        	writer.append(list.get(i).firstname);
	        	writer.append(",");
	        	writer.append(list.get(i).lastname);
	        	writer.append(",");
	        	writer.append(String.valueOf(list.get(i).grade));
	        	writer.append(",");
	        	writer.append(String.valueOf(list.get(i).classVal));
	        	writer.append(",");
	        	writer.append(list.get(i).email);
	        	writer.append(",");
	        	String sb = ""; // this is the string we will be adding everything to.  sb  meant string builder in my brain.  Although its not and actual string builder.
	        	Book[] books = new Book[list.get(i).bookIds.size()]; //gets the books that the specific student has checked out.
	        	for (int j = 0; j < books.length; j++) {
	        		Student s = list.get(i);
	        		int id = Integer.parseInt(String.valueOf(s.bookIds.get(j)));
	        		books[j] = Book.getBookByID(id);
	        		sb += books[j].title + " ("+ books[j].author + "  " + (Arrays.toString(books[j].topics)).replace(',', ' ')+ ") "; // this is the line that it will add.  I replaced the commas with spaces because commas mess up the csv storage.
	        	}
		        writer.append(sb); //adds the string to the writer.
		        writer.append("\n"); // adds \n to end the line.
	        	
	        	
	        }

	        


	     } catch (IOException e) {
	        e.printStackTrace();
	     } finally {
	           try {//closes up
	         writer.flush();
	         writer.close();
	           } catch (IOException e) {
	         e.printStackTrace();
	   }
	   }
	    
	  }
	public static ArrayList<Student> getStudentsbySearch(String term) { //This method allow you to search the students for specific ones.
		ArrayList<Student> found = new ArrayList<Student>(); // this is the ArrayList that will be returned.  It is an ArrayList because the size is unknown.
		ArrayList<Student> list = getStudentList(); // this is the complete, updated list that will be searched.
		if (term.equals("Search") || term.equals("")|| term.equals(null)) { // this checks to see if the user searched anything.  If nothing is in the box, the whole list will be returned;
			found = list;
		} else {
			for (int  i =0; i< list.size(); i++) { //loops through every account.
				if ((list.get(i).firstname+ " "+list.get(i).lastname).toLowerCase().contains(term.toLowerCase())) { // checks to see if anything matches.
					found.add(list.get(i)); //adds the matching students to the list.
				}
			}
		}
		

		
		return found;
	}

	

	
	
	

}
