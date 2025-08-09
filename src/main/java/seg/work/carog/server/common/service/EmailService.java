package seg.work.carog.server.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.sender.noreply}")
    private String noreply;

    @Value("${spring.mail.sender.notice}")
    private String notice;

    private final JavaMailSender javaMailSender;

    public void sendAuthEmail(String toEmail, String key) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(noreply);

            message.setSubject("[ Carog ] 회원가입 이메일 인증");

            String body = "<h2>안녕하세요. Carog에 가입해주셔서 감사합니다.</h3>" +
                    "<h3>아래 인증 번호를 입력하여 이메일 인증을 완료해주시기 바랍니다.</h3>" +
                    "<h3>유효기간은 10분 입니다.</h4>" +
                    "<h2>" + key + "</h2>";
            message.setText(body, "UTF-8", "html");

            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
        } catch (MessagingException e) {
            throw new BaseException(Message.AUTH_MAIL_SEND_FAIL);
        }

        javaMailSender.send(message);
    }
}
