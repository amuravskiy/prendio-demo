package com.solvd.prendiodemo.domain;

import java.util.Objects;

public class SlipInfo {

    private String recDate;
    private String invoiceNumber;
    private String invDate;
    private String invoiceAmount;
    private int day;

    private SlipInfo(SlipInfoBuilder builder) {
        this.recDate = builder.recDate;
        this.invoiceNumber = builder.invoiceNumber;
        this.invDate = builder.invDate;
        this.invoiceAmount = builder.invoiceAmount;
        this.day = builder.day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
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
    public int hashCode() {
        return Objects.hash(recDate, invoiceNumber, invDate, invoiceAmount, day);
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
        return day == slipInfo.day && Objects.equals(recDate, slipInfo.recDate) && Objects.equals(invoiceNumber, slipInfo.invoiceNumber) && Objects.equals(invDate, slipInfo.invDate) && Objects.equals(invoiceAmount, slipInfo.invoiceAmount);
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

        private int day;
        private String recDate;
        private String invoiceNumber;
        private String invDate;
        private String invoiceAmount;

        public int getDay() {
            return day;
        }

        public SlipInfoBuilder setDay(int day) {
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
