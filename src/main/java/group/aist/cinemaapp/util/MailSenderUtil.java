package group.aist.cinemaapp.util;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.dto.MailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
@Log
public class MailSenderUtil {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(MailDto mailDto,byte[] pdfContent) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom("Cinema-app", "no-reply");
        helper.setTo(mailDto.getToMailAddress());
        helper.setSubject(mailDto.getSubject());

        helper.setText(mailDto.getContent(), true);

        ByteArrayResource pdfResource = new ByteArrayResource(pdfContent);

        helper.addAttachment(mailDto.getFileName()+".pdf", pdfResource);

        javaMailSender.send(message);


    }


}
