package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.components.ImageUploadPopup;
import com.solvd.prendiodemo.values.UserProfileInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ProfilePage extends BasePage {

    private final By firstAvaibleDayLocator = By.xpath("//table//a[@href]");

    @FindBy(xpath = "//h2[text()='PROFILE']")
    private ExtendedWebElement profileTitle;

    @FindBy(id = "txtfirstname")
    private ExtendedWebElement firstName;

    @FindBy(id = "txtlastname")
    private ExtendedWebElement lastName;

    @FindBy(id = "basictitle")
    private ExtendedWebElement titleInput;

    @FindBy(id = "basicphonenum")
    private ExtendedWebElement phoneNumberInput;

    @FindBy(id = "basiccarrier")
    private ExtendedWebElement carrierSelect;

    @FindBy(id = "ProfileImageUpload")
    private ExtendedWebElement imageUploadButton;

    @FindBy(xpath = "//input[@value='Save']")
    private ExtendedWebElement saveButton;

    @FindBy(xpath = "//a[@title='Next']")
    private ExtendedWebElement nextMonthButton;

    @FindBy(id = "startdate")
    private ExtendedWebElement startDateInput;

    @FindBy(id = "enddate")
    private ExtendedWebElement endDateInput;

    @FindBy(xpath = "//div[@id='popupform' and child::h2[text()='Edit Profile Photo']]")
    private ImageUploadPopup imageUploadPopup;

    public ProfilePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(profileTitle);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public String getFullName() {
        return firstName.getAttribute("value") + " " + lastName.getAttribute("value");
    }

    public String fillCarrier() {
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
        return select.getAllSelectedOptions().stream()
                .filter(WebElement::isSelected)
                .findFirst()
                .orElseThrow()
                .getText();
    }

    public Pair<String, String> fillOutOfOffice() {
        startDateInput.click();
        ExtendedWebElement dayToClickOn = findExtendedWebElement(firstAvaibleDayLocator);
        dayToClickOn.isClickable();
        dayToClickOn.click();
        endDateInput.click();
        dayToClickOn = findExtendedWebElement(firstAvaibleDayLocator);
        dayToClickOn.isClickable();
        dayToClickOn.click();
        return new ImmutablePair<>(formatDate(startDateInput.getAttribute("value")),
                formatDate(endDateInput.getAttribute("value")));
    }

    private String formatDate(String date) {
        return StringUtils.stripStart(date, "0")
                .replace("/0", "/");
    }

    public String fillPhoneNumber() {
        String phoneNumber = RandomStringUtils.randomNumeric(15);
        phoneNumberInput.type(phoneNumber);
        return phoneNumber;
    }

    public String fillTitle() {
        String title = RandomStringUtils.randomAlphabetic(10);
        titleInput.type(title);
        return title;
    }

    public UserProfileInfo fillProfileInfo() {
        Pair<String, String> dates = fillOutOfOffice();
        return new UserProfileInfo.Builder()
                .setTitle(fillTitle())
                .setPhoneNumber(fillPhoneNumber())
                .setCarrier(fillCarrier())
                .setStartDate(dates.getLeft())
                .setEndDate(dates.getRight())
                .build();
    }

    public UserProfileInfo getProfileInfo() {
        String carrier = new Select(carrierSelect.getElement()).getOptions().stream()
                .filter(WebElement::isSelected)
                .findFirst()
                .orElseThrow()
                .getText();
        return new UserProfileInfo.Builder()
                .setTitle(titleInput.getAttribute("value"))
                .setPhoneNumber(phoneNumberInput.getAttribute("value"))
                .setCarrier(carrier)
                .setStartDate(formatDate(startDateInput.getAttribute("value")))
                .setEndDate(formatDate(endDateInput.getAttribute("value")))
                .build();
    }

    public void clickSave() {
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
