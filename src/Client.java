import java.util.ArrayList;

public class Client {

	public static void main(String[] args) {
		
		CountryDAO dao = new MySQLCountryDAO();
		
		//if I want to get all of the countries
		ArrayList<Country> countries = dao.getCountries();
		System.out.println(countries);
		
		Country c = dao.findCountryByCode("UK");
		System.out.println(c);
		
		/*
		Country uk = new Country.CountryBuilder("SZ", "Kingdom of Eswatini", Continent.AFRICA, 17364f, "Mswati III").build();
		boolean saved = dao.saveCountry(uk);
		System.out.println(saved);
		*/
		
		ArrayList<Country> search = dao.findCountryByName("united");
		System.out.println(search);
	}

}
