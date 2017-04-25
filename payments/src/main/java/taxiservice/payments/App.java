package taxiservice.payments;

import taxiservice.payments.services.PaymentService;

public class App 
{
    public static void main( String[] args )
    {
    		PaymentService p = new PaymentService();
    		System.out.println(p.getCreditForClient(10));
    }
}
