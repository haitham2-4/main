package booking;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class BookingManagerTest {

    @Test
    void testHappyPath() {

        IPaymentGateway pg = mock(IPaymentGateway.class);
        INotificationService ns = mock(INotificationService.class);
        IEventRepository er = mock(IEventRepository.class);

        when(er.isSoldOut("E1")).thenReturn(false);
        when(pg.processPayment(100)).thenReturn("TX123");

        BookingManager bm = new BookingManager(pg, ns, er);

        bm.bookTicket("E1", 100);

        verify(er, times(1)).saveBooking("E1");
        verify(ns, times(1)).sendConfirmation(anyString());
    }

    @Test
    void testInvalidInput() {

        IPaymentGateway pg = mock(IPaymentGateway.class);
        INotificationService ns = mock(INotificationService.class);
        IEventRepository er = mock(IEventRepository.class);

        BookingManager bm = new BookingManager(pg, ns, er);

        bm.bookTicket(null, -5);

        verify(pg, never()).processPayment(anyDouble());
        verify(er, never()).saveBooking(anyString());
        verify(ns, never()).sendConfirmation(anyString());
    }

    @Test
    void testSoldOut() {

        IPaymentGateway pg = mock(IPaymentGateway.class);
        INotificationService ns = mock(INotificationService.class);
        IEventRepository er = mock(IEventRepository.class);

        when(er.isSoldOut("E1")).thenReturn(true);

        BookingManager bm = new BookingManager(pg, ns, er);

        bm.bookTicket("E1", 100);

        verify(er, times(1)).isSoldOut("E1");
        verify(pg, never()).processPayment(anyDouble());
        verify(er, never()).saveBooking(anyString());
        verify(ns, never()).sendConfirmation(anyString());
    }
}