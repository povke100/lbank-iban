package lt.povilass.currencyrates.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.ToString;

@ToString
@XmlRootElement
public class CcyAmt {
	
	private String Ccy;
	private Double Amt;

	@XmlElement
	public String getCcy() {
		return Ccy;
	}

	public void setCcy(String Ccy) {
		this.Ccy = Ccy;
	}

	@XmlElement
	public Double getAmt() {
		return Amt;
	}

	public void setAmt(Double Amt) {
		this.Amt = Amt;
	}

}
