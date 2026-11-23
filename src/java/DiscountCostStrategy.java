public class DiscountCostStrategy implements CostCalculationStrategy {
    private final int thresholdQuantity;
    private final double discountRate;

    public DiscountCostStrategy(int thresholdQuantity, double discountRate) {
        if (thresholdQuantity <= 0 || discountRate < 0.0 || discountRate > 1.0) {
            throw new IllegalArgumentException(
                    "Некорректные параметры стратегии скидок: пороговое количество должно быть > 0, скидка в диапазоне [0.0; 1.0]"
            );
        }
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