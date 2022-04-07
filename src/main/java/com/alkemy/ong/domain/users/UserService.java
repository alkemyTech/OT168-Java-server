package com.alkemy.ong.domain.users;

import com.alkemy.ong.domain.mail.MailGateway;
import com.alkemy.ong.domain.mail.MailRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	private final UserGateway userGateway;
    private final MailGateway mailGateway;

    private final String SUBJECT = "%s, registration was successful";
    private final String BODY = " $s, welcome to Somas Mas ONG, now you are part of our family." +
                                "\nUsername: %s"+
                                "\nPassword: %s";

	public UserService(UserGateway userGateway,MailGateway mailGateway) {
        this.userGateway = userGateway;
        this.mailGateway=mailGateway;
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
        mailGateway.sendMailWithTemplate(user.getEmail(), subject, BODY);
    }

    private void sendMailWithoutTemplate(User user){
        String subject = String.format(SUBJECT,user.getFirstName());
        mailGateway.sendMail(builMail(user));
    }

    private MailRequest builMail(User user){
        return MailRequest.builder()
                .body(String.format(BODY, user.getFirstName(),user.getEmail(),user.getPassword()))
                .subject(String.format(SUBJECT,user.getFirstName()))
                .to(user.getEmail())
                .build();
    }

}
