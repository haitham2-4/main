package main;

public class DiscountCalculator {

    public static int calculateDiscount(String customerType, int totalOrders, boolean isSubscribed) {

        
        if (customerType.equals("NEW") && totalOrders >= 10) {
            throw new IllegalArgumentException("Invalid combination");
        }

        int discount = 5; 

        
        if (isSubscribed) {
            discount += 2;
        }

        
        switch (customerType) {
            case "REGULAR":
                discount += 3;
                break;
            case "PREMIUM":
                discount += 5;
                break;
            case "NEW":
               
                break;
            default:
                throw new IllegalArgumentException("Unknown type");
        }

       
        if (totalOrders >= 10) {
            discount += 5;
        }

       
        return Math.min(discount, 15);
    }
}