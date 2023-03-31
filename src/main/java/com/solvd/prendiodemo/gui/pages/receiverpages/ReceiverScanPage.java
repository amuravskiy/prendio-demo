package com.solvd.prendiodemo.gui.pages.receiverpages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.gui.pages.ReceiverPage;
import com.zebrunner.carina.utils.R;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ReceiverScanPage extends ReceiverPage {

    @FindBy(xpath = "//li//a[text()='Scan']")
    private ExtendedWebElement scanSectionActive;

    @FindBy(xpath = "//input[@type='file']")
    private ExtendedWebElement fileInput;

    @FindBy(id = "uploader_start")
    private ExtendedWebElement uploadButton;

    @FindBy(xpath = "//li[contains(@class,'plupload_file')]")
    private ExtendedWebElement uploadedIcon;

    @FindBy(xpath = "//div[@role='progressbar']")
    private ExtendedWebElement progressBar;

    @FindBy(className = "plupload_total_status")
    private ExtendedWebElement uploadStatus;


    public ReceiverScanPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(scanSectionActive);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void addUploadFile() {
        File file = new File("sample_slip.pdf");
        try {
            FileUtils.copyURLToFile(new URL(R.CONFIG.get("sample_slip_url")), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        boolean found = false;
        File f = null;
        //Look for the file in the files
        // You should write smart REGEX according to the filename
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println("File " + listOfFile.getName());
                if (fileName.matches("sample_slip.pdf")) {
                    f = new File(fileName);
                    found = true;
                }
            }
        }
        Assert.assertTrue(found, "Downloaded document is not found");
        fileInput.type(f.getAbsolutePath());
    }

    public void clickUpload() {
        uploadButton.click();
    }

    public boolean isIconVisible() {
        return uploadedIcon.isVisible();
    }

    public boolean isProgressBarVisible() {
        return progressBar.isVisible();
    }

    public boolean isProgressBarDisappeared() {
        return progressBar.waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public String getUploadStatusText() {
        return uploadStatus.getText();
    }
}
