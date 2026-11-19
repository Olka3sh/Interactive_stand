import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Order {
    private final int idOrder;
    private final LocalDate createdDate;
    private LocalDate finishDate;
    private String status;
    private double totalCost;
    private String description;
    private final Customer customer;
    private final List<OrderComponent> items;

    public Order(int idOrder, LocalDate createdDate, LocalDate finishDate, String status,
                 String description, Customer customer) {
        this.idOrder = idOrder;
        this.createdDate = createdDate;
        this.finishDate = finishDate;
        this.status = status;
        this.totalCost = 0.0;
        this.description = description;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addComponent(Component component, int quantity) {
        items.add(new OrderComponent(this.idOrder, component, quantity));
    }

    public List<Map<String, Integer>> checkMaterialsDeficit(Map<Integer, Integer> stockMap,
                                       CostCalculationStrategy strategy) {
        List<Map<String, Integer>> orderDeficits = new ArrayList<>();
        double orderCost = 0.0;
        for (OrderComponent item : items) {
            Component comp = item.getComponent();
            int compId = comp.getIdComponent();
            int available = stockMap.getOrDefault(compId, 0);
            if (!comp.isAvailable(available, item.getQuantity())) {
                Map<String, Integer> deficitItem = new LinkedHashMap<>();
                deficitItem.put("id_order", this.idOrder);
                deficitItem.put("id_component", compId);
                deficitItem.put("deficit", item.getQuantity() - available);
                orderDeficits.add(deficitItem);
            } else {
                stockMap.put(compId, available - item.getQuantity());
                orderCost += item.calculateCost(strategy);
            }
        }
        this.totalCost = Math.round(orderCost * 100.0) / 100.0;
        return orderDeficits;
    }

    public List<OrderComponent> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getIdOrder() { return idOrder; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getFinishDate() { return finishDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalCost() { return totalCost; }
    public String getDescription() { return description; }
    public Customer getCustomer() { return customer; }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "{'id_order': %d, 'created_date': '%s', 'finish_date': '%s', 'status': '%s', 'total_cost': %.1f, 'description': '%s', 'id_customer': %d}",
                idOrder, createdDate, finishDate, status, totalCost, description, customer.getIdCustomer()
        );
    }
}