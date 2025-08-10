package com.example.service;

import com.example.models.User;
import com.example.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "Tài khoản không tồn tại!";
        }

        User user = userOptional.get();

        if (!user.isActive()) {
            return "Tài khoản chưa kích hoạt!";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Sai mật khẩu!";
        }

        return "Đăng nhập thành công!";
    }

    public String register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email đã tồn tại!";
        }

        String otp = generateOTP();
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu trước khi lưu
        user.setOtp(otp);
        user.setActive(false);
        userRepository.save(user);

        sendOTP(email, otp);
        return "OTP đã gửi, hãy kiểm tra email!";
    }

    private String generateOTP() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void sendOTP(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setSubject("Mã OTP kích hoạt tài khoản");
            helper.setText("Mã OTP của bạn là: " + otp);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi gửi email!");
        }
    }
}
