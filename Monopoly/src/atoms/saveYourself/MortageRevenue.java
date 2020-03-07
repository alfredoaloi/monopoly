package atoms.saveYourself;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("mortageRevenue")
public class MortageRevenue {

	@Param(0)
	private int property;

	@Param(1)
	private int revenue;

	public MortageRevenue() {
	}

	public MortageRevenue(int property, int revenue) {
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
