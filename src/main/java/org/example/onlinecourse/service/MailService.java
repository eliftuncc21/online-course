package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendRegistrationMail(String to, String firstName) {
        String subject = "Kayıt Başarılı!";
        String body = "Merhaba " + firstName + ",\n\nOnline Course sistemine kaydınız başarıyla tamamlandı.";
        sendSimpleMail(to, subject, body);
    }

    public void sendRegistrationCourse(String to, String courseName) {
        String subject = "Kayıt Başarılı!";
        String body = "Merhaba \n\n" + courseName + ", kursuna kaydınız başarıyla tamamlandı.";
        sendSimpleMail(to, subject, body);
    }

    public void sendReminderMail(String to, String firstName) {
        String subject = "Sisteme Giriş Yapmadınız!";
        String body = "Merhaba " + firstName + ",\n\nBir süredir sisteme giriş yapmadığınızı fark ettik. "
                + "Lütfen kurslarınıza devam etmek için sisteme giriş yapın.\n\nTeşekkürler.";
        sendSimpleMail(to, subject, body);
    }

    private void sendSimpleMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
