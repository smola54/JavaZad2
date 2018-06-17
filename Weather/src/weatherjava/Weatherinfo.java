package weatherjava;


public class Weatherinfo {
	
	private String city, country;
	private int temperature;
	private int id;
	
	public Weatherinfo( String city, String country, int temperature ,int id) {
		this.city = city;
		this.country = country;
		this.temperature = temperature;
		this.id = id;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Weatherinfo [city=" + city + ", country=" + country + ", temperature=" + temperature +  "]";
	}

}
