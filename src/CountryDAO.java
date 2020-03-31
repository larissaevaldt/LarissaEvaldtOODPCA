import java.util.ArrayList;

public interface CountryDAO {
	public ArrayList<Country> getCountries();
	public Country findCountryByCode(String code);
	public ArrayList<Country> findCountryByName(String name);
	public boolean saveCountry(Country country);
}
