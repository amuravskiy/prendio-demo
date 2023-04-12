package com.solvd.prendiodemo.domain;

import java.util.Objects;

public class UserProfileInfo {

    private String title;
    private String phoneNumber;
    private String carrier;
    private String startDate;
    private String endDate;

    public UserProfileInfo() {
    }

    private UserProfileInfo(Builder builder) {
        this.title = builder.title;
        this.phoneNumber = builder.phoneNumber;
        this.carrier = builder.carrier;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserProfileInfo that = (UserProfileInfo) o;

        if (!Objects.equals(title, that.title)) {
            return false;
        }
        if (!Objects.equals(phoneNumber, that.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(carrier, that.carrier)) {
            return false;
        }
        if (!Objects.equals(startDate, that.startDate)) {
            return false;
        }
        return Objects.equals(endDate, that.endDate);
    }

    @Override
    public String toString() {
        return "UserProfileInfo{" +
                "title='" + title + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carrier='" + carrier + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public static class Builder {

        private String title;
        private String phoneNumber;
        private String carrier;
        private String startDate;
        private String endDate;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setCarrier(String carrier) {
            this.carrier = carrier;
            return this;
        }

        public Builder setStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public UserProfileInfo build() {
            return new UserProfileInfo(this);
        }
    }
}
