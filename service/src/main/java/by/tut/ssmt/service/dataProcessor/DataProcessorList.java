package by.tut.ssmt.service.dataProcessor;

import by.tut.ssmt.dao.domain.Product;

import java.util.List;


public interface DataProcessorList {
    String calculate(List<Product> products);
}
