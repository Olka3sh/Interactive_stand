public class OrderComponent {
    private final int idOrder;
    private final Component component;
    private final int quantity;
    private double cost;

    public OrderComponent(int idOrder, Component component, int quantity) {
        this.idOrder = idOrder;
        this.component = component;
        this.quantity = quantity;
        this.cost = 0.0;
    }

    public double calculateCost(CostCalculationStrategy strategy) {
        this.cost = strategy.calculateItemCost(component, quantity);
        return this.cost;
    }

    public int getIdOrder() { return idOrder; }
    public Component getComponent() { return component; }
    public int getQuantity() { return quantity; }
    public double getCost() { return cost; }
}
