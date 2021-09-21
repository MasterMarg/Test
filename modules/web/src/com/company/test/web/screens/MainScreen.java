package com.company.test.web.screens;

import com.company.test.service.MainService;
import com.company.test.service.SaveLoadService;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import org.slf4j.Logger;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@UiController("test_MainScreen")
@UiDescriptor("main-screen.xml")
@DialogMode(forceDialog = true, width = "AUTO", height = "AUTO")
public class MainScreen extends Screen {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MainScreen.class);
    @Inject
    private LookupField<Integer> tasks;
    @Inject
    private TextArea<String> dataField;
    @Inject
    private TextArea<String> resultField;
    @Inject
    private MainService mainService;
    @Inject
    private Notifications notifications;
    @Inject
    private Screens screens;
    @Inject
    private FileUploadField uploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private SaveLoadService loadService;

    @Subscribe
    public void onInit(InitEvent event) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Минимальная стоимость трансформации до нормального квадрата", 1);
        map.put("Поиск включенных элементов строки", 2);
        tasks.setOptionsMap(map);
    }

    @Subscribe("uploadField")
    public void onUploadFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        File file = fileUploadingAPI.getFile(uploadField.getFileId());
        if (file != null) {
            try {
                String[] data = loadService.loadFromFile(file.getAbsolutePath());
                tasks.setValue(Integer.parseInt(data[0]));
                dataField.setValue(data[1]);
            } catch (IOException | RuntimeException exception) {
                log.error("Error", exception);
            }
        } else notifications.create(Notifications.NotificationType.ERROR).withCaption("Файл не найден!").show();
    }

    public void pressCountButton() {
        resultField.setValue(null);
        if (tasks.getValue() == null || tasks.getValue() == 0) notifications.
                create(Notifications.NotificationType.HUMANIZED).withCaption("Выберите задание!").show();
        else if (dataField.getValue() == null) {
            notifications.create(Notifications.NotificationType.HUMANIZED).
                    withCaption("Введите данные!").show();
        } else {
            try {
                resultField.setValue(mainService.getResult(tasks.getValue(), dataField.getValue()));
            } catch (RuntimeException exception) {
                notifications.create(Notifications.NotificationType.WARNING).withCaption("Ошибка!").
                        withDescription("Проверьте правильность введенных данных!").show();
            }
        }
    }

    public void saveData() {
        SaveScreen screen = screens.create(SaveScreen.class);
        if (tasks.getValue() != null) screen.setType(tasks.getValue());
        if (dataField.getValue() != null) {
            screen.setData(dataField.getValue());
            screens.show(screen);
        } else notifications.create(Notifications.NotificationType.WARNING).withCaption("Внимание!").
                withDescription("Введите данные для сохранения!").show();
    }
}