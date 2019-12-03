package lt.povilass.currencyrates.data;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class CcyAmt
{
    private String Ccy; //Currency code
    private Double Amt; //Amount
}
