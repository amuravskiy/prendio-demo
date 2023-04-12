package com.solvd.prendiodemo.domain;

import java.util.Objects;

public class SlipInfo {

    private String recDate;
    private String invoiceNumber;
    private String invDate;
    private String invoiceAmount;
    private String day;

    private SlipInfo(SlipInfoBuilder builder) {
        this.recDate = builder.recDate;
        this.invoiceNumber = builder.invoiceNumber;
        this.invDate = builder.invDate;
        this.invoiceAmount = builder.invoiceAmount;
        this.day = builder.day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SlipInfo slipInfo = (SlipInfo) o;

        if (!Objects.equals(recDate, slipInfo.recDate)) {
            return false;
        }
        if (!Objects.equals(invoiceNumber, slipInfo.invoiceNumber)) {
            return false;
        }
        if (!Objects.equals(invDate, slipInfo.invDate)) {
            return false;
        }
        return Objects.equals(invoiceAmount, slipInfo.invoiceAmount);
    }

    @Override
    public String toString() {
        return "SlipInfo{" +
                "recDate='" + recDate + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", invDate='" + invDate + '\'' +
                ", invoiceAmount='" + invoiceAmount + '\'' +
                '}';
    }

    public static class SlipInfoBuilder {

        private String day;
        private String recDate;
        private String invoiceNumber;
        private String invDate;
        private String invoiceAmount;

        public String getDay() {
            return day;
        }

        public SlipInfoBuilder setDay(String day) {
            this.day = day;
            return this;
        }

        public SlipInfoBuilder setRecDate(String recDate) {
            this.recDate = recDate;
            return this;
        }

        public SlipInfoBuilder setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }

        public SlipInfoBuilder setInvDate(String invDate) {
            this.invDate = invDate;
            return this;
        }

        public SlipInfoBuilder setInvoiceAmount(String invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
            return this;
        }

        public SlipInfo build() {
            return new SlipInfo(this);
        }
    }
}
