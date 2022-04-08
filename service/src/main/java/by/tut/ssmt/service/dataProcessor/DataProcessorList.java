package by.tut.ssmt.service.dataProcessor;

import by.tut.ssmt.dao.domain.Product;

import java.util.ArrayList;


public interface DataProcessorList {
    String calculate(ArrayList<Product> products);
}
