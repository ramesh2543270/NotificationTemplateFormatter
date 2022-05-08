package com.manning.application.notification.NotificationTemplateFormatter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateResponse {
    private String status;
    private String statusDescription;
    private String emailContent;
    private String smsContent;
    private String emailSubject;
}
