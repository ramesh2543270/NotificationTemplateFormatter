package com.manning.application.notification.NotificationTemplateFormatter.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TemplateRequest {
    private List<NotificationParameter> notificationParameters;
    private String notificationTemplateName;
    private String notificationMode;
}
