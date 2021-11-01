package by.tut.ssmt.repository.entities;

public class Product {

    private long id;
    private String productName;
    private double omegaThree;
    private double omegaSix;
    private int portion;

    public Product(String productName, double omegaThree, double omegaSix, int portion) {
        this.productName = productName;
        this.omegaThree = omegaThree;
        this.omegaSix = omegaSix;
        this.portion = portion;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        if (id != product.id) return false;
        if (Double.compare(product.omegaThree, omegaThree) != 0) return false;
        if (Double.compare(product.omegaSix, omegaSix) != 0) return false;
        if (portion != product.portion) return false;
        return productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
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
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", omegaThree=" + omegaThree +
                ", omegaSix=" + omegaSix +
                ", portion=" + portion +
                '}';
    }
}
