package com.alkemy.ong.domain.users;

import com.alkemy.ong.domain.mail.MailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	private final UserGateway userGateway;
    private final MailService mailService;

    private final String SUBJECT = "%s, registration was successful";
    private final String BODY = "<br>Welcome to Somas Mas ONG, now you are part of our family." +
                                "<br>%s, your Username is:<br>%s"+
                                "<br><br><b>¡Thank you for registering!</b><br>";


	public UserService(UserGateway userGateway,MailService mailService) {
        this.userGateway = userGateway;
        this.mailService =mailService;
    }

    public List<User> findAll(){
        return userGateway.findAll();
    }

	public User findByEmail(String email) {
        return userGateway.findByEmail(email);
    }

    public User register(User user) {
        User userRegistered = userGateway.register(user);
        sendMailWithTemplate(user);
        return userRegistered;
    }

    public User update (User user){
        return userGateway.update(user);
    }

    public User findById(Long id){
        return userGateway.findById(id);
    }
    
    public void deleteById(Long id) {
        userGateway.deleteById(id);
    }

    private void sendMailWithTemplate(User user){
        String subject = String.format(SUBJECT,user.getFirstName());
        String body = String.format(BODY, user.getFirstName(), user.getEmail());
        mailService.sendMailWithTemplate(user.getEmail(), subject, body);
    }
}
