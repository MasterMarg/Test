package com.company.test.web.screens;

import com.company.test.service.SaveLoadService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.DialogMode;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
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
    private int type;
    private String data;

    public void pressSaveButton() {
        if (textField.getValue() == null) notifications.create(Notifications.NotificationType.WARNING).withCaption(
                "Введите имя файла!").show();
        else {
            try {
                saveLoadService.saveToFile(type, data, textField.getValue());
                notifications.create().withCaption("Файл сохранен").show();
                closeWithDefaultAction();
            } catch (IOException exception) {
                log.error("Error", exception);
            }
        }
    }

    public void setType(int taskType) {
        this.type = taskType;
    }

    public void setData(String string) {
        this.data = string;
    }

    public void pressCancelButton() {
        closeWithDefaultAction();
    }
}