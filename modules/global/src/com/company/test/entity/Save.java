package com.company.test.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "SAVES")
@Entity(name = "Saves")
@NamePattern("%s|id")
public class Save extends StandardEntity {
    private static final long serialVersionUID = -376632752676386870L;
    @Column(
            name = "DATA",
            nullable = false
    )
    private String data;
    @Column(
            name = "TYPE"
    )
    private Integer task;

    public Save() {
    }

    /**
     * Standard method to get a data value
     * @return a {@link String} value of data field
     */
    public String getData() {
        return this.data;
    }

    /**
     * This method is used to set data value
     * @param value is a {@link String} containing data value to set
     */
    public void setData(String value){
        this.data = value;
    }

    /**
     * Standard method to get a task value
     * @return an {@code int} representing a type of a task
     */
    public Integer getTask(){
        return this.task;
    }

    /**
     * This method is used to set task value
     * @param value is an {@code int} representing a type of a task to set
     */
    public void setTask(Integer value){
        this.task = value;
    }
}