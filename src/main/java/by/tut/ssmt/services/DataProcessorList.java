package by.tut.ssmt.services;

import by.tut.ssmt.repository.entities.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DataProcessorList {
    String calculate (ArrayList<Product> products);
}
