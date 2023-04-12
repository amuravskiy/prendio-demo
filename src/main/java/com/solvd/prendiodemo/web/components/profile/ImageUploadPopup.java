package com.solvd.prendiodemo.web.components.profile;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.utils.FileUtil;
import com.solvd.prendiodemo.web.components.BasePopup;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public class ImageUploadPopup extends BasePopup {

    private static final String SAMPLE_PHOTO_URL_PNG = "https://dev-aws-procure.prendio.com/assets/images/prendio.png";

    @FindBy(xpath = "//input[@id='file']")
    private ExtendedWebElement uploadInput;

    @FindBy(xpath = "//div[@class='imageBox' and @style]")
    private ExtendedWebElement imageBox;

    @FindBy(id = "btnCrop")
    private ExtendedWebElement setPhoto;

    public ImageUploadPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isImageAppeared() {
        return imageBox.isVisible();
    }

    public void attachSamplePhoto() {
        uploadInput.attachFile(FileUtil.loadFileAndGetPath(SAMPLE_PHOTO_URL_PNG, new File("sample_photo.png")));
    }

    public void clickUploadButton() {
        setPhoto.click();
    }
}
