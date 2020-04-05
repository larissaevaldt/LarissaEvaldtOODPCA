public class Country {
	private String code;
	private String name;
	private Continent continent;
	private float surfaceArea;
	private String headOfState;
	
	//private constructor so the only way to create a Country object is through the Builder Class
	private Country(CountryBuilder builder) {
		this.code = builder.code;
		this.name = builder.name;
		this.continent = builder.continent;
		this.surfaceArea = builder.surfaceArea;
		this.headOfState = builder.headOfState;
	}
	// no setters, only getters 
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
		//inner class has all the attributes of the outer class
		private String code;
		private String name;
		private Continent continent;
		private float surfaceArea;
		private String headOfState;
		
		//pass only the essential attributes in the constructor and assign default values to the optional
		public CountryBuilder(String code, String name) {
			this.code = code;
			this.name = name;
			this.continent = Continent.ASIA;
			this.surfaceArea = 0.0f;
			this.headOfState = null;
		}
		
		//create setter methods for the 3 optional attributes so we can change them to another value
		//return the same builder object after setting 
		public CountryBuilder setContinent(Continent continent) {
			this.continent = continent;
			return this;
		}
		
		public CountryBuilder setSurfaceArea(float surfaceArea) {
			this.surfaceArea = surfaceArea;
			return this;
		}
		
		public CountryBuilder setHeadOfState(String headOfState) {
			this.headOfState = headOfState;
			return this;
		}
		
		//return the object created by the builder class to the private constructor of the outer class
		public Country build() {
			return new Country(this);
		}



		
	}
	
	
	
}
