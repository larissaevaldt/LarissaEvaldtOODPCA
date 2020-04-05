
public enum Continent {
	//there is a value for empty, because people managed to add countries without continent and the program was crashing
	ASIA("Asia"), 
	EUROPE("Europe"), 
	NORTH_AMERICA("North America"), 
	AFRICA("Africa"), 
	OCEANIA("Oceania"), 
	ANTARCTICA("Antarctica"), 
	SOUTH_AMERICA("South America"),
	EMPTY(""); 
	
	private String value;
	
	Continent(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
