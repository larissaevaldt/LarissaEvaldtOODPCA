import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Client {

	private MySQLCountryDAO dao = new MySQLCountryDAO();

	public Client() throws IOException {
		welcome();
		menu();
		menuSelect();
	}

	public static void main(String[] args) throws IOException {
		new Client();
	}
	
	//prints a goodbye message and closes the connection
	public void logout() {
		System.out.println("------------------- GOODBYE ---------------------");
		dao.db.close();
		System.exit(0);
	}
	//method asks user if want to do another operation and validates the input only allowing the user to enter 1 or 2 
	//if they want then the menu is presented again, if not then quit program
	public void anotherOperation() {
		System.out.println("Would you like to do another operation?");
		System.out.println("Type [1] for YES");
		System.out.println("Type [2] for NO");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		try {
			
			do {
				input = br.readLine();
				//send user off
				if(input.equals("1")) {
					menu();
					menuSelect();
				} else if(input.equals("2")) {
					logout();
				} else {
					System.out.println("Invalid input. Type [1] for YES or [2] for NO");
				}
			} while(!isNumberOneOrTwo(input));
			
		}catch(Exception e ) {}
	}
	
	//method calls the create country method first and then saves in the database through the DAO method 
	//also prints a message for the user to know if the country was added or not
	public void saveCountry() throws IOException {
		
		Country c = createCountry();
		
		boolean saved = dao.saveCountry(c);
		if(saved) {
			System.out.println("Country successly added");
		} else {
			System.out.println("Something wen't wrong and the country was not added. Try again");
		}
	}
	
	//asks user for a name and then call the findCountryByName from MySQLCountryDAO class
	//saves the countries in an array list and if it finds one or more countries it prints them nicely formatted
	//otherwise prints a message saying there is no country in the database
	public void selectByName() {
		ArrayList<Country> c = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = "";

		try {
				System.out.println("---------- PLEASE ENTER A COUNTRY NAME -----------");
				name = br.readLine();
				
				c = dao.findCountryByName(name);
				//check if there is at least one country in the array list 
				//if the array is empty it means there is no country with that name
				if(c.size() >= 1) {
					//if there is 1 or more country, we print them
					printHeader();
					for(int i=0; i < c.size(); i++) {
						System.out.printf("%-10s%-45s%-22s%-22f%-22s\n",c.get(i).getCode(), c.get(i).getName(), c.get(i).getContinent().getValue(), c.get(i).getSurfaceArea(), c.get(i).getHeadOfState());
					}
				} else {
					//if there is none, print a message
					System.out.println("SORRY, NO COUNTRY IN THE DATABASE WITH THAT NAME");
				}
				
		} catch (Exception e) {
			System.out.println("Error reading input");
		}

	}
	
	//method asks the user for all the attributes, for the continent, surfaceArea and headOfState it asks if the user wants to enter or go with the default value since it's optional
	//validate each input, and then create a country through the CountryBuilder class and return it
	public Country createCountry() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		Country.CountryBuilder builder;
		Country country;
		String code = "";
		String name = "";
		String continentString = "";
		Continent continent = null;
		float surfaceArea = 0;
		String headOfState = "";
		String input;
		String surfaceAreaString;
		
		
		//ASK USER FOR THE COUNTRY CODE
		try {
			//keep asking until user enter a valid code (less than 3 characters)
			do {
				System.out.println("Enter a maximum 3 character country code:");
				code = br.readLine();
				//search if there is already a country with that code in the database, because if there is we  can't use that code
				country = dao.findCountryByCode(code);
				//print message so client know what's wrong
				if(!isSizeThree(code)) {
					System.out.println("Code is too long. Please enter maximum 3 characteres");
				}
				if(country != null) {
					System.out.println("This code is already used. Try another one");
				}
			}while (!isSizeThree(code) || country != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//GET NAME
		System.out.println("Enter the country name: "); 
		try {
			//keep asking until a valid name is entered (only a-z characters and no consecutive white spaces)
			do {
				name = br.readLine();
				if(!isValidName(name)) {
					System.out.println("Invalid name. Try again");
				}
			} while (!isValidName(name));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ASK USER IF THEY WANT TO ENTER A CONTINENT OR GO WITH THE DEFAULT
		//do while loop to keep asking for a continent until the client enters a valid one
		do {
			System.out.println("Would you like to enter a continent or just go with the default 'Asia'?");
			System.out.println("Enter [1] if you want to assign a value");
			System.out.println("Enter [2] if you want to go with the default");
			
			input = br.readLine();
			
			if(input.equals("1")) {
				do {
					System.out.println("Enter the continent: (Asia, Europe, North America, Africa, Oceania, Antarctica or South America)"); 
					try {
						continentString = br.readLine();
						//change it to upper case, just for it to work even in case someone types asia, Asia, asiA, for example and cut white spaces
						continentString = continentString.toUpperCase().trim();
						//assign the right continent according to what the client entered
						switch(continentString) {
							case("ASIA"):
								continent = Continent.ASIA;
								break;
							case("EUROPE"):
								continent = Continent.EUROPE;
								break;
							case("NORTH AMERICA"):
								continent = Continent.NORTH_AMERICA;
								break;
							case("AFRICA"):
								continent = Continent.AFRICA;
								break;
							case("OCEANIA"):
								continent = Continent.OCEANIA;
								break;
							case("ANTARCTICA"):
								continent = Continent.ANTARCTICA;
								break;
							case("SOUTH AMERICA"):
								continent = Continent.SOUTH_AMERICA;
								break;
							default:
								System.out.println("Invalid Continent. Try again");
						} 
					} catch (IOException e) {
						e.printStackTrace();
					}
				} while (!isValidContinent(continentString));
			} else if (input.equals("2")) {
				//assign the default value
				continent = Continent.ASIA;
			} else {
				System.out.println("Invalid input. Try again");
			}
			
		} while(!isNumberOneOrTwo(input));
		
		

		 //ASK USER FOR A SURFACE AREA, IF THEY WANT TO ADD ONE OR GO WITH THE DEFAULT
		// do while loop to keep asking until a valid number is entered
		do {
			System.out.println("Would you like to enter a surface area or just go with the default of 0.0?");
			System.out.println("Enter [1] if you want to assign a value");
			System.out.println("Enter [2] if you want to go with the default");
			
			input = br.readLine();
			
			if(!isNumberOneOrTwo(input)) {
				System.out.println("Invalid input. Try again");
			}
			//switch to first check if its a valid float number and then convert the string to float if user enter 1
			//and assign the default value if user enters 2
			switch(input) {
				case("1"):
					do {
						System.out.println("Please enter a number for the surface area:"); 
						surfaceAreaString = br.readLine();
						if(isFloat(surfaceAreaString)) {
							surfaceArea = Float.parseFloat(surfaceAreaString);
						} else {
							System.out.println("Invalid number. Try again");
						}
						
					} while (!isFloat(surfaceAreaString));
				case("2"):
					surfaceArea = 0.0f;
			}
		}while(!isNumberOneOrTwo(input));
		
		
		//ASK USER FOR A HEAD OF STATE
		System.out.println("Enter the Head of State: (Just press enter to save without one)"); 
		try {
			headOfState = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//FIRST CREATE A BUILDER WITHOUT HEAD OF STATE THEN CHECKS IF THE HEAD OF STATE STRING IS EMPTY
		//IF IT'S NOT EMPTY THE USER ENTERED A HEAD OF STATE SO WE NEED TO CHANGE 
		builder = new Country.CountryBuilder(code, name).setSurfaceArea(surfaceArea).setContinent(continent);
		if(!isEmptyString(headOfState)) {
			builder.setHeadOfState(headOfState);
		}
		//CREATE A COUNTRY AND RETURN IT
		return country = builder.build();
	}
	
	//method asks user to enter a code and uses the findCountryByCode method from MySQLCountryDAO class
	//Prints the country if it finds and a message if it doesn't
	public void selectByCode() {
		Country c;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = "";

		try {
				System.out.println("---------- PLEASE ENTER A COUNTRY CODE -----------");
				code = br.readLine();
				c = dao.findCountryByCode(code);
				if (c == null) {
					System.out.println("There is no country in the database with that code");
				} 
				System.out.println("--------------------------------------------------------------------------------------------------------------------------");
				System.out.println(c);
				System.out.println("--------------------------------------------------------------------------------------------------------------------------");

		} catch (Exception e) {
			System.out.println("Error reading input");
		}

	}
	
	
	public void printAllCountries() {
		//call the MySQLCountryDAO class method getCountries, save it to an ArrayList
		ArrayList<Country> countries = dao.getCountries();
		//print a header to look nicer
		printHeader();
		//loop through the countries printing them in a nice formatted way
		for (int i = 0; i < countries.size(); i++) {
			System.out.printf("%-10s%-45s%-22s%-22f%-22s\n",countries.get(i).getCode(), countries.get(i).getName(), countries.get(i).getContinent().getValue(), countries.get(i).getSurfaceArea(), countries.get(i).getHeadOfState());
		}
	}
	
	//ask user to enter a number according to what they want to do, validate if entered a valid number (1-5) and execute other methods according to the number entered
	public void menuSelect() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = ""; // move the input variable to the top
		// so we can see it later after the catch

		try {

			do {
				System.out.println("----------- What would you like to do? -----------");
				input = br.readLine();

				if (!isNumberBetweenOneAndFive(input)) {
					System.out.println("Invalid input. Type a number between 1 and 5");
				} 

			} while (!isNumberBetweenOneAndFive(input));

		} catch (Exception e) {
			System.out.println("Error reading input");
		}

		// send user off
		if (input.equals("1")) {
			printAllCountries();
			anotherOperation();
		} else if (input.equals("2")) {
			selectByCode();
			anotherOperation();
		} else if (input.equals("3")) {
			selectByName();
			anotherOperation();
		} else if (input.equals("4")) {
			saveCountry();
			anotherOperation();
		} else {
			logout();
		} 
	}
	
	//method prints the instructions for the user to enter a number according to what they want to do
	public void menu() {

		System.out.println("Please select an option from the menu below:");
		System.out.println("Enter [1] to LIST ALL COUNTRIES IN THE DATABASE");
		System.out.println("enter [2] to FIND a country BY COUNTRY CODE");
		System.out.println("Enter [3] to FIND a country/countries BY NAME");
		System.out.println("Enter [4] to SAVE A NEW COUNTRY IN THE DATABASE");
		System.out.println("Enter [5] to QUIT");

	}
	
	//simple welcome message
	public void welcome() {
		System.out.println("-------------------  WELCOME ---------------------");
	}
	
	/*
	 * Simply prints a header on top of the results from the database so it looks better
	 * The following format code was taken from https://stackoverflow.com/questions/26576909/how-to-format-string-output-so-that-columns-are-evenly-centered
	 * and changed for this particular case, the % denotes that a special formatting follows, the - is for right padding, without - it would pad to the left, the number is the amount of spaces
	 * and the letter is the data format, s for string and f for float, \n to go to the next line. The same logic is used in the loop to print the actual content in the method print all countries and search by name as well
	 */
	public void printHeader() {
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s%-45s%-22s%-22s%-22s\n","CODE","NAME","CONTINENT", "SURFACE AREA", "HEAD OF STATE");
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
	}
	
	//VALIDATIONS
	public boolean isNumberBetweenOneAndFive(String input) {
		return input.matches("[1-5]+");
	}
	
	public boolean isNumberOneOrTwo(String input) {
		return input.matches("[1-2]+");
	}

	public boolean isSizeThree(String input) {
		return input.length() <= 3;
	}
	
	public boolean isValidName(String input) {
		//name can start or end only with a letter
		//cannot contain consecutive spaces
		return input.matches("^([a-zA-Z]+\\s)*[a-zA-Z]+$");
	}
	
	public boolean isEmptyString(String input) {
		return input.equals("");
	}
	
	public boolean isValidContinent(String input) {
		input = input.toUpperCase().trim();
		if(input.equals("ASIA") || input.equals("EUROPE") || input.equals("NORTH AMERICA") || input.equals("AFRICA") || input.equals("OCEANIA") || input.equals("ANTARCTICA") || input.equals("SOUTH AMERICA")) {
			return true;
		} 
		return false;	
	}
	
	//method checks if the string entered can be converted to a float
	//regex expression found on this link https://regexr.com/35aig
	public boolean isFloat(String input) {
		return input.matches("^[0-9]*\\.?,?[0-9]+$");
	}
	
}
		
	


