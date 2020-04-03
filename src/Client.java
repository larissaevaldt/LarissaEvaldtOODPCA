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

	public void logout() {
		System.out.println("---------------- GOODBYE ------------------");
		System.exit(0);
	}
	
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
					System.out.println("Invalid input. Type [1] for yes OR [2] for no");
				}
			
			} while(!isNumberBetweenOneAndTwo(input));
			
		}catch(Exception e ) {}
	}
	
	public void saveCountry() throws IOException {
		
		Country c = createCountry();
		
		boolean saved = dao.saveCountry(c);
		if(saved) {
			System.out.println("Country successly added");
			anotherOperation();
		} else {
			System.out.println("Something wen't wrong and the country was not added. Try again");
			anotherOperation();
		}
	}
	
	public void selectByName() {
		ArrayList<Country> c = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = "";

		try {
				System.out.println("------- PLEASE ENTER A COUNTRY NAME --------");
				name = br.readLine();
				
				c = dao.findCountryByName(name);
				//check if there is at least one country in the array list 
				//if the array is empty it means there is no country with that name
				if(c.size() >= 1) {
					//if there is 1 or more country, we print them
					printHeader();
					for(int i=0; i < c.size(); i++) {
						System.out.printf("%-10s%-45s%-22s%-22f%-22s\n",c.get(i).getCode(), c.get(i).getName(), c.get(i).getContinent().getName(), c.get(i).getSurfaceArea(), c.get(i).getHeadOfState());
					}
				} else {
					//if there is none, print a message
					System.out.println("SORRY, NO COUNTRY IN THE DATABASE WITH THAT NAME");
				}
				
		} catch (Exception e) {
			System.out.println("Error reading input");
		}

	}
	
	public Country createCountry() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		String code = null;
		String name = null;
		String continentString;
		Continent continent = null;
		float surfaceArea;
		String headOfState = null;
		Country country;
		
		//GET THE COUNTRY CODE
		try {
			do {
				System.out.println("Enter 3 characteres for the country code:");
				code = br.readLine();
				//search if there is already a country with that code in the database, because if there is we  can't use that code
				country = dao.findCountryByCode(code);
				//so print message so client know what's wrong
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
			do {
				name = br.readLine();
				if(!isValidName(name)) {
					System.out.println("Invalid name. Try again");
				}
			} while (!isValidName(name));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//GET THE CONTINENT from the client, change it to upper case, just for it to work even in case someone types asia, Asia, asiA, for example and cut white spaces
		//switch case assigns the right continent according to what the client entered and it's inside of a while loop to keep asking for a continent until the client enters a valid one
		boolean valid = false;
		while(valid == false) {
			System.out.println("Enter the continent: (Asia, Europe, North America, Africa, Oceania, Antarctica or South America)"); 
			try {
				continentString = br.readLine();
				String upperCase = continentString.toUpperCase().trim();
				switch(upperCase) {
					case("ASIA"):
						continent = Continent.ASIA;
						valid = true;
						break;
					case("EUROPE"):
						continent = Continent.EUROPE;
						valid = true;
						break;
					case("NORTH AMERICA"):
						continent = Continent.NORTH_AMERICA;
						valid = true;
						break;
					case("AFRICA"):
						continent = Continent.AFRICA;
						valid = true;
						break;
					case("OCEANIA"):
						continent = Continent.OCEANIA;
						valid = true;
						break;
					case("ANTARCTICA"):
						continent = Continent.ANTARCTICA;
						valid = true;
						break;
					case("SOUTH AMERICA"):
						continent = Continent.SOUTH_AMERICA;
						valid = true;
						break;
					default:
						System.out.println("Invalid Continent. Try again");
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//ASK FOR A SURFACE AREA
		surfaceArea = 0;
		while (surfaceArea == 0) {
			System.out.println("Enter a number for the Surface Area:"); 
		    try {
		    	surfaceArea = Float.parseFloat(br.readLine());
		    } catch (NumberFormatException e) {
		    	 System.out.println("Not a valid surface area. Try entering a number");
		    	 surfaceArea = 0;
			}
		}
		
		//GET HEAD OF STATE
		System.out.println("Enter the Head of State: "); 
		try {
			headOfState = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Country.CountryBuilder builder = new Country.CountryBuilder(code, name, continent, surfaceArea, headOfState);
		return country = builder.build();
	}

	public void selectByCode() {
		Country c;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = "";

		try {
				System.out.println("------- PLEASE ENTER A COUNTRY CODE --------");
				code = br.readLine();
				
				c = dao.findCountryByCode(code);
				if (c == null) {
					System.out.println("There is no country in the database with that code");
				} 
				
				System.out.println(c);

		} catch (Exception e) {
			System.out.println("Error reading input");
		}

	}
	
	
	public void printAllCountries() {
		ArrayList<Country> countries = dao.getCountries();
		printHeader();
		for (int i = 0; i < countries.size(); i++) {
			System.out.printf("%-10s%-45s%-22s%-22f%-22s\n",countries.get(i).getCode(), countries.get(i).getName(), countries.get(i).getContinent().getName(), countries.get(i).getSurfaceArea(), countries.get(i).getHeadOfState());
		}
	}

	public void menuSelect() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = ""; // move the input variable to the top
		// so we can see it later after the catch

		try {

			do {
				System.out.println("------- What would you like to do? --------");
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
		} else {
			logout();
		} 
	}

	public void menu() {

		System.out.println("Please select an option from the menu below:");
		System.out.println("Enter [1] to LIST ALL COUNTRIES IN THE DATABASE");
		System.out.println("enter [2] to FIND a country BY COUNTRY CODE");
		System.out.println("Enter [3] to FIND a country/countries BY NAME");
		System.out.println("Enter [4] to SAVE A NEW COUNTRY IN THE DATABASE");
		System.out.println("Enter [5] to Exit");

	}

	public void welcome() {
		System.out.println("-------------------  WELCOME ---------------------");
	}
	
	/*
	 * The following format code was taken from https://stackoverflow.com/questions/26576909/how-to-format-string-output-so-that-columns-are-evenly-centered
	 * and changed for this particular case, the % denotes that a special formatting follows, the - is for right padding, without - it would pad to the left, the number is the amount of spaces
	 * and the letter is the data format, s for string and f for float, \n to go to the next line. The same logic is used in the loop to print the actual content in the method print all countries and search by name as well
	 */
	public void printHeader() {
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s%-45s%-22s%-22s%-22s\n","CODE","NAME","CONTINENT", "SURFACE AREA", "HEAD OF STATE");
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
	}
	
	//validations
	public boolean isNumberBetweenOneAndFive(String input) {
		return input.matches("[1-5]+");
	}
	
	public boolean isNumberBetweenOneAndTwo(String input) {
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
	
}
		
	


