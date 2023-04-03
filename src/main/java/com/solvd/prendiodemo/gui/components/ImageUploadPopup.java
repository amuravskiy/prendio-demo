package com.solvd.prendiodemo.gui.components;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.utils.R;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUploadPopup extends BasePopup {

    @FindBy(xpath = "//input[@id='file']")
    private ExtendedWebElement uploadInput;

    @FindBy(xpath = "//div[@class='imageBox' and @style]")
    private ExtendedWebElement imageBox;

    @FindBy(id = "btnCrop")
    private ExtendedWebElement setPhoto;

    @FindBy(id = "btnZoomIn")
    private ExtendedWebElement zoomIn;

    public ImageUploadPopup(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean imageAppeared() {
        return imageBox.isVisible();
    }

    public void attachPhoto() {
        File file = new File("sample_photo.png");
        try {
            FileUtils.copyURLToFile(new URL(R.CONFIG.get("sample_photo_url_png")), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uploadInput.attachFile(file.getAbsolutePath());
    }

    public void clickUpload() {
        setPhoto.click();
    }
}
