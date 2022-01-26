package com.company.test.web.screens.save;

import com.haulmont.cuba.gui.screen.*;
import com.company.test.entity.Save;

import java.util.Collection;

@UiController("Saves.browse")
@UiDescriptor("save-browse-screen.xml")
@LookupComponent("savesTable")
@LoadDataBeforeShow
public class SaveBrowseScreen extends StandardLookup<Save> {
    private Collection<Save> saves;

    /**
     * Standard method to get saves field value
     * @return a {@link Collection}<{@link Save}> from saves field
     */
    public Collection<Save> getSaves() {
        return this.saves;
    }

    /**
     * This method used to set a saves field value
     * @param saves is a {@link Collection}<{@link Save}> to set to the saves field
     */
    public void setSaves(Collection<Save> saves){
        this.saves = saves;
    }

    /**
     * This method invokes on a confirmButton click
     */
    public void onSaveButtonClick() {
        this.setSaves(this.getLookupComponent().getLookupSelectedItems());
        if (!this.getSaves().isEmpty()) this.close(StandardOutcome.SELECT);
    }
}