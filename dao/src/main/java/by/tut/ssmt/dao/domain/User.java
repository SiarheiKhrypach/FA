package by.tut.ssmt.dao.domain;

public class User {
    private int UserId;
    private String userName;
//    private char [] password;
    private String password;

    public User() {}

//    public User(String userName, char [] password) {
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(int UserId, String userName) {
        this.UserId = UserId;
        this.userName = userName;
    }


    //    public User(int UserId, String userName, char [] password) {
    public User(int UserId, String userName, String password) {
        this.UserId = UserId;
        this.userName = userName;
        this.password = password;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

//    public char[] getPassword() {
    public String getPassword() {
        return password;
    }

//    public void setPassword(char[] password) {
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (UserId != user.UserId) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = UserId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId=" + UserId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
