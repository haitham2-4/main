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

        
        if (eventId == null || amount <= 0) {
            return false;
        }

        
        if (eventRepository.isSoldOut(eventId)) {
            return false;
        }

        
        String transactionId = paymentGateway.processPayment(amount);

        if (transactionId == null) {
            return false;
        }

        
        eventRepository.saveBooking(eventId);

    
        notificationService.sendConfirmation("Booking confirmed!");

        return true;
    }
}
