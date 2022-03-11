package by.tut.ssmt.service.dataProcessors;

import by.tut.ssmt.dao.repository.entities.Product;

import java.util.ArrayList;


public interface DataProcessorList {
    String calculate(ArrayList<Product> products);
}
