package com.tongthuan.webdothethao_backend.service;

import com.tongthuan.webdothethao_backend.HandleError;
import com.tongthuan.webdothethao_backend.constantvalue.Role;
import com.tongthuan.webdothethao_backend.entity.Cart;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.CartRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> registerAccount(Users user)
    {
        if(usersRepository.existsByUserName(user.getUserName())){
            return ResponseEntity.badRequest().body(new HandleError("Ten dang nhap da ton tai!"));
        }

        if(usersRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body(new HandleError("Email da ton tai!"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActiveCode(genActiveCode());
        user.setActive(false);
        user.setEnable(true);
        user.setRole(Role.CUSTOMER);
        user.setCreatedDate(new Date(System.currentTimeMillis()));

        usersRepository.save(user);

        sendActiveEmail(user.getEmail(),user.getActiveCode());

        return ResponseEntity.ok().build();

    }




    private String genActiveCode()
    {
        return UUID.randomUUID().toString();
    }

    private void sendActiveEmail(String email, String activeCode)
    {
        String subject = "Kích hoạt tài khoản tại web YOUSPORT";
        String text = "";
        text+="<br/> Nhấn vào nút bên dưới để kích hoạt cho tài khoản "+email;
        String url = "http://localhost:3000/Active/"+email+"/"+activeCode;
        text+="<br/> <a href="+url+">"+"<button >KÍCH HOẠT</button>"+"</a>";
        emailService.sendMessage("tongthuan15092003@gmail.com",email,subject,text);
    }


    public ResponseEntity<?> activeAccount(String email, String activeCode){

        Users user = usersRepository.findByEmail(email);

        if(user == null)
        {
            return ResponseEntity.badRequest().body(new HandleError("Người dùng không tồn tại!"));
        }

        if(user.isActive())
        {
            return ResponseEntity.badRequest().body(new HandleError("Người dùng đã kích hoạt trước đó!"));
        }

        if(activeCode.equals(user.getActiveCode()))
        {
            user.setActive(true);
            user.setActiveCode(null);
            usersRepository.save(user);
            addCart(user);
            return  ResponseEntity.ok("Kích hoạt tài khoản thành công");
        }else {
            return  ResponseEntity.badRequest().body("Mã kích hoạt không chính xác!");
        }

    }

    public void addCart(Users user)
    {
        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if(cart == null)
        {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
    }


}
