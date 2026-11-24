package shop.notify;

/** Saluran notifikasi yang bisa ditukar. */
public interface NotificationChannel {
    void send(String recipient, String message);
}