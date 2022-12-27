package by.tut.ssmt.dao.domain;

public class Product {

    private int productID;
    private String productName;
    private double omegaThree;
    private double omegaSix;
    private int portions;

    public Product(){}

    public Product(String productName, double omegaThree, double omegaSix, int portions) {
        this.productName = productName;
        this.omegaThree = omegaThree;
        this.omegaSix = omegaSix;
        this.portions = portions;
    }

    public Product(int productID, String productName, double omegaThree, double omegaSix, int portions) {
        this.productID = productID;
        this.productName = productName;
        this.omegaThree = omegaThree;
        this.omegaSix = omegaSix;
        this.portions = portions;
    }

    public int getProductId() {
        return productID;
    }

    public void setProductId(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getOmegaThree() {
        return omegaThree;
    }

    public void setOmegaThree(double omegaThree) {
        this.omegaThree = omegaThree;
    }

    public double getOmegaSix() {
        return omegaSix;
    }

    public void setOmegaSix(double omegaSix) {
        this.omegaSix = omegaSix;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productID != product.productID) return false;
        if (Double.compare(product.omegaThree, omegaThree) != 0) return false;
        if (Double.compare(product.omegaSix, omegaSix) != 0) return false;
        if (portions != product.portions) return false;
        return productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (productID ^ (productID >>> 32));
        result = 31 * result + productName.hashCode();
        temp = Double.doubleToLongBits(omegaThree);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(omegaSix);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + portions;
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productID +
                ", productName='" + productName + '\'' +
                ", omegaThree=" + omegaThree +
                ", omegaSix=" + omegaSix +
                ", portions=" + portions +
                '}';
    }
}
