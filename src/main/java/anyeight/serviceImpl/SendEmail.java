package anyeight.serviceImpl;

import anyeight.util.SendMail;

import javax.mail.MessagingException;

/**
 * Created by T5-SK on 2017/6/13.
 */
public class SendEmail implements Runnable {
    private String email;
    private String text;

    public SendEmail(String email,String text) {
        this.email = email;
        this.text=text;
    }

    public void run() {
        try {
            SendMail.sendMessage("smtp.qq.com", "244513274@qq.com",
                    "ampfrgvsfyrwbich", email,text,
                    "---------------AnyEight-----------",
                    "text/html;charset=UTF-8");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
