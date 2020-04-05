import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MySQLCountryDAO implements CountryDAO {
	//connect to the database
	DataSource db = DataSource.getInstance();
	
	// retrieve all of the countries in the database, save them to an ArrayList and return the array
	public ArrayList<Country> getCountries() {
		ArrayList<Country> countries = new ArrayList<>();
		
		String query = "SELECT * FROM country";
		ResultSet rs = db.select(query);
		
		String code = "";
		String name = "";
		Continent continent = null;
		float surfaceArea = 0f;
		String headOfState = "";
		
		Country.CountryBuilder builder = null;
		Country country = null;

		try {
			//loop until the last entry in the database
			while (rs.next()) {
				//assign whatever is in column one to the variable code and so on
				code = rs.getString(1);
				name = rs.getString(2);
				//first checks if the column Continent is empty, because it seems it's possible to add countries without a continent
				if(rs.getString(3).equals("")) {
					continent = Continent.valueOf("EMPTY");
				} else {
					//if there is a continent, for the valueOf method to work we need to pass the exact value of the Enum
					//for example NORTH_AMERICA, so the string read from the database is changed to upper case and the spaces replaced with underscore 
					continent = Continent.valueOf(rs.getString(3).toUpperCase().replaceAll(" ", "_"));
				}
				surfaceArea = rs.getFloat(4);
				headOfState = rs.getString(5);
				
				//create the country
				builder = new Country.CountryBuilder(code, name).setSurfaceArea(surfaceArea).setHeadOfState(headOfState).setContinent(continent);
				country = builder.build();
				//add to the arrayList
				countries.add(country);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return the arrayList
		return countries;
	}

	//receives a country code, search the database if there is a country with that code
	//assign the values in each column to variables, create a country with those values and return it
	public Country findCountryByCode(String code) {
		String query = "SELECT * FROM country WHERE code = '" + code + "';";
		ResultSet rs = db.select(query);

		String name = "";
		Continent continent = null;
		float surfaceArea = 0f;
		String headOfState = "";
		
		Country c = null;

		try {
			if (rs.next()) {
				
				name = rs.getString(2);
				
				if(rs.getString(3).length() == 0) {
					continent = Continent.valueOf("EMPTY");
				} else {
					continent = Continent.valueOf(rs.getString(3).toUpperCase().replaceAll(" ", "_"));
				}
				surfaceArea = rs.getFloat(4);
				headOfState = rs.getString(5);

				c = new Country.CountryBuilder(code, name).setSurfaceArea(surfaceArea).setHeadOfState(headOfState).setContinent(continent).build();
				return c;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//very similar to the method to search by code except it takes a name 
	//and since there may be more than one country with the same name it stores them all in an array and return the array
	public ArrayList<Country> findCountryByName(String name) {
		ArrayList<Country> countries = new ArrayList<>();
		String query = "SELECT * FROM country WHERE Name LIKE '%"+name+"%' ;";
		ResultSet rs = db.select(query);
		
		String code = "";
		Continent continent = null;
		float surfaceArea = 0f;
		String headOfState = "";

		Country country = null;

		try {
			while (rs.next()) {
				code = rs.getString(1);
				name = rs.getString(2);
				if(rs.getString(3).equals("")) {
					continent = Continent.valueOf("EMPTY");
				} else {
					continent = Continent.valueOf(rs.getString(3).toUpperCase().replaceAll(" ", "_"));
				}
				surfaceArea = rs.getFloat(4);
				headOfState = rs.getString(5);
				
				
				country = new Country.CountryBuilder(code, name).setSurfaceArea(surfaceArea).setHeadOfState(headOfState).setContinent(continent).build();
				countries.add(country);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countries;
	}

	//receives a Country object, we get the values for each of the attributes saved in variables 
	//do an INSERT INTO query passing the values taken from the object
	//return boolean true if everything went, false if it didn't
	public boolean saveCountry(Country country) {
		
		String code = country.getCode();
		String name = country.getName();
		Continent continent = country.getContinent();
		float surfaceArea = country.getSurfaceArea();
		String headOfState = country.getHeadOfState();
		
		String query = "INSERT INTO country (Code, Name, Continent, SurfaceArea, HeadOfState) VALUES ('"+code+"', '"+name+"', '"+continent.getValue()+"', "+surfaceArea+", '"+headOfState+"');";
		boolean isSaved = db.save(query);
		return isSaved;
		
	}

}
