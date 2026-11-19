public class DiscountCostStrategy implements CostCalculationStrategy {
    private final int thresholdQuantity;
    private final double discountRate;

    public DiscountCostStrategy(int thresholdQuantity, double discountRate) {
        this.thresholdQuantity = thresholdQuantity;
        this.discountRate = discountRate;
    }

    @Override
    public double calculateItemCost(Component component, int quantity) {
        double baseCost = component.getPricePerUnit() * quantity;
        if (quantity >= thresholdQuantity) {
            return baseCost * (1.0 - discountRate);
        }
        return baseCost;
    }

    @Override
    public String getStrategyName() {
        return "Оптовый расчёт со скидкой";
    }
}