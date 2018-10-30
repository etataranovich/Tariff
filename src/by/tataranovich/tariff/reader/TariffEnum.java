package by.tataranovich.tariff.reader;

public enum TariffEnum {
    TARIFFS("tariffs"),
    ID("id"),
    OPERATOR("operator"),
    TARIFF("tariff"),
    NAME("name"),
    PAYROLL("payroll"),
    DATEOFCREATION("dateOfCreation"),
    
    INNETWORKCALLPRICE("inNetworkCallPrice"),
    OUTNETWORKCALLPRICE("outNetworkCallPrice"),
    TOSTATIONARYCALLPRICE("toStationaryCallPrice"),
    SMSPRICE("smsPrice"),
    
    CALLPRICE("callPrice"),
    
    FAVOURITENUMBER("favouriteNumber"),
    TIMETARIFICATION("timeTarification"),
    TARIFFTOCONNECTPRICE("tariffToConnectPrice"),
    
    PARAMETERS("parameters");
    

    private String value;

    private TariffEnum(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
