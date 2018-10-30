package by.tataranovich.tariff.reader;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tariff {
    
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final String VELCOME = "VELCOME";

    private String id;
    private String operator;
    private String name;
    private BigDecimal payroll;
    private Date dateOfCreation;
    private CallPrice callPrice = new CallPrice();
    private Parameters parameters = new Parameters();

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getOperator() {
	return operator;
    }

    public void setOperator(String operator) {
	this.operator = operator;
    }

    public BigDecimal getPayroll() {
	return payroll;
    }

    public void setPayroll(BigDecimal payroll) {
	this.payroll = payroll;
    }

    public Date getDateOfCreation() {
	return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
	this.dateOfCreation = dateOfCreation;
    }

    public CallPrice getCallPrice() {
	return callPrice;
    }

    public void setCallprice(CallPrice callPrice) {
	this.callPrice = callPrice;
    }

    public Parameters getParameters() {
	return parameters;
    }

    public void setParameter(Parameters parameters) {
	this.parameters = parameters;
    }

    @Override
    public String toString() {
	return "Tariff{" + "id='" + id + '\'' + ", operator='" + operator + '\'' + ", name='" + name + '\''
		+ ", payroll=" + payroll + ", dateOfCreation=" + DATE_FORMAT.format(dateOfCreation) + ", callPrice=" + callPrice
		+ ", parameters=" + parameters + '}';
    }

    public static class CallPrice {

	private BigDecimal inNetworkCallPrice;
	private BigDecimal outNetworkCallPrice;
	private BigDecimal toStationaryCallPrice;
	private BigDecimal smsPrice;

	@Override
	public String toString() {
	    return "CallPrice{" + "inNetworkCallPrice=" + inNetworkCallPrice + ", outNetworkCallPrice="
		    + outNetworkCallPrice + ", toStationaryCallPrice=" + toStationaryCallPrice + ", smsPrice="
		    + smsPrice + '}';
	}

	public CallPrice() {
	    super();
	}

	public CallPrice(BigDecimal inNetworkCallPrice, BigDecimal outNetworkCallPrice,
		BigDecimal toStationaryCallPrice, BigDecimal smsPrice) {
	    super();
	    this.inNetworkCallPrice = inNetworkCallPrice;
	    this.outNetworkCallPrice = outNetworkCallPrice;
	    this.toStationaryCallPrice = toStationaryCallPrice;
	    this.smsPrice = smsPrice;
	}

	public BigDecimal getInNetworkCallPrice() {
	    return inNetworkCallPrice;
	}

	public void setInNetworkCallPrice(BigDecimal inNetworkCallPrice) {
	    this.inNetworkCallPrice = inNetworkCallPrice;
	}

	public BigDecimal getOutNetworkCallPrice() {
	    return outNetworkCallPrice;
	}

	public void setOutNetworkCallPrice(BigDecimal outNetworkCallPrice) {
	    this.outNetworkCallPrice = outNetworkCallPrice;
	}

	public BigDecimal getToStationaryCallPrice() {
	    return toStationaryCallPrice;
	}

	public void setToStationaryCallPrice(BigDecimal toStationaryCallPrice) {
	    this.toStationaryCallPrice = toStationaryCallPrice;
	}

	public BigDecimal getSmsPrice() {
	    return smsPrice;
	}

	public void setSmsPrice(BigDecimal smsPrice) {
	    this.smsPrice = smsPrice;
	}

    }

    public static class Parameters {
	public Parameters() {
	    super();
	}

	private int timeTarification;
	private String favouriteNumber;
	private BigDecimal tariffToConnectPrice;

	public Parameters(int timeTarification, String favouriteNumber, BigDecimal tariffToConnectPrice) {
	    super();
	    this.timeTarification = timeTarification;
	    this.favouriteNumber = favouriteNumber;
	    this.tariffToConnectPrice = tariffToConnectPrice;
	}

	public int getTimeTarification() {
	    return timeTarification;
	}

	public void setTimeTarification(int timeTarification) {
	    this.timeTarification = timeTarification;
	}

	public String getFavouriteNumber() {
	    return favouriteNumber;
	}

	public void setFavouriteNumber(String favouriteNumber) {
	    this.favouriteNumber = favouriteNumber;
	}

	public BigDecimal getTariffToConnectPrice() {
	    return tariffToConnectPrice;
	}

	public void setTariffToConnectPrice(BigDecimal tariffToConnectPrice) {
	    this.tariffToConnectPrice = tariffToConnectPrice;
	}

	@Override
	public String toString() {
	    return "Parameters{" + "timeTarification=" + timeTarification + ", favouriteNumber='" + favouriteNumber
		    + '\'' + ", tariffToConnectPrice=" + tariffToConnectPrice + '}';
	}
    }

}