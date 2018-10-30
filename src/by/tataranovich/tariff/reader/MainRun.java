package by.tataranovich.tariff.reader;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static by.tataranovich.tariff.reader.Tariff.VELCOME;

public class MainRun {

    public static final String PATH_TO_XML = "TariffHandler/tariffs.xml";

    public static void main(String[] args) {

	TariffsSAXBuilder saxBuilder = new TariffsSAXBuilder();
	saxBuilder.buildSetTariffs(PATH_TO_XML);
	System.out.println("SAX");
	print(saxBuilder.getTariffs());
	System.out.println("Changed Tariffs");
	print(changeTariffForVelcom(saxBuilder.getTariffs()));

	TariffsDOMBuilder domBuilder = new TariffsDOMBuilder();
	domBuilder.buildSetTariffs(PATH_TO_XML);
	System.out.println("DOM");
	print(domBuilder.getTariffs());

	TariffsStAXBuilder staxBuilder = new TariffsStAXBuilder();
	staxBuilder.buildSetTariffs(PATH_TO_XML);
	System.out.println("STAX");
	print(staxBuilder.getTariffs());

	System.out.println("MAX OutNetworkCallPrice: " + maxOutNetworkCallPrice(staxBuilder.getTariffs()));
	System.out.println("MIN OutNetworkCallPrice: " + minOutNetworkCallPrice(staxBuilder.getTariffs()));
	System.out.println("OutNetworkCallPrices: " + outNetworkCallPrice(staxBuilder.getTariffs()));
    }

    public static void print(Set<Tariff> tariffs) {
	for (Tariff tariff : tariffs) {
	    System.out.println(tariff);
	}
    }

    public static Set<Tariff> changeTariffForVelcom(Set<Tariff> tariffs) {
	return tariffs.stream().filter(t -> t.getOperator().equals(VELCOME)).map(t -> {
	    t.setPayroll(t.getPayroll().multiply(BigDecimal.valueOf(1.1)));
	    return t;
	}).collect(Collectors.toSet());
    }
    
    public static BigDecimal maxOutNetworkCallPrice(Set<Tariff> tariffs) {
	return tariffs.stream()
		.map(a -> a.getCallPrice().getOutNetworkCallPrice())
		.max(Comparator.naturalOrder()).get();
    }
    
    public static BigDecimal minOutNetworkCallPrice(Set<Tariff> tariffs) {
	return tariffs.stream()
		.map(a -> a.getCallPrice().getOutNetworkCallPrice())
		.min(Comparator.naturalOrder()).get();
    }
    
    public static List<BigDecimal> outNetworkCallPrice(Set<Tariff> tariffs) {
	return tariffs.stream()
		.map(a -> a.getCallPrice().getOutNetworkCallPrice())
		.sorted(Comparator.naturalOrder())
		.collect(Collectors.toList());
    }
}