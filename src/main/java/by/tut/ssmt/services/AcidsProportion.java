package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.logging.Logger;

public class AcidsProportion implements DataProcessor{
    private static final Logger LOGGER = Logger.getLogger(AcidsProportion.class.getName());

    public String calculate(Map <Integer, Product> products) {
        LOGGER.info("entrySet: " + products.entrySet());

        double totalOmegaThree = 0;
        double totalOmegaSix = 0;
        String proportion = "";
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
        return proportion;
    }

}
