package com.solvd.prendiodemo.gui.pages;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.PageOpeningStrategy;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.gui.components.ConfirmationPopup;
import com.solvd.prendiodemo.gui.components.OKPopup;
import com.solvd.prendiodemo.gui.components.ReqApprovalPopup;
import com.solvd.prendiodemo.gui.components.ShipToPopup;
import com.solvd.prendiodemo.values.CartContents;
import com.solvd.prendiodemo.values.ItemContents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    @FindBy(id = "divCartId")
    private ExtendedWebElement cartId;

    @FindBy(id = "txtCartName")
    private ExtendedWebElement cartName;

    @FindBy(xpath = "//div[@id='btmCartDiv']//div[@id='btncopycart']")
    private ExtendedWebElement duplicateCartButton;

    @FindBy(xpath = "//div[@id='confirmpopup' and //child::input[@value='Ok']]")
    private OKPopup okPopup;

    @FindBy(xpath = "//div[@id='popupform' and h2[text()='CONFIRMATION']]")
    private ConfirmationPopup confirmationPopup;

    @FindBy(id = "txtPartNo")
    private List<ExtendedWebElement> partNumbers;

    @FindBy(id = "txtPartDesc")
    private List<ExtendedWebElement> descriptions;

    @FindBy(id = "ttlPrice")
    private List<ExtendedWebElement> totals;

    @FindBy(id = "spnsuppName")
    private List<ExtendedWebElement> supplierNames;

    @FindBy(id = "ddCurrency")
    private List<ExtendedWebElement> currencySelects;

    @FindBy(id = "lnkChangeAddress")
    private ExtendedWebElement shipToButton;

    @FindBy(id = "divShipToAddress")
    private ExtendedWebElement shipToAddressText;

    @FindBy(xpath = "//div[@id='popupform' and h2[text()='Ship To Address List']]")
    private ShipToPopup shipToPopup;

    @FindBy(id = "ddDeptOrderType")
    private ExtendedWebElement orderTypeSelect;

    @FindBy(id = "ddDeptHead-button")
    private ExtendedWebElement departmentSelect;

    @FindBy(xpath = "//ul[@id='ddDeptHead-menu']//li[not(contains(text(), '---Select'))]")
    private ExtendedWebElement departmentToSelect;

    @FindBy(id = "ddGLAccountHead-button")
    private ExtendedWebElement GLAccountSelect;

    @FindBy(xpath = "//ul[@id='ddGLAccountHead-menu']//li[not(contains(text(), '---Select'))]")
    private ExtendedWebElement GLAccountToSelect;

    @FindBy(id = "ddCartProject-button")
    private ExtendedWebElement projectSelect;

    @FindBy(xpath = "//ul[@id='ddCartProject-menu']//li[not(contains(text(), '---Select'))]")
    private ExtendedWebElement projectToSelect;

    @FindBy(xpath = "//div[@id='cartheadapplytoall' and text()='Apply to all lines']")
    private ExtendedWebElement applyToAllButton;

    @FindBy(id = "ddDeptCart")
    private List<ExtendedWebElement> itemsDepartment;

    @FindBy(id = "ddGLAccount")
    private List<ExtendedWebElement> itemsGlAccount;

    @FindBy(id = "ddProjectCart")
    private List<ExtendedWebElement> itemsProject;

    @FindBy(xpath = "//div[@id='allavailablecart']//div[@id='btnSubmitCart']")
    private ExtendedWebElement submitCartButton;

    @FindBy(xpath = "//div[@id='popupform' and h2[text()='Requisition  Approval']]")
    private ReqApprovalPopup requisitionApprovalPopup;

    public CartPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(cartId);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public void clickApplyToAll() {
        applyToAllButton.click();
    }

    public void clickOkOnInfoPopup() {
        okPopup.clickOk();
    }

    public String getId() {
        return cartId.getText();
    }

    public boolean isInfoPopupVisible() {
        return okPopup.isVisible();
    }

    public boolean isInfoPopupDisappeared() {
        return okPopup.getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public void clickOkOnConfirmPopup() {
        confirmationPopup.clickOK();
    }

    public boolean isConfirmPopupVisible() {
        return confirmationPopup.isVisible();
    }

    public boolean isConfirmPopupDisappeared() {
        return confirmationPopup.getRootExtendedElement().waitUntilElementDisappear(EXPLICIT_TIMEOUT);
    }

    public ConfirmationPopup getConfirmationPopup() {
        return confirmationPopup;
    }

    public void clickDuplicateCart() {
        duplicateCartButton.click();
    }

    public String getCartName() {
        return cartName.getAttribute("value");
    }

    public CartContents getCartContents() {
        CartContents cartContents = new CartContents();
        cartContents.setPartNumbers(partNumbers.stream().map(ExtendedWebElement::getText).collect(Collectors.toList()));
        cartContents.setDescriptions(descriptions.stream().map(ExtendedWebElement::getText).collect(Collectors.toList()));
        cartContents.setTotals(totals.stream().map(ExtendedWebElement::getText).collect(Collectors.toList()));
        return cartContents;
    }

    public ItemContents getFirstItemContents() {
        ItemContents itemContents = getCartContents().getItemContents(0);
        itemContents.setCurrencyType(currencySelects.get(0).getAttribute("title"));
        itemContents.setSupplier(supplierNames.get(0).getText().trim());
        return itemContents;
    }

    public void removeTemplateWord() {
        String text = cartName.getText();
        cartName.getElement().clear();
        cartName.type(text.replace(" Template", ""));
        cartId.click();
    }

    public void clickShipToButton() {
        shipToButton.click();
        ensureLoaded();
    }

    public boolean isShipToPopupVisible() {
        return shipToPopup.isVisible();
    }

    public String getShipToPopupTitle() {
        return shipToPopup.getHeaderText();
    }

    public String getAddressLine1(int index) {
        return shipToPopup.getAddressLine1Text(index);
    }

    public void chooseShipToAddress(int index) {
        shipToPopup.clickAddress(index);
        ensureLoaded();
    }

    public String getshipToAddressText() {
        return shipToAddressText.getText();
    }

    public void setSelects() {
        Util.selectByIndex(orderTypeSelect, 2);
        departmentSelect.click();
        departmentToSelect.click();
        waitSuccessMessageVisible();
        GLAccountSelect.click();
        GLAccountToSelect.click();
        waitSuccessMessageVisible();
        projectSelect.click();
        projectToSelect.click();
    }

    public boolean isReqApprovalPopupVisible() {
        return requisitionApprovalPopup.isVisible();
    }

    public String getReqApprovalPopupTitle() {
        return requisitionApprovalPopup.getHeaderText();
    }

    public boolean isItemSelectsAsCartSelects(int itemIndex) {
        return departmentSelect.getText().equals(itemsDepartment.get(itemIndex).getSelectedValue())
                && GLAccountSelect.getText().equals(itemsGlAccount.get(itemIndex).getSelectedValue())
                && projectSelect.getText().equals(itemsProject.get(itemIndex).getSelectedValue());
    }

    public void clickSubmitCartButton() {
        submitCartButton.click();
        ensureLoaded();
    }

    public DashboardPage clickSubmitReqApproval() {
        requisitionApprovalPopup.clickSubmit();
        ensureLoaded();
        return new DashboardPage(driver);
    }

    public boolean isSubmitReqApprovalClickable() {
        return requisitionApprovalPopup.isClickable();
    }
}
