public class Employee extends User {
    private String EmployeeId;
    private String position;

    public Employee(String userId, String username, String password,
                    String name, String email, String employeeId, String position) {
        super(userId, username, password, name, email);
        this.EmployeeId = employeeId;
        this.position = position;
    }


    public String getEmployeeId() {
        return EmployeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String pos) {
        this.position = pos;
    }
}







