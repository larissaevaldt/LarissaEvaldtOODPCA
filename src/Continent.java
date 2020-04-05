
public enum Continent {
	
	ASIA("Asia"), 
	EUROPE("Europe"), 
	NORTH_AMERICA("North America"), 
	AFRICA("Africa"), 
	OCEANIA("Oceania"), 
	ANTARCTICA("Antarctica"), 
	SOUTH_AMERICA("South America"),
	EMPTY(""); 
	//there is a value for empty, because people managed to add countries without continent and the program was crashing
	
	
	private String value;
	
	Continent(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public Continent getEnum(String continentString) {
		
		for(Continent c : Continent.values()) {
			if(c.getValue().equals(continentString)) {
				return c;
			}
		}
		return null;
	}
}
