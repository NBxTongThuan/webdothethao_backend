package com.tongthuan.webdothethao_backend.service;

import com.tongthuan.webdothethao_backend.HandleError;
import com.tongthuan.webdothethao_backend.constantvalue.Role;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.ChangePasswordRequest;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.ResetPasswordRequest;
import com.tongthuan.webdothethao_backend.entity.Cart;
import com.tongthuan.webdothethao_backend.entity.UserDetail;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.CartRepository;
import com.tongthuan.webdothethao_backend.repository.UserDetailsRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> registerAccount(Users user) {
        if (usersRepository.existsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body(new HandleError("Ten dang nhap da ton tai!"));
        }
        if (usersRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new HandleError("Email da ton tai!"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActiveCode(genUUIDCode());
        user.setActive(false);
        user.setEnable(true);
        user.setRole(Role.CUSTOMER);
        user.setCreatedDate(LocalDateTime.now());

        UserDetail userDetails = new UserDetail();
        userDetails.setUser(user);
        usersRepository.saveAndFlush(user);
        userDetailsRepository.saveAndFlush(userDetails);
        sendActiveEmail(user.getEmail(), user.getActiveCode());
        return ResponseEntity.ok().build();

    }

    @Transactional
    public Users lockAccount(String userId) {
        Users user = usersRepository.findByUserId(userId);
        user.setEnable(false);
        return usersRepository.saveAndFlush(user);
    }


    private String genUUIDCode() {
        return UUID.randomUUID().toString();
    }

    private void sendActiveEmail(String email, String activeCode) {
        String subject = "Kích hoạt tài khoản tại web YOUSPORT";
        String text = "";
        text += "<div style=\"font-family: Arial, sans-serif; font-size: 14px; color: #333; padding: 20px;\">";
        text += "<h2 style=\"color: #2e7d32;\">Kích hoạt tài khoản của bạn</h2>";
        text += "<p>Chào bạn,</p>";
        text += "<p>Bạn vừa đăng ký tài khoản với địa chỉ email: <strong>" + email + "</strong>.</p>";
        text += "<p>Nhấn vào nút bên dưới để kích hoạt tài khoản:</p>";

        String url = "http://localhost:3000/Active/" + email + "/" + activeCode;
        text += "<a href=\"" + url + "\" style=\"display: inline-block; padding: 12px 20px; background-color: #2e7d32; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; margin-top: 10px;\">KÍCH HOẠT</a>";

        text += "<p style=\"margin-top: 20px;\">Nếu bạn không thực hiện hành động này, hãy bỏ qua email này.</p>";
        text += "<p>Trân trọng,<br/>Đội ngũ YOUSPORT</p>";
        text += "</div>";
        emailService.sendMessage("tongthuan15092003@gmail.com", email, subject, text);

    }


    public ResponseEntity<?> activeAccount(String email, String activeCode) {

        Users user = usersRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body(new HandleError("Người dùng không tồn tại!"));
        }

        if (user.isActive()) {
            return ResponseEntity.badRequest().body(new HandleError("Người dùng đã kích hoạt trước đó!"));
        }

        if (activeCode.equals(user.getActiveCode())) {
            user.setActive(true);
            user.setActiveCode(null);
            usersRepository.saveAndFlush(user);
            addCart(user);
            return ResponseEntity.ok("Kích hoạt tài khoản thành công");
        } else {
            return ResponseEntity.badRequest().body("Mã kích hoạt không chính xác!");
        }

    }

    public void addCart(Users user) {
        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.saveAndFlush(cart);
        }
    }

    public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        Users user = usersRepository.findByUserName(changePasswordRequest.getUserName()).orElse(null);
        if (user == null)
            return false;

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),user.getPassword()))
            return false;
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        usersRepository.saveAndFlush(user);
        return true;
    }

    public boolean forgotPassword(String email)
    {
        Users user = usersRepository.findByEmail(email);
        if(user == null)
        {
            return false;
        }
        user.setForgotPasswordCode(genUUIDCode());

        sendResetPasswordMail(email,user.getForgotPasswordCode());
        usersRepository.saveAndFlush(user);
        return true;

    }

    public boolean resetPassword(ResetPasswordRequest resetPasswordRequest)
    {
        Users user = usersRepository.findByEmail(resetPasswordRequest.getEmail());
        if(user == null)
        {
            return false;
        }

        String forgotCode = user.getForgotPasswordCode();

        if(!forgotCode.equals(resetPasswordRequest.getForgotPasswordCode()))
            return false;

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        user.setForgotPasswordCode(null);
        usersRepository.saveAndFlush(user);
        return true;
    }

    private void sendResetPasswordMail(String email,String resetPasswordCode)
    {
        String subject = "Tạo lại mật khẩu tài khoản tại web YOUSPORT";
        String text = "";

        text += "<div style=\"font-family: Arial, sans-serif; font-size: 14px; color: #333; padding: 20px;\">";
        text += "<h2 style=\"color: #d32f2f;\">Yêu cầu tạo lại mật khẩu</h2>";
        text += "<p>Xin chào,</p>";
        text += "<p>Bạn hoặc ai đó đã yêu cầu tạo lại mật khẩu cho tài khoản: <strong>" + email + "</strong>.</p>";
        text += "<p>Nhấn vào nút bên dưới để đặt lại mật khẩu:</p>";

        String url = "http://localhost:3000/resetPassword/" + email + "/" + resetPasswordCode;
        text += "<a href=\"" + url + "\" style=\"display: inline-block; padding: 12px 20px; background-color: #d32f2f; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; margin-top: 10px;\">ĐẶT LẠI MẬT KHẨU</a>";

        text += "<p style=\"margin-top: 20px;\">Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>";
        text += "<p>Trân trọng,<br/>Đội ngũ YOUSPORT</p>";
        text += "</div>";

        emailService.sendMessage("tongthuan15092003@gmail.com", email, subject, text);

    }

}
