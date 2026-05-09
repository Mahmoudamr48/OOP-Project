public abstract class User {
    private String userId;
    private String userName;
    private String password;
    private String name;
    private String email;

    public User(String userId, String userName, String password, String name, String email) {
        this.userId = userId;
        this.userName = userName;
        setPassword(password);
        this.name = name;
        this.email = email;
    }

    public String getEmail() {return email;}
    public String getName() {return name;}
    public String getUserId() {return userId;}
    public String getUserName() {return userName;}


    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {
        if (password.length() < 5) {System.out.println("Min pass length is 5 characters");}
        else this.password = password;}



}
