package lt.povilass.currencyrates.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.ToString;

@ToString
@XmlRootElement
public class FxRates
{
    private String xmlns;
    private FxRate[] FxRate;

    @XmlAttribute
    public String getXmlns ()
    {
        return xmlns;
    }

    public void setXmlns (String xmlns)
    {
        this.xmlns = xmlns;
    }

    @XmlElement
    public FxRate[] getFxRate ()
    {
        return FxRate;
    }

    public void setFxRate (FxRate[] FxRate)
    {
        this.FxRate = FxRate;
    }
}
