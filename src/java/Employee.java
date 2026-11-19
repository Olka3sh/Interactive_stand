import java.time.LocalDate;

public class Employee {
    private final int idEmployee;
    private String fullName;
    private String position;
    private String phone;
    private String email;
    private LocalDate hireDate;

    public Employee(int idEmployee, String fullName, String position, String phone, String email, LocalDate hireDate) {
        this.idEmployee = idEmployee;
        this.fullName = fullName;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.hireDate = hireDate;
    }

    public int getIdEmployee() { return idEmployee; }
    public String getFullName() { return fullName; }
    public String getPosition() { return position; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public LocalDate getHireDate() { return hireDate; }
}