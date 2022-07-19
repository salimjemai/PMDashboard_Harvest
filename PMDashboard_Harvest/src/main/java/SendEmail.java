import com.sendgrid.Email;
import com.sendgrid.Method;
import com.sendgrid.Mail;
import com.sendgrid.Content;
import com.sendgrid.Response;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

import java.io.IOException;

public class SendEmail
{

//    API Key
//    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
//----------------------------------------------------------------------------

    public void sendingEmail(String emailAdress,String Changes) throws Exception, IOException
    {
//        , sun.util.calendar.BaseCalendar.Date startDate, BaseCalendar.Date endDate

        Email from = new Email( "junko.himono@inventive.io" );
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email( "junkohimono@yahoo.co.jp" );
        Content content = new Content( "text/plain", "This is a test email" );
        Mail mail = new Mail( from, subject, to, content );
        SendGrid sg = new SendGrid( System.getenv( "SENDGRID_API_KEY" ) );
        Request request = new Request();
        try {
            request.setMethod( Method.POST );
            request.setEndpoint( "mail/send" );
            request.setBody( mail.build() );
            Response response = sg.api( request );
            System.out.println( response.getStatusCode() );
            System.out.println( response.getBody() );
            System.out.println( response.getHeaders() );
        }
        catch (IOException ex)
        {
            String IOExpMessage = null;

            if (ex != null)
                IOExpMessage = ex.getMessage();
            System.out.println( "Error Sending Email: " + IOExpMessage );
        }
    }
}
