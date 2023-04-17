package com.solvd.prendiodemo.domain;

import java.util.Objects;

public class ItemContents {

    private String title;
    private String supplier;
    private String itemNumber;
    private String price;
    private String currencyType;
    private String project;
    private String glAccount;
    private String department;

    public ItemContents() {
    }

    public ItemContents(ItemContentsBuilder builder) {
        this.title = builder.title;
        this.supplier = builder.supplier;
        this.itemNumber = builder.itemNumber;
        this.price = builder.price;
        this.currencyType = builder.currencyType;
        this.project = builder.project;
        this.glAccount = builder.glAccount;
        this.department = builder.department;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, supplier, itemNumber, price, currencyType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemContents that = (ItemContents) o;
        return Objects.equals(title, that.title) && Objects.equals(supplier, that.supplier) && Objects.equals(itemNumber, that.itemNumber) && Objects.equals(price, that.price) && Objects.equals(currencyType, that.currencyType);
    }

    @Override
    public String toString() {
        return "ItemContents{" +
                "title='" + title + '\'' +
                ", supplier='" + supplier + '\'' +
                ", itemNumber='" + itemNumber + '\'' +
                ", price='" + price + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", project='" + project + '\'' +
                ", glAccount='" + glAccount + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(String glAccount) {
        this.glAccount = glAccount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public static class ItemContentsBuilder {

        private String title;
        private String supplier;
        private String itemNumber;
        private String price;
        private String currencyType;
        private String project;
        private String glAccount;
        private String department;

        public ItemContentsBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ItemContentsBuilder setSupplier(String supplier) {
            this.supplier = supplier;
            return this;
        }

        public ItemContentsBuilder setItemNumber(String itemNumber) {
            this.itemNumber = itemNumber;
            return this;
        }

        public ItemContentsBuilder setPrice(String price) {
            this.price = price;
            return this;
        }

        public ItemContentsBuilder setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
            return this;
        }

        public ItemContentsBuilder setProject(String project) {
            this.project = project;
            return this;
        }

        public ItemContentsBuilder setGlAccount(String glAccount) {
            this.glAccount = glAccount;
            return this;
        }

        public ItemContentsBuilder setDepartment(String department) {
            this.department = department;
            return this;
        }

        public ItemContents build() {
            return new ItemContents(this);
        }
    }
}
