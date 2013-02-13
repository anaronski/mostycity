package pirate.mostycity.utils.jms;

import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class JmsListener {
	public void orderReceived(Map<String, Object> message) throws Exception {
        int orderId = (Integer) message.get("orderId");
        String orderCode = (String) message.get("orderCode");
        System.out.println("Account received: "+ orderCode + "Id: "+ orderId);
      }
}
