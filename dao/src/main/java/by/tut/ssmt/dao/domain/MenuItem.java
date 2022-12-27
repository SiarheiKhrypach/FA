package by.tut.ssmt.dao.domain;

public class MenuItem {

//    private String productName;
//    private double omegaThree;
//    private double omegaSix;
//    private int portions;

    private String userName;
    private int productID;
    private int portions;

    public MenuItem(){
    }

    public MenuItem(String userName, int productID, int portions) {
        this.userName = userName;
        this.productID = productID;
        this.portions = portions;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

        MenuItem menuItem = (MenuItem) o;

        if (productID != menuItem.productID) return false;
        if (portions != menuItem.portions) return false;
        return userName.equals(menuItem.userName);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + productID;
        result = 31 * result + portions;
        return result;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "userName='" + userName + '\'' +
                ", productID=" + productID +
                ", portions=" + portions +
                '}';
    }

}



