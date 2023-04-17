package com.solvd.prendiodemo.domain;

import java.util.Objects;

public class DepInfo {

    private String name;
    private String desc;
    private String notes;

    public DepInfo(String name, String desc, String notes) {
        this.name = name;
        this.desc = desc;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, notes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepInfo depInfo = (DepInfo) o;
        return Objects.equals(name, depInfo.name) && Objects.equals(desc, depInfo.desc) && Objects.equals(notes, depInfo.notes);
    }

    @Override
    public String toString() {
        return "DepInfo{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
