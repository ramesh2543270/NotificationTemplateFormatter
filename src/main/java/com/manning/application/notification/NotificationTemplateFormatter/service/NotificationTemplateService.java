package com.manning.application.notification.NotificationTemplateFormatter.service;

import com.manning.application.notification.NotificationTemplateFormatter.model.NotificationParameter;
import com.manning.application.notification.NotificationTemplateFormatter.model.TemplateRequest;
import com.manning.application.notification.NotificationTemplateFormatter.model.TemplateResponse;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationTemplateService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public TemplateResponse getNotificationTemplateMerged(TemplateRequest notificationTemplateRequest){
        Map<String,Object> notificationTemplateParamMap =
                notificationTemplateRequest.getNotificationParameters()
                        .stream()
                        .collect(Collectors
                                .toMap(NotificationParameter::getNotificationParameterName,NotificationParameter::getNotificationParameterValue));

        TemplateResponse notificationTemplateResponse = new TemplateResponse();
        if(notificationTemplateRequest.getNotificationMode().equals("EMAIL")){
            Context ctx = new Context();
            ctx.setVariables(notificationTemplateParamMap);
            File emailTemplateFile = new File("./src/main/resources/templates/email/"+notificationTemplateRequest.getNotificationTemplateName()+".html");
            if(emailTemplateFile.exists()){
                String notificationContent = templateEngine.process("email/"+notificationTemplateRequest.getNotificationTemplateName()+".html",ctx);
                notificationTemplateResponse.setEmailContent(notificationContent);
                notificationTemplateResponse.setStatus("SUCCESS");
                notificationTemplateResponse.setStatusDescription("Successfully merged the template with the template parameters");
                notificationTemplateResponse.setEmailSubject("Message from Citizen Bank");
                notificationTemplateResponse.setEmailContent(notificationContent);
            }
            else
            {
                notificationTemplateResponse.setStatus("ERROR");
                notificationTemplateResponse.setStatusDescription("Email Template is not available");
            }
        }
        else{//SMS
            String smstemplateString="";
            if(notificationTemplateRequest.getNotificationTemplateName().equalsIgnoreCase("ViewBalance")){
                smstemplateString = this.getBalanceSMSTemplate();
            }
            else if(notificationTemplateRequest.getNotificationTemplateName().equalsIgnoreCase("PhoneNumberChanged")){
                smstemplateString = this.getPhoneNumberChanged();
            }

            if(!smstemplateString.equals("")){
                StringSubstitutor sub = new StringSubstitutor(notificationTemplateParamMap);
                String notificationContent = sub.replace(smstemplateString);
                System.out.println(notificationContent);
                notificationTemplateResponse.setStatus("SUCCESS");
                notificationTemplateResponse.setStatusDescription("Successfully merged the template with the template parameters");
                notificationTemplateResponse.setSmsContent(notificationContent);
            }
            else {
                notificationTemplateResponse.setStatus("ERROR");
                notificationTemplateResponse.setStatusDescription("Email Template is not available");
            }
        }

        return notificationTemplateResponse;
    }

    public static String getBalanceSMSTemplate() {
        return "Hello ${name}"
                .concat("\n")
                .concat("Welcome to the Citizen Bank\n")
                .concat("Your balance is ${balance}\n")
                .concat("Thanks");
    }

    private String getPhoneNumberChanged() {
        return "Hello ${name}"
                .concat("\n")
                .concat("Welcome to the Citizen Bank\n")
                .concat("Your Phonenumber is changed from ${oldPhoneNumber} to ${newPhoneNumber}\n");
    }

}
