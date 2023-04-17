package com.solvd.prendiodemo;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.solvd.prendiodemo.domain.*;
import com.solvd.prendiodemo.service.LoginService;
import com.solvd.prendiodemo.validation.SuccessMessageValidation;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.UserStatusWindow;
import com.solvd.prendiodemo.web.components.accountspayable.DepSetupPopup;
import com.solvd.prendiodemo.web.components.accountspayable.DepUserPopup;
import com.solvd.prendiodemo.web.components.accountspayable.DepWatcherSetupPopup;
import com.solvd.prendiodemo.web.components.buyer.*;
import com.solvd.prendiodemo.web.components.cart.ShipToPopup;
import com.solvd.prendiodemo.web.components.profile.ImageUploadPopup;
import com.solvd.prendiodemo.web.pages.*;
import com.solvd.prendiodemo.web.pages.accountspayable.AccountsPayableSuppliersPage;
import com.solvd.prendiodemo.web.pages.accountspayable.DepartmentPage;
import com.solvd.prendiodemo.web.pages.accountspayable.VouchersPage;
import com.solvd.prendiodemo.web.pages.buyer.AddressesPage;
import com.solvd.prendiodemo.web.pages.buyer.BuyerSuppliersPage;
import com.solvd.prendiodemo.web.pages.receiver.ReceiverScanMatchPage;
import com.solvd.prendiodemo.web.pages.receiver.ReceiverScanPage;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.utils.R;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrendioTest extends AbstractTest {

    public static final String USERNAME = R.TESTDATA.get("username");
    public static final String PASSWORD = R.TESTDATA.get("password");
    private static final int HOURS_OFFSET_FROM_UTC = R.TESTDATA.getInt("hours_offset_from_utc");
    private static final DateTimeFormatter ADD_SUPPLIER_DATE_FORMAT = DateTimeFormatter
            .ofPattern(R.TESTDATA.get("add_supplier_date_format"))
            .withZone(ZoneOffset.ofHours(HOURS_OFFSET_FROM_UTC));
    private static final String TRAIL_FULL_NAME_REGEX = R.TESTDATA.get("trail_full_name_regex");
    private static final String TRAIL_DATE_REGEX = R.TESTDATA.get("trail_date_regex");
    private static final String TEMPLATE_CART_NAME = R.TESTDATA.get("template_cart_name");
    private static final String CATALOG_QUERY = R.TESTDATA.get("catalog_query");


    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies login works properly", testName = "Login Test")
    public void checkLoginTest() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.assertPageOpened();
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(USERNAME, PASSWORD);
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        dashboardPage.switchToTab(1);
        dashboardPage.assertPageOpened();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies adding supplier leaves trail", testName = "Add Supplier Trail Test")
    public void checkAddingSupplierTrailTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        String fullName = dashboardPage.getFullName();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        BuyerSuppliersPage buyerSuppliersPage = buyerPage.clickSuppliers();
        buyerSuppliersPage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, buyerSuppliersPage);
        AddSupplierPopup addSupplierPopup = buyerSuppliersPage.clickAddSupplierButton();
        Assert.assertTrue(addSupplierPopup.isVisible(), "Add Supplier popup is not visible");
        String supplierName = addSupplierPopup.fillRequiredFieldsRandomly();
        addSupplierPopup.clickSave();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Supplier Added Successfully");
        addSupplierPopup.clickClose();
        Assert.assertTrue(addSupplierPopup.isDisappeared(), "Popup didn't disappear");
        AccountPayablePage accountPayablePage = buyerSuppliersPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        AccountsPayableSuppliersPage apSuppliersPage = accountPayablePage.clickSuppliers();
        apSuppliersPage.assertPageOpened();
        apSuppliersPage = apSuppliersPage.search(supplierName);
        apSuppliersPage.assertPageOpened();
        BasePopup supplierInfoPopup = apSuppliersPage.clickSupplierByName(supplierName);
        Assert.assertTrue(supplierInfoPopup.isVisible(), "Supplier Info popup is not visible");
        String trailRecordText = supplierInfoPopup.getCreatedTrailText();
        String currentDateFormatted = ADD_SUPPLIER_DATE_FORMAT.format(Instant.now());
        Matcher trailFullNameMatcher = Pattern.compile(TRAIL_FULL_NAME_REGEX).matcher(trailRecordText);
        Matcher trailDateMatcher = Pattern.compile(TRAIL_DATE_REGEX).matcher(trailRecordText);
        Assert.assertTrue(trailDateMatcher.find(), "Date is not found in trail text");
        Assert.assertTrue(trailFullNameMatcher.find(), "Full name is not found in trail text");
        String trailDate = trailDateMatcher.group(1);
        String trailFullName = trailFullNameMatcher.group(1);
        Assert.assertEquals(trailDate, currentDateFormatted, "Trail date is not equal to current date");
        Assert.assertEquals(trailFullName, fullName, "Trail full name is not equal to current user full name");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies cart remains unchanged after duplication", testName = "Cart Template Test")
    public void checkCartTemplateTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        AllCartsPage allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        allCartsPage = allCartsPage.search(TEMPLATE_CART_NAME);
        allCartsPage.assertPageOpened();
        Assert.assertTrue(allCartsPage.isCartWithNameFound(TEMPLATE_CART_NAME), "Cart with name " + TEMPLATE_CART_NAME + " is not found");
        CartPage cartPage = allCartsPage.clickFirstCart();
        cartPage.assertPageOpened();
        if (cartPage.isInfoPopupVisible()) {
            BasePopup okPopup = cartPage.getConfirmationPopup();
            okPopup.clickConfirmationButton();
            Assert.assertTrue(okPopup.isDisappeared(), "Popup didn't disappear");
        }
        CartContents cartContents = cartPage.getCartContents();
        String templateCartId = cartPage.getId();
        BasePopup confirmationPopup = cartPage.clickDuplicateCart();
        Assert.assertTrue(confirmationPopup.isVisible(), "Confirmation popup is not visible");
        confirmationPopup.clickConfirmationButton();
        Assert.assertTrue(confirmationPopup.isDisappeared(), "Popup didn't disappear");
        cartPage = new CartPage(getDriver());
        cartPage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, cartPage);
        CartContents duplicatedContents = cartPage.getCartContents();
        Assert.assertEquals(duplicatedContents, cartContents, "Cart contents does not match");
        Assert.assertEquals(cartPage.getCartName(), TEMPLATE_CART_NAME, "Cart name does not match");
        cartPage.removeTemplateWord();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Cart items saved");
        String newCartName = cartPage.getCartName();
        String newCartId = cartPage.getId();
        Assert.assertEquals(cartPage.getCartName(), TEMPLATE_CART_NAME.replace(" Template", ""), "Cart name does not match");
        dashboardPage = cartPage.clickDashboard();
        dashboardPage.assertPageOpened();
        allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        Assert.assertTrue(allCartsPage.isCartPresent(newCartId), "Cart with id " + newCartId + " not found");
        Assert.assertEquals(allCartsPage.getCartNameById(newCartId), newCartName, "Cart id does not match");
        allCartsPage = allCartsPage.search(newCartName);
        Assert.assertEquals(allCartsPage.getCartNameById(templateCartId), TEMPLATE_CART_NAME, "Cart name does not match");
        cartPage = allCartsPage.clickById(templateCartId);
        Assert.assertEquals(cartPage.getCartContents(), cartContents, "Cart contents does not match");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies cart order creation", testName = "Cart Using Catalog Test")
    public void checkCartUsingCatalogTest() {
        final int index = 0;
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        SearchResultPage searchResultPage = dashboardPage.searchCatalog(CATALOG_QUERY);
        searchResultPage.assertPageOpened();
        Assert.assertTrue(searchResultPage.isRetrieved(), "Retrieving has not ended in retrieving timeout");
        Assert.assertTrue(searchResultPage.isItemsDisplayed(), "Items not found");
        Assert.assertTrue(searchResultPage.isAllItemTitlesContainQuery(CATALOG_QUERY), "Not all items contain query string in their title");
        ItemContents itemContents = searchResultPage.getItemContents(index);
        searchResultPage.clickAddToCart(index);
        Assert.assertTrue(searchResultPage.isCreateNewCartButtonDisplayed(index), "Create new cart button is not displayed");
        Assert.assertTrue(searchResultPage.isAddInExistingCartButtonDisplayed(index), "Add in existing cart button is not displayed");
        CartPage cartPage = searchResultPage.clickCreateNewCart(index);
        cartPage.assertPageOpened();
        ItemContents itemContentsInCart = cartPage.getFirstItemContents();
        Assert.assertEquals(itemContentsInCart, itemContents, "Item contents not equal to what was on the results page");
        ShipToPopup shipToPopup = cartPage.clickShipToButton();
        Assert.assertTrue(shipToPopup.isVisible(), "Ship To popup is not visible");
        String line1 = shipToPopup.chooseShipToAddress(0);
        cartPage.ensureLoaded();
        Assert.assertEquals(cartPage.getShipToAddressLine1Text(), line1, "Selected address Line1 does not match");
        cartPage.setSelects();
        cartPage.clickApplyToAll();
        String cartId = cartPage.getId();
        Assert.assertTrue(cartPage.isItemSelectsAsCartSelects(0), "Selects aren't set as per cart for an item");
        BasePopup reqApprovalPopup = cartPage.clickSubmitCartButton();
        if (cartPage.isInfoPopupVisible()) {
            BasePopup basePopup = cartPage.getConfirmationPopup();
            basePopup.clickConfirmationButton();
            Assert.assertTrue(basePopup.isDisappeared(), "Popup didn't disappear");
            cartPage.ensureLoaded();
        }
        Assert.assertTrue(reqApprovalPopup.isVisible(), "Requisition Approval popup is not visible");
        dashboardPage = cartPage.clickSubmitReqApproval();
        dashboardPage.assertPageOpened();
        Assert.assertTrue(dashboardPage.getOrderPreviewsCartName(0).contains(cartId), "Cart id is not found in first order");
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies department creation", testName = "Create and Edit Department Test")
    public void checkCreateAndEditDepartmentTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        AccountPayablePage accountPayablePage = dashboardPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        DepartmentPage departmentPage = accountPayablePage.clickDepartment();
        departmentPage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, departmentPage);
        Assert.assertTrue(departmentPage.isAddButtonVisible(), "Add department page is not visible");
        DepSetupPopup depSetupPopup = departmentPage.clickAddDep();
        Assert.assertTrue(depSetupPopup.isVisible(), "Department Setup popup is not visible");
        DepartmentInfo infoEntered = depSetupPopup.fillFieldsRandomly();
        depSetupPopup.clickSave();
        Assert.assertTrue(depSetupPopup.isDisappeared(), "Popup didn't disappear");
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Department Added Successfully");
        departmentPage = departmentPage.searchDepartmentByDesc(infoEntered.getDescription());
        departmentPage.assertPageOpened();
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        Assert.assertTrue(depSetupPopup.isVisible(), "Department Setup popup is not visible");
        String expectedName = ("Department Setup - " + infoEntered.getName()).toLowerCase();
        Assert.assertEquals(depSetupPopup.getHeaderText().toLowerCase(), expectedName, "Department Setup popup department name not found");
        depSetupPopup.clickWatchers();
        Assert.assertTrue(depSetupPopup.getWatchersText().contains("0"), "Watchers info doesn't show 0 watchers");//TODO: soft
        Assert.assertTrue(depSetupPopup.isWatchersTableEmpty(), "Watcher table is not empty");
        DepWatcherSetupPopup depWatcherSetupPopup = depSetupPopup.clickAddWatcher();
        Assert.assertTrue(depWatcherSetupPopup.isVisible(), "Department Watcher Setup popup is not visible");
        WatcherInfo watcherInfoEntered = depWatcherSetupPopup.selectFirstWatcher();
        depWatcherSetupPopup.clickSave();
        Assert.assertTrue(depWatcherSetupPopup.isDisappeared(), "Popup didn't disappear");
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Department Watcher Added Successfully");
        BasePopup confirmationPopup = depSetupPopup.close();
        Assert.assertTrue(confirmationPopup.isVisible(), "Confirmation popup is not visible");
        confirmationPopup.clickConfirmationButton();
        Assert.assertTrue(confirmationPopup.isDisappeared(), "Popup didn't disappear");
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepUserPopup depUserPopup = depSetupPopup.clickUsers();
        String username = depUserPopup.selectAnyUser();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Saved Successfully");
        depUserPopup.clickClose();
        depSetupPopup.ensureLoaded();
        Assert.assertTrue(depUserPopup.isDisappeared(), "Popup didn't disappear");
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepartmentInfo loadedInfo = depSetupPopup.getInfo();
        Assert.assertEquals(loadedInfo, infoEntered, "Department info does not match entered");
        depSetupPopup.clickWatchers();
        Assert.assertEquals(depSetupPopup.getWatcherInfo(), watcherInfoEntered, "Watcher info does not match entered");
        depUserPopup = depSetupPopup.clickUsers();
        Assert.assertEquals(depUserPopup.getSelectedUserName(), username, "Selected user does not match");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies receiver voucher creation", testName = "PDF File Upload and Invoice Creation Test")
    public void checkReceiverVoucherCreationTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        ReceiverPage receiverPage = dashboardPage.clickReceiver();
        receiverPage.assertPageOpened();
        ReceiverScanPage scanPage = receiverPage.clickScan();
        scanPage.assertPageOpened();
        scanPage.addUploadFile();
        Assert.assertTrue(scanPage.isIconVisible(), "Icon of the uploaded file is not visible");
        scanPage.clickUpload();
        Assert.assertTrue(scanPage.isProgressBarVisible(), "Progress bar is not visible");
        Assert.assertTrue(scanPage.isProgressBarDisappeared(), "Progress did not disappear");
        Assert.assertEquals(scanPage.getUploadStatusText(), "100%", "Upload status text doesn't show 100%");//TODO: soft
        ReceiverScanMatchPage matchPage = receiverPage.clickScanMatch();
        matchPage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, matchPage);
        Assert.assertTrue(matchPage.isFirstScanItemVisible(), "First Scan item is not visible");
        matchPage.checkFirstItem();
        SlipInfo infoEntered = matchPage.fillSlipInfoRandomly();
        matchPage.clickNext();
        matchPage.ensureLoaded();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Entry Added Successfully");
        AccountPayablePage accountPayablePage = matchPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        VouchersPage vouchersPage = accountPayablePage.clickVouchers();
        String invNumber = infoEntered.getInvoiceNumber();
        vouchersPage = vouchersPage.search(invNumber);
        Assert.assertTrue(vouchersPage.isVoucherFound(invNumber), "Voucher with invNumber " + invNumber + " not found");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies user profile update", testName = "User Profile Update Test")
    public void checkUserProfileUpdateTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        UserStatusWindow statusWindow = dashboardPage.getUserPhotoBlock().openUserStatus();
        statusWindow.assertUIObjectPresent();
        ProfilePage profilePage = statusWindow.clickViewProfileButton();
        profilePage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, profilePage);
        UserProfileInfo filledInfo = profilePage.fillProfileInfoRandomly();
        profilePage.hoverUploadButton();
        Assert.assertTrue(profilePage.isUploadButtonVisible(), "Upload button is not visible");
        ImageUploadPopup imageUploadPopup = profilePage.clickUploadButton();
        Assert.assertTrue(imageUploadPopup.isVisible(), "Image Upload popup is not visible");
        imageUploadPopup.attachSamplePhoto();
        Assert.assertTrue(imageUploadPopup.isImageAppeared(), "Image didn't appear");
        imageUploadPopup.clickUpload();
        imageUploadPopup.ensureLoaded();
        Assert.assertTrue(imageUploadPopup.isDisappeared(), "Popup didn't disappear");
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Image Uploaded Successfully");
        profilePage.clickSave();
        profilePage.ensureLoaded();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("User Profile Saved.");
        profilePage.refresh();
        profilePage = new ProfilePage(getDriver());
        profilePage.assertPageOpened();
        Assert.assertEquals(profilePage.getProfileInfo(), filledInfo, "Profile info not as entered");
        Assert.assertTrue(profilePage.isOutLinkVisible(), "Out of office sign is not visible");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies supplier creation", testName = "Creating and Editing Supplier Test")
    public void checkCreateSupplierTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, suppliersPage);
        suppliersPage.assertPageOpened();
        AddSupplierPopup addSupplierPopup = suppliersPage.clickAddSupplierButton();
        Assert.assertTrue(addSupplierPopup.isVisible(), "addSupplier popup is not visible");
        SupplierInfo infoEntered = addSupplierPopup.fillInfoRandomly();
        addSupplierPopup.clickSave();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Supplier Added Successfully");
        addSupplierPopup.getPopupLeftMenu().clickAccountNumbers();
        Assert.assertTrue(addSupplierPopup.isAccountsSectionDisplayed(), "Account section is not displayed");
        AddAccountNumbersPopup addAccountNumbersPopup = addSupplierPopup.clickAdd();
        Assert.assertTrue(addAccountNumbersPopup.isVisible(), "Add Account Numbers popup is not visible");
        AddressPopup addressPopup = addAccountNumbersPopup.clickSelectShipToAddress();
        Assert.assertTrue(addressPopup.isVisible(), "Account Numbers Address popup is not visible");
        infoEntered.setShipToLine2(addressPopup.getAddressLine2Text(0));
        addressPopup.clickFirstAddress();
        Assert.assertTrue(addressPopup.isDisappeared(), "Popup didn't disappear");
        infoEntered.setAccountNumber(addAccountNumbersPopup.fillAccountNumberRandomly());
        addAccountNumbersPopup.clickSave();
        Assert.assertTrue(addAccountNumbersPopup.isDisappeared(), "Popup didn't disappear");
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Account Added Successfully");
        addSupplierPopup.clickClose();
        Assert.assertTrue(addSupplierPopup.isDisappeared(), "Popup didn't disappear");
        String name = infoEntered.getName();
        suppliersPage = suppliersPage.search(name);
        Assert.assertTrue(suppliersPage.isSupplierFound(name), "Supplier with name " + name + " not found");
        addSupplierPopup = suppliersPage.editSupplierByName(name);
        suppliersPage.ensureLoaded();
        Assert.assertTrue(addSupplierPopup.isVisible(), "Edit Supplier popup is not visible");
        SupplierInfo infoRead = addSupplierPopup.getFullInfo();
        Assert.assertEquals(infoRead, infoEntered, "Supplier info not as entered");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies shipping address creation", testName = "Creating and Editing Shipping Address Test")
    public void checkCreateShippingAddressTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        AddressesPage addressesPage = buyerPage.clickAddresses();
        addressesPage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, addressesPage);
        AddressSetupPopup addressSetupPopup = addressesPage.clickAddAddress();
        Assert.assertTrue(addressSetupPopup.isVisible(), "Address Setup popup is not visible");
        AddressInfo addressInfo = addressSetupPopup.fillInfoRandomly();
        addressSetupPopup.clickSave();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Saved Successfully");
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.clickUsers();
        Assert.assertTrue(addressSetupPopup.isUserSectionVisible(), "User section is not visible");
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.checkAllDefault();
        Assert.assertTrue(addressSetupPopup.isAllDefaultActive(), "Not all checkboxes checked");
        addressSetupPopup.clickClose();
        addressSetupPopup.ensureLoaded();
        Assert.assertTrue(addressSetupPopup.isDisappeared(), "Popup didn't disappear");
        String addressLine1 = addressInfo.getAddressLines().get(0);
        addressesPage = addressesPage.search(addressLine1);
        Assert.assertTrue(addressesPage.isAddressFound(addressLine1), "Address not found");
        addressSetupPopup = addressesPage.editFirstAddress();
        Assert.assertTrue(addressSetupPopup.isVisible(), "Address Setup popup is not visible");
        Assert.assertEquals(addressSetupPopup.getInfo(), addressInfo, "Addresses aren't identical");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies supplier item creation", testName = "Adding Supplier Item Test")
    public void checkAddSupplierItemTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, suppliersPage);
        AddSupplierPopup supplierPopup = suppliersPage.clickAddSupplierButton();
        Assert.assertTrue(supplierPopup.isVisible(), "Supplier popup is not visible");
        supplierPopup.fillRequiredFieldsRandomly();
        supplierPopup.clickSave();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Supplier Added Successfully");
        supplierPopup.ensureLoaded();
        AddSupplierPopup catalogItemsPopup = supplierPopup.clickCatalogItems();
        buyerPage.ensureLoaded();
        Assert.assertTrue(catalogItemsPopup.isCatalogItemsSectionOpened(), "Catalog Item section is not opened");
        AddSupplierItemPopup itemPopup = catalogItemsPopup.clickAddItem();
        catalogItemsPopup.ensureLoaded();
        Assert.assertTrue(itemPopup.isVisible(), "Add Supplier Item popup is not visible");
        SupplierItemInfo infoEntered = itemPopup.fillInfoRandomly();
        Assert.assertEquals(infoEntered.getGenericDesc(), infoEntered.getDesc(), "Description is not copied");
        itemPopup.clickAddSpec();
        Assert.assertTrue(itemPopup.getSpecListSize() > 1, "Spec number has not increased");
        itemPopup.clickSave();
        itemPopup.ensureLoaded();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Catalog Items Added Successfully");
        itemPopup.clickClose();
        Assert.assertTrue(itemPopup.isDisappeared(), "Popup didn't disappear");
        supplierPopup = new BuyerSuppliersPage(getDriver()).getAddSupplierPopup();
        itemPopup = supplierPopup.clickOnAddedItemEdit();
        itemPopup.ensureLoaded();
        Assert.assertTrue(itemPopup.isVisible(), "Edit Supplier Item popup is not visible");
        SupplierItemInfo infoRead = itemPopup.getInfo();
        Assert.assertEquals(infoRead, infoEntered, "Items information is not identical");
        softAssert.assertAll();
    }
}
