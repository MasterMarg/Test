package com.company.test.web.screens;

import com.company.test.entity.Save;
import com.company.test.service.MainService;
import com.company.test.service.SaveLoadService;
import com.company.test.web.screens.save.SaveBrowseScreen;
import com.company.test.web.screens.save.SaveEditScreen;
import com.haulmont.cuba.core.global.DataManager;
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
import java.util.ArrayList;
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
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private DataManager dataManager;

    /**
     * Subscribing to screen initialisation event to initialise LookUpField's options
     *
     * @param event screen initialisation event
     */
    @Subscribe
    public void onInit(InitEvent event) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put(messageBundle.getMessage("mainScreen.lookUpField.comp1.caption"), 1);
        map.put(messageBundle.getMessage("mainScreen.lookUpField.comp2.caption"), 2);
        tasks.setOptionsMap(map);
    }

    /**
     * Subscribing to successful file uploading event in the FileUploadField to get data from the loaded file
     *
     * @param event successful file uploading event
     */
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
        } else notifications.create(Notifications.NotificationType.ERROR).
                withCaption(messageBundle.getMessage("mainScreen.fileNotFoundException.message")).show();
    }

    /**
     * Invoking this method on a Count button click
     */
    public void pressCountButton() {
        resultField.clear();
        if (tasks.getValue() == null || tasks.getValue() == 0) notifications.
                create(Notifications.NotificationType.HUMANIZED).
                withCaption(messageBundle.getMessage("mainScreen.taskException.message")).show();
        else if (dataField.getValue() == null) {
            notifications.create(Notifications.NotificationType.HUMANIZED).
                    withCaption(messageBundle.getMessage("mainScreen.noDataException.message")).show();
        } else {
            try {
                resultField.setValue(mainService.getResult(tasks.getValue(), dataField.getValue()));
            } catch (IOException exception) {
                notifications.create(Notifications.NotificationType.WARNING).
                        withCaption(messageBundle.getMessage("mainScreen.wrongDataException.caption")).
                        withDescription(exception.getMessage()).show();
            } catch (RuntimeException exception) {
                notifications.create(Notifications.NotificationType.WARNING).
                        withCaption(messageBundle.getMessage("mainScreen.wrongDataException.caption")).
                        withDescription(messageBundle.getMessage("mainScreen.wrongDataException.message")).show();
            }
        }
    }

    /**
     * Invoking this method on a Export button press, creating a new SaveScreen to save data
     */
    public void saveData() {
        SaveScreen screen = screens.create(SaveScreen.class);
        if (tasks.getValue() != null) screen.setType(tasks.getValue());
        if (dataField.getValue() != null) {
            screen.setData(dataField.getValue());
            screens.show(screen);
        } else notifications.create(Notifications.NotificationType.WARNING).
                withCaption(messageBundle.getMessage("mainScreen.noSavingDataException.caption")).
                withDescription(messageBundle.getMessage("mainScreen.noSavingDataException.message")).show();
    }

    /**
     * Invoking this method to create a Save out of data input and save it in database
     */
    public void saveToDB() {
        SaveEditScreen screen = screens.create(SaveEditScreen.class);
        Save save = dataManager.create(Save.class);
        if (tasks.getValue() != null) save.setTask(tasks.getValue());
        if (dataField.getValue() != null) {
            save.setData(dataField.getValue());
            screen.setEntityToEdit(save);
            screens.show(screen);
        } else notifications.create(Notifications.NotificationType.WARNING).
                withCaption(messageBundle.getMessage("mainScreen.noSavingDataException.caption")).
                withDescription(messageBundle.getMessage("mainScreen.noSavingDataException.message")).show();
    }

    /**
     * Invoking this method to load a Save from database
     */
    public void loadFromDB() {
        SaveBrowseScreen screen = screens.create(SaveBrowseScreen.class);
        screen.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.SELECT)) {
                ArrayList<Save> saveList = new ArrayList<>(screen.getSaves());
                if (!saveList.isEmpty()) {
                    Save save = saveList.get(0);
                    tasks.setValue(save.getTask());
                    dataField.setValue(save.getData());
                    resultField.setValue(null);
                }
            }
        });
        screens.show(screen);
    }
}