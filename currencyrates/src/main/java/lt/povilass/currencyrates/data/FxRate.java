package lt.povilass.currencyrates.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.ToString;

@ToString
@XmlRootElement
public class FxRate {
	
	private String Dt;
	private CcyAmt[] CcyAmt;
	private String Tp;

	@XmlElement
	public String getDt() {
		return Dt;
	}

	public void setDt(String Dt) {
		this.Dt = Dt;
	}

	@XmlElement
	public CcyAmt[] getCcyAmt() {
		return CcyAmt;
	}

	public void setCcyAmt(CcyAmt[] CcyAmt) {
		this.CcyAmt = CcyAmt;
	}

	@XmlElement
	public String getTp() {
		return Tp;
	}

	public void setTp(String Tp) {
		this.Tp = Tp;
	}
}
