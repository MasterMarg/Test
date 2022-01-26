package com.company.test.web.screens;

import com.company.test.service.SaveLoadService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import org.slf4j.Logger;
import javax.inject.Inject;
import java.io.IOException;

@UiController("test_SaveScreen")
@UiDescriptor("save-screen.xml")
@DialogMode(forceDialog = true, width = "AUTO")

public class SaveScreen extends Screen {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SaveScreen.class);
    @Inject
    private TextField<String> textField;
    @Inject
    private SaveLoadService saveLoadService;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    private int type;
    private String data;

    /**
     * Invoking this method on a Save button click trying to save data to file
     */
    public void pressSaveButton() {
        if (textField.getValue() == null) notifications.create(Notifications.NotificationType.WARNING).
                withCaption(messageBundle.getMessage("mainScreen.noFileNameException.caption")).show();
        else {
            try {
                saveLoadService.saveToFile(type, data, textField.getValue());
                notifications.create().withCaption(messageBundle.getMessage("mainScreen.fileSaved.caption")).show();
                closeWithDefaultAction();
            } catch (IOException exception) {
                log.error("Error", exception);
            }
        }
    }

    /**
     * This method is a standard setter to a screen field used to send information about chosen task
     * from Main Screen
     * @param taskType is an int number, representing a type of a chosen task in a LookUpField in Main Screen
     */
    public void setType(int taskType) {
        this.type = taskType;
    }

    /**
     * This method is a standard setter to a screen field used to send information about data input
     * from Main Screen
     * @param string is a string input of a user in a data field of Main Screen
     */
    public void setData(String string) {
        this.data = string;
    }

    /**
     * Invoking this method on a Cancel button click
     */
    public void pressCancelButton() {
        closeWithDefaultAction();
    }
}