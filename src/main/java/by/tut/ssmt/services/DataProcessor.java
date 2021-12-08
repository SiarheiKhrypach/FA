package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;

import java.util.ArrayList;
import java.util.Map;

public interface DataProcessor {
    String calculate (ArrayList<Product> products);
}
