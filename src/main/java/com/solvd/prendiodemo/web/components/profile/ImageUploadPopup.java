package com.solvd.prendiodemo.web.components.profile;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.utils.FileUtil;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;

public class ImageUploadPopup extends BasePopup {

    private static final String SAMPLE_PHOTO_URL_PNG = R.TESTDATA.get("sample_photo_url_png");

    @FindBy(xpath = "//input[@id='file']")
    private ExtendedWebElement uploadInput;

    @FindBy(xpath = "//div[@class='imageBox' and @style]")
    private ExtendedWebElement imageBox;

    @FindBy(id = "btnCrop")
    private ExtendedWebElement setPhoto;

    public ImageUploadPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void assertImageAppeared() {
        Assert.assertTrue(imageBox.isVisible(), "Image didn't appear");
    }

    public void attachSamplePhoto() {
        uploadInput.attachFile(FileUtil.loadFileAndGetPath(SAMPLE_PHOTO_URL_PNG, new File("sample_photo.png")));
    }

    public void clickUpload() {
        setPhoto.click();
    }
}
