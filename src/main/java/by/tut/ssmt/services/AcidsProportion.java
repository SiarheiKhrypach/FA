package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Logger;

public class AcidsProportion implements DataProcessor{
    private static final Logger LOGGER = Logger.getLogger(AcidsProportion.class.getName());

    public String calculate(Map <Integer, Product> products) {
//        for (int i = 0; i < products.size(); i++) {
//            totalOmegaThree += products.get(i + 1).getOmegaThree() * products.get(i + 1).getPortion();
//            totalOmegaSix += products.get(i + 1).getOmegaSix() * products.get(i + 1).getPortion();
//        }
        LOGGER.info("entrySet: " + products.entrySet());

        double totalOmegaThree = 0;
        double totalOmegaSix = 0;
        String proportion = "";
//        String formattedProportion;
        for (Map.Entry entry : products.entrySet()) {
            Product product = (Product) entry.getValue();
            totalOmegaThree += product.getOmegaThree() * product.getPortion();
            totalOmegaSix += product.getOmegaSix() * product.getPortion();
            if ((totalOmegaThree!=0)) {
                proportion = String.valueOf(new DecimalFormat("#0.00").format(totalOmegaSix / totalOmegaThree));
            } else {
                proportion = "Division by zero - Your totals for Omega3 is null";
            }
        }
//        String formattedProportion = new DecimalFormat("#0.00").format(proportion);

//        return formattedProportion;
        return proportion;
    }

}
