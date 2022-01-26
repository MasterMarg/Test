package com.company.test.web.screens.save;

import com.haulmont.cuba.gui.screen.*;
import com.company.test.entity.Save;

@UiController("Saves.edit")
@UiDescriptor("save-edit.xml")
@EditedEntityContainer("saveDc")
@LoadDataBeforeShow
public class SaveEditScreen extends StandardEditor<Save> {
}