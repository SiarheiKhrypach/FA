package by.tut.ssmt.dao.repository.entities;

public class Product {

    private int productID;
    private String productName;
    private double omegaThree;
    private double omegaSix;
    private int portion;

    public Product(){}

    public Product(String productName, double omegaThree, double omegaSix, int portion) {
        this.productName = productName;
        this.omegaThree = omegaThree;
        this.omegaSix = omegaSix;
        this.portion = portion;
    }

    public Product(int productID, String productName, double omegaThree, double omegaSix, int portion) {
        this.productID = productID;
        this.productName = productName;
        this.omegaThree = omegaThree;
        this.omegaSix = omegaSix;
        this.portion = portion;
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

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productID != product.productID) return false;
        if (Double.compare(product.omegaThree, omegaThree) != 0) return false;
        if (Double.compare(product.omegaSix, omegaSix) != 0) return false;
        if (portion != product.portion) return false;
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
        result = 31 * result + portion;
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productID +
                ", productName='" + productName + '\'' +
                ", omegaThree=" + omegaThree +
                ", omegaSix=" + omegaSix +
                ", portion=" + portion +
                '}';
    }
}
