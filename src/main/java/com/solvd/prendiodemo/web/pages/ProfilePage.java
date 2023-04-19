package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.UserProfileInfo;
import com.solvd.prendiodemo.utils.DateUtil;
import com.solvd.prendiodemo.web.components.common.CalendarForm;
import com.solvd.prendiodemo.web.components.profile.ImageUploadPopup;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ProfilePage extends BasePage {

    @FindBy(xpath = "//h2[text()='PROFILE']")
    private ExtendedWebElement profileTitle;

    @FindBy(id = "txtfirstname")
    private ExtendedWebElement firstNameField;

    @FindBy(id = "txtlastname")
    private ExtendedWebElement lastNameField;

    @FindBy(id = "basictitle")
    private ExtendedWebElement titleField;

    @FindBy(id = "basicphonenum")
    private ExtendedWebElement phoneNumberField;

    @FindBy(id = "basiccarrier")
    private ExtendedWebElement carrierSelect;

    @FindBy(id = "ProfileImageUpload")
    private ExtendedWebElement imageUploadButton;

    @FindBy(xpath = "//input[@value='Save']")
    private ExtendedWebElement saveButton;

    @FindBy(id = "ui-datepicker-div")
    private CalendarForm calendarForm;

    @FindBy(id = "startdate")
    private ExtendedWebElement startDateInput;

    @FindBy(id = "enddate")
    private ExtendedWebElement endDateInput;

    @FindBy(xpath = "//div[@id='popupform' and contains(@class, 'editprofilephoto')]")
    private ImageUploadPopup imageUploadPopup;

    public ProfilePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(profileTitle);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public String getCurrentUserFullName() {
        return getValue(firstNameField) + " " + getValue(lastNameField);
    }

    public String selectOtherCarrier() {
        Select select = new Select(carrierSelect.getElement());
        String valueToSelect = select.getOptions().stream()
                .filter(WebElement::isEnabled)
                .filter(option -> !option.isSelected())
                .filter(option -> !option.getAttribute("value").equals("0"))
                .filter(option -> !option.getAttribute("value").equals(select.getFirstSelectedOption().getAttribute("value")))
                .findFirst()
                .orElseThrow()
                .getAttribute("value");
        select.selectByValue(valueToSelect);
        return getSelectedOptionText(carrierSelect);
    }

    public Pair<String, String> fillOutOfOffice() {
        return new ImmutablePair<>(fillStartDate(), fillEndDate());
    }

    private String fillStartDate() {
        startDateInput.click();
        calendarForm = new CalendarForm(getDriver(), getDriver());
        calendarForm.waitDateToBeVisible();
        calendarForm.clickFirstAvailableDateButton();
        return DateUtil.formatDateProfile(getValue(startDateInput));
    }

    private String fillEndDate() {
        endDateInput.click();
        calendarForm = new CalendarForm(getDriver(), getDriver());
        calendarForm.waitDateToBeVisible();
        calendarForm.clickFirstAvailableDateButton();
        return DateUtil.formatDateProfile(getValue(endDateInput));
    }

    public String fillPhoneNumberRandomly() {
        String phoneNumber = RandomStringUtils.randomNumeric(15);
        phoneNumberField.type(phoneNumber);
        return phoneNumber;
    }

    public String fillTitleRandomly() {
        String title = RandomStringUtils.randomAlphabetic(10);
        titleField.type(title);
        return title;
    }

    public UserProfileInfo fillProfileInfoRandomly() {
        Pair<String, String> dates = fillOutOfOffice();
        return UserProfileInfo.builder()
                .title(fillTitleRandomly())
                .phoneNumber(fillPhoneNumberRandomly())
                .carrier(selectOtherCarrier())
                .startDate(dates.getLeft())
                .endDate(dates.getRight())
                .build();
    }

    public UserProfileInfo getProfileInfo() {
        return UserProfileInfo.builder()
                .title(getValue(titleField))
                .phoneNumber(getValue(phoneNumberField))
                .carrier(getSelectedOptionText(carrierSelect))
                .startDate(DateUtil.formatDateProfile(getValue(startDateInput)))
                .endDate(DateUtil.formatDateProfile(getValue(endDateInput)))
                .build();
    }

    public void clickSaveButton() {
        saveButton.click();
        ensureLoaded();
    }

    public void hoverUploadButton() {
        imageUploadButton.hover();
    }

    public ImageUploadPopup clickUploadButton() {
        imageUploadButton.click();
        return imageUploadPopup;
    }

    public boolean isUploadButtonVisible() {
        return imageUploadButton.isVisible();
    }
}
