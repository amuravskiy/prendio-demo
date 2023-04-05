package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.solvd.prendiodemo.utils.Util;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;

public class ImageUploadPopup extends BasePopup {

    @FindBy(xpath = "//input[@id='file']")
    private ExtendedWebElement uploadInput;

    @FindBy(xpath = "//div[@class='imageBox' and @style]")
    private ExtendedWebElement imageBox;

    @FindBy(id = "btnCrop")
    private ExtendedWebElement setPhoto;

    public ImageUploadPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void imageAppeared() {
        Assert.assertTrue(imageBox.isVisible(), "Image didn't appear");
    }

    public void attachPhoto() {
        File file = Util.loadFile(R.CONFIG.get("sample_photo_url_png"), new File("sample_photo.png"));
        uploadInput.attachFile(file.getAbsolutePath());
    }

    public void clickUpload() {
        setPhoto.click();
    }
}
