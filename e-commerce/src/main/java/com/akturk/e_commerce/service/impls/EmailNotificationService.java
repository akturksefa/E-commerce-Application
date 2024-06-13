package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.model.CartItem;
import com.akturk.e_commerce.model.Order;
import com.akturk.e_commerce.model.Product;
import com.akturk.e_commerce.model.User;
import com.akturk.e_commerce.repository.ProductRepository;
import com.akturk.e_commerce.service.NotificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

@Component
public class EmailNotificationService implements NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private ProductRepository productRepository;


    public void sendUserRegistrationVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromMail;
        String senderName = "Deneme";
        String subject = ".................";
        String content = "Sevgili " + user.getUsername() + ",<br><br>"
                + "<p>Bize katıldığınız için teşekkürler! Sizi aramızda görmekten mutluluk duyuyoruz.</p><br>"
                + "<br>Teşekkürler,<br>"
                + "Deneme.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    public void sendOrderConfirmationEmail(User user, Order order) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromMail;
        String senderName = "Purely";
        String subject = "Purely - Order confirmation";

        StringBuilder contentBuilder = new StringBuilder("Dear " + user.getUsername() + ",<br><br>"
                + "<h2>Thank you for your order!</h2>"
                + "<p>Your order #" + order.getId() + " has been successfully placed!</p>"
                + "<h3>Order summary</h3>");
        for(CartItem orderItem: order.getOrderItems()) {

            Product product = productRepository.findById(orderItem.getProduct().getId()).orElse(null);
            String description = product.getProductName() + ": " + orderItem.getQuantity() + " x " + product.getPrice() + "<br>";
            contentBuilder.append(description);
        }

        String content = contentBuilder.toString();

        content += "<h4>Toplam: " + order.getOrderAmt() + "</h4>"
                + "<p>Teslimat ücretleri kapınızda toplam tutarınıza eklenecektir!</p>"
                + "<br>Teşekkürler,<br>"
                + "Deneme.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

}
