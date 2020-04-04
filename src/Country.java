public class Country {
	private String code;
	private String name;
	private Continent continent;
	private float surfaceArea;
	private String headOfState;
	
	private Country(CountryBuilder builder) {
		this.code = builder.code;
		this.name = builder.name;
		this.continent = builder.continent;
		this.surfaceArea = builder.surfaceArea;
		this.headOfState = builder.headOfState;
	}
	
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public Continent getContinent() {
		return continent;
	}
	public float getSurfaceArea() {
		return surfaceArea;
	}
	public String getHeadOfState() {
		return headOfState;
	}
	
	@Override
	public String toString() {
		return "Code: " + code + "	Name: " + name + "		Continent: " + continent.getName() + "	   Surface Area: " + surfaceArea + "	   Head of State: " + headOfState;
	}	
	
	public static class CountryBuilder {
		private String code;
		private String name;
		private Continent continent;
		private float surfaceArea;
		private String headOfState;
		
		public CountryBuilder(String code, String name, Continent continent, float surfaceArea) {
			this.code = code;
			this.name = name;
			this.continent = continent;
			this.surfaceArea = surfaceArea;
			this.headOfState = "Undefined";
		}
		
		public CountryBuilder setHeadOfState(String headOfState) {
			this.headOfState = headOfState;
			return this;
		}
		
		public Country build() {
			return new Country(this);
		}
		
	}
	
	
	
}
