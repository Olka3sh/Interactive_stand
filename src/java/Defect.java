import java.time.LocalDate;

public abstract class Defect {
    private final int idDefect;
    private String description;
    private String severity;
    private LocalDate foundDate;
    private LocalDate fixedDate;
    private String status;
    private Employee employee;
    private Operation operation;

    protected Defect(int idDefect, String description, String severity, LocalDate foundDate,
                     LocalDate fixedDate, String status, Employee employee, Operation operation) {
        this.idDefect = idDefect;
        this.description = description;
        this.severity = severity;
        this.foundDate = foundDate;
        this.fixedDate = fixedDate;
        this.status = status;
        this.employee = employee;
        this.operation = operation;
    }

    public abstract String getDefectCategory();
    public abstract String getRoutingTarget();

    public int getIdDefect() { return idDefect; }
    public String getDescription() { return description; }
    public String getSeverity() { return severity; }
    public LocalDate getFoundDate() { return foundDate; }
    public LocalDate getFixedDate() { return fixedDate; }
    public String getStatus() { return status; }
    public Employee getEmployee() { return employee; }
    public Operation getOperation() { return operation; }
}