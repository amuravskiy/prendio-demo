package com.solvd.prendiodemo.web.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.domain.CartContents;
import com.solvd.prendiodemo.domain.ItemContents;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.cart.ItemsForm;
import com.solvd.prendiodemo.web.components.cart.ShipToPopup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import static com.solvd.prendiodemo.constants.Constants.LOADING_BLOCK_APPEAR_TIMEOUT;

public class CartPage extends BasePage {

    @FindBy(id = "divCartId")
    private ExtendedWebElement cartId;

    @FindBy(id = "txtCartName")
    private ExtendedWebElement cartName;

    @FindBy(xpath = "//div[@id='btmCartDiv']//div[@id='btncopycart']")
    private ExtendedWebElement duplicateCartButton;

    @FindBy(xpath = "//div[@id='popupform' and //div[@class='message_information']]")
    private BasePopup okPopup;

    @FindBy(xpath = "//div[@id='popupform' and //div[@class='message_confirmation']]")
    private BasePopup confirmationPopup;

    @FindBy(id = "lnkChangeAddress")
    private ExtendedWebElement shipToButton;

    @FindBy(id = "divShipToAddress")
    private ExtendedWebElement shipToAddressText;

    @FindBy(xpath = "//div[@id='popupform' and contains(@class,'shippintolistpopup')]")
    private ShipToPopup shipToPopup;

    @FindBy(id = "ddDeptOrderType")
    private ExtendedWebElement orderTypeSelect;

    @FindBy(id = "ddDeptHead-button")
    private ExtendedWebElement departmentSelect;

    @FindBy(xpath = "//ul[@id='ddDeptHead-menu']//li[not(contains(text(), '---Select'))]")
    private ExtendedWebElement departmentToSelect;

    @FindBy(id = "ddGLAccountHead-button")
    private ExtendedWebElement glAccountSelect;

    @FindBy(xpath = "//ul[@id='ddGLAccountHead-menu']//li[not(contains(text(), '---Select'))]")
    private ExtendedWebElement glAccountToSelect;

    @FindBy(id = "ddCartProject-button")
    private ExtendedWebElement projectSelect;

    @FindBy(xpath = "//ul[@id='ddCartProject-menu']//li[not(contains(text(), '---Select'))]")
    private ExtendedWebElement projectToSelect;

    @FindBy(xpath = "//div[@id='cartheadapplytoall' and text()='Apply to all lines']")
    private ExtendedWebElement applyToAllButton;

    @FindBy(xpath = "//div[@id='allavailablecart']//div[@id='btnSubmitCart']")
    private ExtendedWebElement submitCartButton;

    @FindBy(xpath = "//div[@id='popupform' and //div[@id='divorderList']]")
    private BasePopup requisitionApprovalPopup;

    @FindBy(xpath = "//div[@id='divCatLogList']")
    private ItemsForm itemsForm;

    public CartPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(cartId);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void clickApplyToAllButton() {
        applyToAllButton.click();
    }

    public String getCartId() {
        return cartId.getText();
    }

    public boolean isInfoPopupVisible() {
        return okPopup.getRootExtendedElement().isVisible(LOADING_BLOCK_APPEAR_TIMEOUT);
    }

    public BasePopup clickDuplicateCartButton() {
        duplicateCartButton.click();
        return confirmationPopup;
    }

    public String getCartName() {
        return getValue(cartName);
    }

    public CartContents getCartContents() {
        return new CartContents(itemsForm.getAllItems());
    }

    public ItemContents getFirstItemContents() {
        return itemsForm.getItemContents(0, 0);
    }

    public void removeTemplateWord() {
        String text = cartName.getText();
        cartName.getElement().clear();
        cartName.type(text.replace(" Template", ""));
        cartId.click();
    }

    public ShipToPopup clickShipToButton() {
        shipToButton.click();
        ensureLoaded();
        return shipToPopup;
    }

    public String getShipToAddressLineOneText() {
        return shipToAddressText.getText().split("\n")[0];
    }

    public void setCartSelects() {
        selectByIndex(orderTypeSelect, 2);
        selectDepartment();
        selectGlAccount();
        selectProject();
    }

    private void selectDepartment() {
        departmentSelect.click();
        departmentToSelect.click();
        waitSuccessMessageVisible();
    }

    private void selectGlAccount() {
        glAccountSelect.click();
        glAccountToSelect.click();
        waitSuccessMessageVisible();
    }

    private void selectProject() {
        projectSelect.click();
        projectToSelect.click();
    }

    public boolean isItemSelectsAsCartSelects(int itemIndex) {
        String department = departmentSelect.getText();
        String glAcc = glAccountSelect.getText();
        String project = projectSelect.getText();
        return itemsForm.getAllItems().stream()
                .allMatch(itemContents -> itemContents.getDepartment().equals(department)
                        && itemContents.getGlAccount().equals(glAcc)
                        && itemContents.getProject().equals(project));
    }

    public BasePopup clickSubmitCartButton() {
        submitCartButton.click();
        ensureLoaded();
        return requisitionApprovalPopup;
    }

    public DashboardPage clickSubmitReqApproval() {
        requisitionApprovalPopup.clickConfirmationButton();
        ensureLoaded();
        return new DashboardPage(getDriver());
    }

    public BasePopup getConfirmationPopup() {
        return okPopup;
    }
}
