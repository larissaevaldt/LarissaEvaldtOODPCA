import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Client {

	private MySQLCountryDAO dao = new MySQLCountryDAO();
	BufferedReader br;

	public Client() {
		welcome();
		menu();
		menuSelect();
	}

	public static void main(String[] args) {
		new Client();
	}

	public void logout() {
		System.out.println("---------------- GOODBYE ------------------");
		System.exit(0);
	}

	public void selectByCode() {
		Country c;
		br = new BufferedReader(new InputStreamReader(System.in));
		String code = "";

		try {
				System.out.println("------- PLEASE ENTER A COUNTRY CODE --------");
				code = br.readLine();
				
				c = dao.findCountryByCode(code);
				System.out.println(c);

		} catch (Exception e) {
			System.out.println("Error reading input");
		}

	}

	public void printAllCountries() {
		ArrayList<Country> countries = dao.getCountries();
		for (int i = 0; i < countries.size(); i++) {
			System.out.println(countries.get(i));
		}
	}

	public void menuSelect() {

		// dao = new MySQLCountryDAO();
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
		} else if (input.equals("2")) {
			selectByCode();
		} else if (input.equals("3")) {
			// searchByName();
		} else if (input.equals("4")) {
			// saveCountry();
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
