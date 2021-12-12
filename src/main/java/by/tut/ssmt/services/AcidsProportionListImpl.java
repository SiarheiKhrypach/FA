package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AcidsProportionListImpl implements DataProcessorList {
    private static final Logger LOGGER = Logger.getLogger(AcidsProportionListImpl.class.getName());

    public String calculate(ArrayList <Product> products) {
        LOGGER.info("Products: " + products);

        double totalOmegaThree = 0;
        double totalOmegaSix = 0;
        String proportion = "";
        for (Product product : products) {
            totalOmegaThree += product.getOmegaThree() * product.getPortion();
            totalOmegaSix += product.getOmegaSix() * product.getPortion();
            if ((totalOmegaThree != 0)) {
                proportion = String.valueOf(new DecimalFormat("#0.00").format(totalOmegaSix / totalOmegaThree));
            } else {
                proportion = "Division by zero - Your totals for Omega3 is null";
            }
        }
        return proportion;
    }


}
