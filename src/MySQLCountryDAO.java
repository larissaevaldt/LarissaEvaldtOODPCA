import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MySQLCountryDAO implements CountryDAO {
	
	DataSource db = DataSource.getInstance();

	@Override
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
				
				
				builder = new Country.CountryBuilder(code, name, continent, surfaceArea, headOfState);
				country = builder.build();
				countries.add(country);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return countries;
	}

	@Override
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
				if(rs.getString(3).equals("")) {
					continent = Continent.valueOf("EMPTY");
				} else {
					continent = Continent.valueOf(rs.getString(3).toUpperCase().replaceAll(" ", "_"));
				}
				surfaceArea = rs.getFloat(4);
				headOfState = rs.getString(5);

				c = new Country.CountryBuilder(code, name, continent, surfaceArea, headOfState).build();
				return c;
			}
			
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Country> findCountryByName(String name) {
		ArrayList<Country> countries = new ArrayList<>();
		String query = "SELECT * FROM country WHERE Name LIKE '%"+name+"%' ;";
		ResultSet rs = db.select(query);
		
		String code = "";
		Continent continent = null;
		float surfaceArea = 0f;
		String headOfState = "";
		
		Country.CountryBuilder builder = null;
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
				
				
				builder = new Country.CountryBuilder(code, name, continent, surfaceArea, headOfState);
				country = builder.build();
				countries.add(country);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return countries;
	}

	@Override
	public boolean saveCountry(Country country) {
		
		String code = country.getCode();
		String name = country.getName();
		Continent continent = country.getContinent();
		float surfaceArea = country.getSurfaceArea();
		String headOfState = country.getHeadOfState();
		
		String query = "INSERT INTO country (Code, Name, Continent, SurfaceArea, HeadOfState) VALUES ('"+code+"', '"+name+"', '"+continent.getName()+"', "+surfaceArea+", '"+headOfState+"');";
		return db.save(query);
	}

}
