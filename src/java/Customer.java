import java.time.LocalDate;
import java.util.List;

public class Customer {
    private final int idCustomer;
    private String name;
    private String inn;
    private String contactPerson;
    private String phone;
    private String email;

    public Customer(int idCustomer, String name, String inn, String contactPerson, String phone, String email) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.inn = inn;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
    }

    public Order registerProductionOrder(List<Order> orders, String description, LocalDate createdDate, int plannedDays) {
        if (orders == null) {
            throw new IllegalArgumentException("Список заказов orders не может быть null");
        }
        if (plannedDays <= 0) {
            throw new IllegalArgumentException("Плановый срок выполнения plannedDays должен быть больше 0");
        }
        int nextId = orders.stream().mapToInt(Order::getIdOrder).max().orElse(0) + 1;
        LocalDate start = (createdDate != null) ? createdDate : LocalDate.now();
        LocalDate finish = start.plusDays(plannedDays);

        Order newOrder = new Order(nextId, start, finish, "Зарегистрирован", description, this);
        orders.add(newOrder);
        return newOrder;
    }

    public int getIdCustomer() { return idCustomer; }
    public String getName() { return name; }
    public String getInn() { return inn; }
    public String getContactPerson() { return contactPerson; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}