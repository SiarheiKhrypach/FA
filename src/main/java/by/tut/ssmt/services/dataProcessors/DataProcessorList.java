package by.tut.ssmt.services.dataProcessors;

import by.tut.ssmt.repository.entities.Product;

import java.util.ArrayList;

public interface DataProcessorList {
    String calculate (ArrayList<Product> products);
}
