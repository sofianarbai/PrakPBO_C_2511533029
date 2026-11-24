package shop.shipping;

/** Aturan ongkos kirim yang bisa ditukar. */
public interface ShippingRate {
    long costFor(int grams);
}