package com.solvd.prendiodemo.domain;

import java.util.Objects;

public class ItemContents {

    private String title;
    private String supplier;
    private String itemNumber;
    private String price;
    private String currencyType;

    private ItemContents(ItemContentsBuilder builder) {
        this.title = builder.title;
        this.supplier = builder.supplier;
        this.itemNumber = builder.itemNumber;
        this.price = builder.price;
        this.currencyType = builder.currencyType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemContents that = (ItemContents) o;

        if (!Objects.equals(title, that.title)) {
            return false;
        }
        if (!Objects.equals(supplier, that.supplier)) {
            return false;
        }
        if (!Objects.equals(itemNumber, that.itemNumber)) {
            return false;
        }
        if (!Objects.equals(price, that.price)) {
            return false;
        }
        return Objects.equals(currencyType, that.currencyType);
    }

    @Override
    public String toString() {
        return "ItemContents{" +
                "title='" + title + '\'' +
                ", supplier='" + supplier + '\'' +
                ", itemNumber='" + itemNumber + '\'' +
                ", price='" + price + '\'' +
                ", currencyType='" + currencyType + '\'' +
                '}';
    }

    public static class ItemContentsBuilder {

        private String title;
        private String supplier;
        private String itemNumber;
        private String price;
        private String currencyType;

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

        public ItemContents build() {
            return new ItemContents(this);
        }
    }
}
