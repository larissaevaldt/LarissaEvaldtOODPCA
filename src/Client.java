import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Client {

	private MySQLCountryDAO dao = new MySQLCountryDAO();
	BufferedReader br;

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
		System.out.println("1. Yes");
		System.out.println("2. No");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		try {
			
			input = br.readLine();
			
			if(input.equals("1") || input.equals("y") || input.equals("Y")) {
				menu();
				menuSelect();
			}if(input.equals("2") || input.equals("n") || input.equals("N")) {
				logout();
				
			}else {
				System.out.println("You didn't enter a valid number");
				System.out.println("Try again");
				anotherOperation();
			}
			
		}catch(Exception e ) {}
	}
	
	public void saveCountry() throws IOException {
		String code = null;
		String name = null;
		String continentString;
		Continent continent = null;
		float surfaceArea;
		String headOfState = null;
		
		Country country;
		
		//GET THE CODE
		try {
			do {
				System.out.println("Enter 3 characteres for the country code:");
				System.out.println("(if you get asked to type again, either the code is already taken or it's too long so try again)");
				code = br.readLine();
				country = dao.findCountryByCode(code);
			}while (code.length()>3 || country != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//GET NAME
		System.out.println("Enter the country name: "); 
		try {
			name = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//GET CONTINENT
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
		//GET SURFACE AREA
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
		Country c = builder.build();
		
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
		br = new BufferedReader(new InputStreamReader(System.in));
		String name = "";

		try {
				System.out.println("------- PLEASE ENTER A COUNTRY NAME --------");
				name = br.readLine();
				
				c = dao.findCountryByName(name);
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				System.out.printf("%-10s%-45s%-22s%-22s%-22s\n","CODE","NAME","CONTINENT", "SURFACE AREA", "HEAD OF STATE");
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
				for(int i=0; i < c.size(); i++) {
					System.out.printf("%-10s%-45s%-22s%-22f%-22s\n",c.get(i).getCode(), c.get(i).getName(), c.get(i).getContinent().getName(), c.get(i).getSurfaceArea(), c.get(i).getHeadOfState());
				}

		} catch (Exception e) {
			System.out.println("Error reading input");
		}

	}

	public void selectByCode() {
		Country c;
		br = new BufferedReader(new InputStreamReader(System.in));
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
	
	/*
	 * The following format code was taken from https://stackoverflow.com/questions/26576909/how-to-format-string-output-so-that-columns-are-evenly-centered
	 * and changed for this particular case, the % denotes that a special formatting follows, the - is for right padding, the number is the amount of spaces
	 * and the letter is the format s for string and f for float
	 */
	public void printAllCountries() {
		ArrayList<Country> countries = dao.getCountries();
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s%-45s%-22s%-22s%-22s\n","CODE","NAME","CONTINENT", "SURFACE AREA", "HEAD OF STATE");
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
		for (int i = 0; i < countries.size(); i++) {
			System.out.printf("%-10s%-45s%-22s%-22f%-22s\n",countries.get(i).getCode(), countries.get(i).getName(), countries.get(i).getContinent().getName(), countries.get(i).getSurfaceArea(), countries.get(i).getHeadOfState());
		}
	}

	public void menuSelect() throws IOException {

		br = new BufferedReader(new InputStreamReader(System.in));
		String input = ""; // move the input variable to the top
		// so we can see it later after the catch

		try {

			boolean valid = false;

			do {
				System.out.println("------- What would you like to do? --------");
				input = br.readLine();

				if (input.matches("[1-5]+")) {
					valid = true;
				} else {
					valid = false;
					System.out.println("Invalid input. Type a number between 1 and 5");
				}

			} while (valid == false);

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
		} else if (input.equals("5")) {
			logout();

		} else {
			System.out.println("You didn't enter a valid number. Try again!");
			menuSelect();
		}
	}

	public void menu() {

		System.out.println("Please select an option from the menu below:");
		System.out.println("Press [1] to LIST ALL COUNTRIES IN THE DATABASE");
		System.out.println("Press [2] to FIND a country BY COUNTRY CODE");
		System.out.println("Press [3] to FIND a country/countries BY NAME");
		System.out.println("Press [4] to SAVE A NEW COUNTRY IN THE DATABASE");
		System.out.println("Press [5] to Exit");

	}

	public void welcome() {
		System.out.println("----------------  WELCOME ------------------");
	}

}
