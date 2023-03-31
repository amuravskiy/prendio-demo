package com.solvd.prendiodemo.values;

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
    public String toString() {
        return "DepInfo{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
