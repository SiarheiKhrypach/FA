package by.tut.ssmt.service.dataProcessor;

import by.tut.ssmt.dao.domain.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AcidsProportionListImpl implements DataProcessorList {

    public String calculate(ArrayList <Product> products) {

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
