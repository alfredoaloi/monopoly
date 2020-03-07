package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("sellHouseRevenue")
public class SellHouseRevenue {

	@Param(0)
	private int property;

	@Param(1)
	private int revenue;

	public SellHouseRevenue() {
	}

	public SellHouseRevenue(int property, int revenue) {
		super();
		this.property = property;
		this.revenue = revenue;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

}
