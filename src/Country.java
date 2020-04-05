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
		return "Code: " + code + "	Name: " + name + "		Continent: " + continent.getValue() + "	   Surface Area: " + surfaceArea + "	   Head of State: " + headOfState;
	}	
	
	public static class CountryBuilder {
		private String code;
		private String name;
		private Continent continent;
		private float surfaceArea;
		private String headOfState;
		
		public CountryBuilder(String code, String name) {
			this.code = code;
			this.name = name;
			this.continent = Continent.ASIA;
			this.surfaceArea = 0.0f;
			this.headOfState = null;
		}
		
		
		public CountryBuilder setSurfaceArea(float surfaceArea) {
			this.surfaceArea = surfaceArea;
			return this;
		}
		
		public CountryBuilder setHeadOfState(String headOfState) {
			this.headOfState = headOfState;
			return this;
		}
		
		public Country build() {
			return new Country(this);
		}

		public CountryBuilder setContinent(Continent continent) {
			// TODO Auto-generated method stub
			this.continent = continent;
			return this;
		}

		
	}
	
	
	
}
