//Done By Tareq : 2236415
package booking;

public class BookingManager {

    private IPaymentGateway paymentGateway;
    private INotificationService notificationService;
    private IEventRepository eventRepository;
    public BookingManager(IPaymentGateway paymentGateway,
                          INotificationService notificationService,
                          IEventRepository eventRepository) {
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
        this.eventRepository = eventRepository;
    }

    public boolean bookTicket(String eventId, double amount) {

        // invalid input
        if (eventId == null || amount <= 0) {
            return false;
        }

        // sold out
        if (eventRepository.isSoldOut(eventId)) {
            return false;
        }

        // payment
        String transactionId = paymentGateway.processPayment(amount);

        if (transactionId == null) {
            return false;
        }

        // save booking
        eventRepository.saveBooking(eventId);

        // send confirmation
        notificationService.sendConfirmation("Booking confirmed!");

        return true;
    }
}
