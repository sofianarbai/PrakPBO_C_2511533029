package shop.shipping;

public class FreeShipping extends ShippingService {

    @Override
    public long ratePerKg() {
        return 0;
    }

    @Override
    public long calculateCost(int weightGrams) {
        return 0;
    }

}
