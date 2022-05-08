package com.manning.application.notification.NotificationTemplateFormatter.controller;

import com.manning.application.notification.NotificationTemplateFormatter.model.TemplateRequest;
import com.manning.application.notification.NotificationTemplateFormatter.model.TemplateResponse;
import com.manning.application.notification.NotificationTemplateFormatter.service.NotificationTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications/templates")
public class NotificationTemplateController {

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @PostMapping
    public TemplateResponse getNotificationTemplates(@RequestBody TemplateRequest templateRequest){
        System.out.println(templateRequest.toString());
        TemplateResponse notificationTemplateResponse=notificationTemplateService.getNotificationTemplateMerged(templateRequest);
        return notificationTemplateResponse;
    }
}
