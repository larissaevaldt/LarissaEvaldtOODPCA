import java.util.ArrayList;

public class Client {

	public static void main(String[] args) {
		
		CountryDAO dao = new MySQLCountryDAO();
		
		//if I want to get all of the countries
		ArrayList<Country> countries = dao.getCountries();
		System.out.println(countries);
		
		Country c = dao.findCountryByCode("");
		System.out.println(c);

	}

}
