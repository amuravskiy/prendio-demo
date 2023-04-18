package com.solvd.prendiodemo;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.solvd.prendiodemo.domain.*;
import com.solvd.prendiodemo.service.LoginService;
import com.solvd.prendiodemo.validation.SuccessMessageValidation;
import com.solvd.prendiodemo.web.components.BasePopup;
import com.solvd.prendiodemo.web.components.SupplierSelectPopup;
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

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies login works properly", testName = "Login Test")
    public void checkLoginTest() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(USERNAME, PASSWORD);
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        dashboardPage.switchToTab(1);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies adding supplier leaves trail", testName = "Add Supplier Trail Test")
    public void checkAddingSupplierTrailTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        String fullName = dashboardPage.getFullName();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        Assert.assertTrue(buyerPage.isPageOpened(), "Buyer page is not opened");
        BuyerSuppliersPage buyerSuppliersPage = buyerPage.clickSuppliers();
        Assert.assertTrue(buyerSuppliersPage.isPageOpened(), "Suppliers page is not opened");
        AddSupplierPopup addSupplierPopup = buyerSuppliersPage.clickAddSupplierButton();
        Assert.assertTrue(addSupplierPopup.isVisible(), "Add Supplier popup is not visible");
        String supplierName = addSupplierPopup.fillRequiredFieldsRandomly();
        addSupplierPopup.clickSaveButton();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, buyerSuppliersPage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Supplier Added Successfully");
        addSupplierPopup.clickCloseButton();
        Assert.assertTrue(addSupplierPopup.isDisappeared(), "Popup didn't disappear");
        AccountPayablePage accountPayablePage = buyerSuppliersPage.clickAccountsPayable();
        Assert.assertTrue(accountPayablePage.isPageOpened(), "Accounts Payable page is not opened");
        AccountsPayableSuppliersPage apSuppliersPage = accountPayablePage.clickSuppliersSection();
        Assert.assertTrue(apSuppliersPage.isPageOpened(), "Accounts Payable Suppliers page is not opened");
        apSuppliersPage = apSuppliersPage.searchSupplierByName(supplierName);
        Assert.assertTrue(apSuppliersPage.isPageOpened(), "Accounts Payable Suppliers page is not opened");
        BasePopup supplierInfoPopup = apSuppliersPage.clickSupplierByName(supplierName);
        Assert.assertTrue(supplierInfoPopup.isVisible(), "Supplier Info popup is not visible");
        String trailRecordText = supplierInfoPopup.getCreatedTrailText();
        String currentDateFormatted = ADD_SUPPLIER_DATE_FORMAT.format(Instant.now());
        Matcher trailFullNameMatcher = Pattern.compile(TRAIL_FULL_NAME_REGEX).matcher(trailRecordText);
        Matcher trailDateMatcher = Pattern.compile(TRAIL_DATE_REGEX).matcher(trailRecordText);
        softAssert.assertTrue(trailDateMatcher.find(), "Date is not found in trail text");
        softAssert.assertTrue(trailFullNameMatcher.find(), "Full name is not found in trail text");
        String trailDate = trailDateMatcher.group(1);
        String trailFullName = trailFullNameMatcher.group(1);
        softAssert.assertEquals(trailDate, currentDateFormatted, "Trail date is not equal to current date");
        softAssert.assertEquals(trailFullName, fullName, "Trail full name is not equal to current user full name");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies cart remains unchanged after duplication", testName = "Cart Template Test")
    public void checkCartTemplateTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        AllCartsPage allCartsPage = dashboardPage.clickViewAllCarts();
        Assert.assertTrue(allCartsPage.isPageOpened(), "All Carts page is not opened");
        allCartsPage = allCartsPage.searchCartByName(TEMPLATE_CART_NAME);
        Assert.assertTrue(allCartsPage.isPageOpened(), "All Carts page is not opened");
        Assert.assertTrue(allCartsPage.isCartWithNameFound(TEMPLATE_CART_NAME), "Cart with name " + TEMPLATE_CART_NAME + " is not found");
        CartPage cartPage = allCartsPage.clickFirstCart();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");
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
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");
        CartContents duplicatedContents = cartPage.getCartContents();
        Assert.assertEquals(duplicatedContents, cartContents, "Cart contents does not match");
        Assert.assertEquals(cartPage.getCartName(), TEMPLATE_CART_NAME, "Cart name does not match");
        cartPage.removeTemplateWord();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, cartPage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Cart items saved");
        String newCartName = cartPage.getCartName();
        String newCartId = cartPage.getId();
        Assert.assertEquals(cartPage.getCartName(), TEMPLATE_CART_NAME.replace(" Template", ""), "Cart name does not match");
        dashboardPage = cartPage.clickDashboard();
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        allCartsPage = dashboardPage.clickViewAllCarts();
        Assert.assertTrue(allCartsPage.isPageOpened(), "All Carts page is not opened");
        Assert.assertTrue(allCartsPage.isCartPresent(newCartId), "Cart with id " + newCartId + " not found");
        Assert.assertEquals(allCartsPage.getCartNameById(newCartId), newCartName, "Cart id does not match");
        allCartsPage = allCartsPage.searchCartByName(TEMPLATE_CART_NAME);
        Assert.assertEquals(allCartsPage.getCartNameById(templateCartId), TEMPLATE_CART_NAME, "Cart name does not match");
        cartPage = allCartsPage.clickById(templateCartId);
        Assert.assertEquals(cartPage.getCartContents(), cartContents, "Cart contents does not match");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies cart order creation", testName = "Cart Using Catalog Test")
    public void checkCartUsingCatalogTest() {
        final int index = 0;
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        SearchResultPage searchResultPage = dashboardPage.searchCatalog(CATALOG_QUERY);
        Assert.assertTrue(searchResultPage.isPageOpened(), "Search Result page is not opened");
        Assert.assertTrue(searchResultPage.isRetrieved(), "Retrieving has not ended in retrieving timeout");
        Assert.assertTrue(searchResultPage.isItemsDisplayed(), "Items not found");
        softAssert.assertTrue(searchResultPage.isAllItemTitlesContainQuery(CATALOG_QUERY), "Not all items contain query string in their title");
        ItemContents itemContents = searchResultPage.getItemContents(index);
        searchResultPage.clickAddToCartButton(index);
        Assert.assertTrue(searchResultPage.isCreateNewCartButtonDisplayed(index), "Create new cart button is not displayed");
        Assert.assertTrue(searchResultPage.isAddInExistingCartButtonDisplayed(index), "Add in existing cart button is not displayed");
        CartPage cartPage = searchResultPage.clickCreateNewCart(index);
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");
        ItemContents itemContentsInCart = cartPage.getFirstItemContents();
        softAssert.assertEquals(itemContentsInCart, itemContents, "Item contents not equal to what was on the results page");
        ShipToPopup shipToPopup = cartPage.clickShipToButton();
        Assert.assertTrue(shipToPopup.isVisible(), "Ship To popup is not visible");
        String line1 = shipToPopup.chooseShipToAddress(0);
        cartPage.ensureLoaded();
        Assert.assertEquals(cartPage.getShipToAddressLine1Text(), line1, "Selected address Line1 does not match");
        cartPage.setSelects();
        cartPage.clickApplyToAllButton();
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
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        Assert.assertTrue(dashboardPage.getOrderPreviewsCartName(0).contains(cartId), "Cart id is not found in first order");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies department creation", testName = "Create and Edit Department Test")
    public void checkCreateAndEditDepartmentTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        AccountPayablePage accountPayablePage = dashboardPage.clickAccountsPayable();
        Assert.assertTrue(accountPayablePage.isPageOpened(), "Account Payable page is not opened");
        DepartmentPage departmentPage = accountPayablePage.clickDepartmentSection();
        Assert.assertTrue(departmentPage.isPageOpened(), "Department page is not opened");
        Assert.assertTrue(departmentPage.isAddButtonVisible(), "Add department page is not visible");
        DepSetupPopup depSetupPopup = departmentPage.clickAddDep();
        Assert.assertTrue(depSetupPopup.isVisible(), "Department Setup popup is not visible");
        DepartmentInfo infoEntered = depSetupPopup.fillFieldsRandomly();
        depSetupPopup.clickSaveButton();
        Assert.assertTrue(depSetupPopup.isDisappeared(), "Popup didn't disappear");
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, departmentPage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Department Added Successfully");
        departmentPage = departmentPage.searchDepartmentByDesc(infoEntered.getDescription());
        Assert.assertTrue(departmentPage.isPageOpened(), "Department page is not opened");
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        Assert.assertTrue(depSetupPopup.isVisible(), "Department Setup popup is not visible");
        String expectedName = ("Department Setup - " + infoEntered.getName()).toLowerCase();
        Assert.assertEquals(depSetupPopup.getHeaderText().toLowerCase(), expectedName, "Department Setup popup department name not found");
        depSetupPopup.clickWatchersSection();
        softAssert.assertTrue(depSetupPopup.getWatchersText().contains("0"), "Watchers info doesn't show 0 watchers");
        softAssert.assertTrue(depSetupPopup.isWatchersTableEmpty(), "Watcher table is not empty");
        DepWatcherSetupPopup depWatcherSetupPopup = depSetupPopup.clickAddWatcher();
        Assert.assertTrue(depWatcherSetupPopup.isVisible(), "Department Watcher Setup popup is not visible");
        WatcherInfo watcherInfoEntered = depWatcherSetupPopup.selectFirstWatcher();
        depWatcherSetupPopup.clickSaveButton();
        Assert.assertTrue(depWatcherSetupPopup.isDisappeared(), "Popup didn't disappear");
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Department Watcher Added Successfully");
        BasePopup confirmationPopup = depSetupPopup.close();
        Assert.assertTrue(confirmationPopup.isVisible(), "Confirmation popup is not visible");
        confirmationPopup.clickConfirmationButton();
        Assert.assertTrue(confirmationPopup.isDisappeared(), "Popup didn't disappear");
        departmentPage = new DepartmentPage(getDriver());
        departmentPage = departmentPage.searchDepartmentByDesc(infoEntered.getDescription());
        Assert.assertTrue(departmentPage.isPageOpened(), "Department page is not opened");
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepUserPopup depUserPopup = depSetupPopup.clickUsers();
        String username = depUserPopup.selectAnyUser();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Saved Successfully");
        depUserPopup.clickCloseButton();
        depSetupPopup.ensureLoaded();
        Assert.assertTrue(depUserPopup.isDisappeared(), "Popup didn't disappear");
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepartmentInfo loadedInfo = depSetupPopup.getInfo();
        Assert.assertEquals(loadedInfo, infoEntered, "Department info does not match entered");
        depSetupPopup.clickWatchersSection();
        Assert.assertEquals(depSetupPopup.getWatcherInfo(), watcherInfoEntered, "Watcher info does not match entered");
        depUserPopup = depSetupPopup.clickUsers();
        Assert.assertEquals(depUserPopup.getSelectedUserName(), username, "Selected user does not match");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies receiver voucher creation", testName = "PDF File Upload and Invoice Creation Test")
    public void checkReceiverVoucherCreationTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        ReceiverPage receiverPage = dashboardPage.clickReceiver();
        Assert.assertTrue(receiverPage.isPageOpened(), "Receiver page is not opened");
        ReceiverScanPage scanPage = receiverPage.clickScanSection();
        Assert.assertTrue(scanPage.isPageOpened(), "Scan page is not opened");
        scanPage.addUploadFile();
        softAssert.assertTrue(scanPage.isIconVisible(), "Icon of the uploaded file is not visible");
        scanPage.clickUploadButton();
        softAssert.assertTrue(scanPage.isProgressBarVisible(), "Progress bar is not visible");
        softAssert.assertTrue(scanPage.isProgressBarDisappeared(), "Progress did not disappear");
        softAssert.assertEquals(scanPage.getUploadStatusText(), "100%", "Upload status text doesn't show 100%");
        ReceiverScanMatchPage matchPage = receiverPage.clickScanMatchSection();
        Assert.assertTrue(matchPage.isPageOpened(), "Match page is not opened");
        Assert.assertTrue(matchPage.isFirstScanItemVisible(), "First Scan item is not visible");
        matchPage.checkFirstItem();
        SlipInfo infoEntered = matchPage.fillSlipInfoRandomly();
        matchPage.clickNextButton();
        if (matchPage.isSupplierSelectPopupVisible()) {
            SupplierSelectPopup supplSelectPopup = matchPage.getSupplierSelectPopup();
            supplSelectPopup.selectFirstAvailable();
            Assert.assertTrue(supplSelectPopup.isDisappeared(), "Popup didn't disappear");
        }
        matchPage.ensureLoaded();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, matchPage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Entry Added Successfully");
        AccountPayablePage accountPayablePage = matchPage.clickAccountsPayable();
        Assert.assertTrue(accountPayablePage.isPageOpened(), "Account Payable page is not opened");
        VouchersPage vouchersPage = accountPayablePage.clickVouchersSection();
        String invNumber = infoEntered.getInvoiceNumber();
        vouchersPage = vouchersPage.searchVoucher(invNumber);
        Assert.assertTrue(vouchersPage.isVoucherFound(invNumber), "Voucher with invNumber " + invNumber + " not found");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies user profile update", testName = "User Profile Update Test")
    public void checkUserProfileUpdateTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        UserStatusWindow statusWindow = dashboardPage.getUserPhotoBlock().openUserStatus();
        statusWindow.assertUIObjectPresent();
        ProfilePage profilePage = statusWindow.clickViewProfileButton();
        Assert.assertTrue(profilePage.isPageOpened(), "Profile page is not opened");
        UserProfileInfo filledInfo = profilePage.fillProfileInfoRandomly();
        profilePage.hoverUploadButton();
        Assert.assertTrue(profilePage.isUploadButtonVisible(), "Upload button is not visible");
        ImageUploadPopup imageUploadPopup = profilePage.clickUploadButton();
        Assert.assertTrue(imageUploadPopup.isVisible(), "Image Upload popup is not visible");
        imageUploadPopup.attachSamplePhoto();
        softAssert.assertTrue(imageUploadPopup.isImageAppeared(), "Image didn't appear");
        imageUploadPopup.clickUploadButton();
        imageUploadPopup.ensureLoaded();
        Assert.assertTrue(imageUploadPopup.isDisappeared(), "Popup didn't disappear");
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, profilePage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Image Uploaded Successfully");
        profilePage.clickSaveButton();
        profilePage.ensureLoaded();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("User Profile Saved.");
        getDriver().navigate().refresh();
        profilePage = new ProfilePage(getDriver());
        Assert.assertTrue(profilePage.isPageOpened(), "Profile page is not opened");
        Assert.assertEquals(profilePage.getProfileInfo(), filledInfo, "Profile info not as entered");
        Assert.assertTrue(profilePage.isOutLinkVisible(), "Out of office sign is not visible");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies supplier creation", testName = "Creating and Editing Supplier Test")
    public void checkCreateSupplierTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        Assert.assertTrue(suppliersPage.isPageOpened(), "Suppliers page is not opened");
        AddSupplierPopup addSupplierPopup = suppliersPage.clickAddSupplierButton();
        Assert.assertTrue(addSupplierPopup.isVisible(), "addSupplier popup is not visible");
        SupplierInfo infoEntered = addSupplierPopup.fillInfoRandomly();
        addSupplierPopup.clickSaveButton();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, suppliersPage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Supplier Added Successfully");
        addSupplierPopup.getPopupLeftMenu().clickAccountNumbersSection();
        Assert.assertTrue(addSupplierPopup.isAccountsSectionDisplayed(), "Account section is not displayed");
        AddAccountNumbersPopup addAccountNumbersPopup = addSupplierPopup.clickAdd();
        Assert.assertTrue(addAccountNumbersPopup.isVisible(), "Add Account Numbers popup is not visible");
        AddressPopup addressPopup = addAccountNumbersPopup.clickSelectShipToAddress();
        Assert.assertTrue(addressPopup.isVisible(), "Account Numbers Address popup is not visible");
        infoEntered.setShipToLine2(addressPopup.getAddressLine2Text(0));
        addressPopup.clickFirstAddress();
        Assert.assertTrue(addressPopup.isDisappeared(), "Popup didn't disappear");
        infoEntered.setAccountNumber(addAccountNumbersPopup.fillAccountNumberRandomly());
        addAccountNumbersPopup.clickSaveButton();
        Assert.assertTrue(addAccountNumbersPopup.isDisappeared(), "Popup didn't disappear");
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Account Added Successfully");
        addSupplierPopup.clickCloseButton();
        Assert.assertTrue(addSupplierPopup.isDisappeared(), "Popup didn't disappear");
        String name = infoEntered.getName();
        suppliersPage = suppliersPage.searchSupplierByName(name);
        Assert.assertTrue(suppliersPage.isSupplierFound(name), "Supplier with name " + name + " not found");
        addSupplierPopup = suppliersPage.editSupplierByName(name);
        suppliersPage.ensureLoaded();
        Assert.assertTrue(addSupplierPopup.isVisible(), "Edit Supplier popup is not visible");
        SupplierInfo infoRead = addSupplierPopup.getFullInfo();
        Assert.assertEquals(infoRead, infoEntered, "Supplier info not as entered");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies shipping address creation", testName = "Creating and Editing Shipping Address Test")
    public void checkCreateShippingAddressTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        Assert.assertTrue(buyerPage.isPageOpened(), "Buyer page is not opened");
        AddressesPage addressesPage = buyerPage.clickAddresses();
        Assert.assertTrue(addressesPage.isPageOpened(), "Addresses page is not opened");
        AddressSetupPopup addressSetupPopup = addressesPage.clickAddAddress();
        Assert.assertTrue(addressSetupPopup.isVisible(), "Address Setup popup is not visible");
        AddressInfo addressInfo = addressSetupPopup.fillInfoRandomly();
        addressSetupPopup.clickSaveButton();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, addressesPage);
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Saved Successfully");
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.clickUsersSection();
        Assert.assertTrue(addressSetupPopup.isUserSectionVisible(), "User section is not visible");
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.checkAllDefault();
        softAssert.assertTrue(addressSetupPopup.isAllDefaultActive(), "Not all checkboxes checked");
        addressSetupPopup.clickCloseButton();
        addressSetupPopup.ensureLoaded();
        Assert.assertTrue(addressSetupPopup.isDisappeared(), "Popup didn't disappear");
        String addressLine1 = addressInfo.getAddressLines().get(0);
        addressesPage = addressesPage.searchAddressByAddressLine(addressLine1);
        Assert.assertTrue(addressesPage.isAddressFound(addressLine1), "Address not found");
        addressSetupPopup = addressesPage.editFirstAddress();
        Assert.assertTrue(addressSetupPopup.isVisible(), "Address Setup popup is not visible");
        Assert.assertEquals(addressSetupPopup.getInfo(), addressInfo, "Addresses aren't identical");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "anazarenko")
    @Test(description = "Verifies supplier item creation", testName = "Adding Supplier Item Test")
    public void checkAddSupplierItemTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isPageOpened(), "Dashboard page is not opened");
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        Assert.assertTrue(suppliersPage.isPageOpened(), "Suppliers page is not opened");
        AddSupplierPopup supplierPopup = suppliersPage.clickAddSupplierButton();
        Assert.assertTrue(supplierPopup.isVisible(), "Supplier popup is not visible");
        supplierPopup.fillRequiredFieldsRandomly();
        supplierPopup.clickSaveButton();
        SuccessMessageValidation successMessageValidation = new SuccessMessageValidation(softAssert, suppliersPage);
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
        softAssert.assertEquals(infoEntered.getGenericDesc(), infoEntered.getDesc(), "Description is not copied");
        itemPopup.clickAddSpecButton();
        Assert.assertTrue(itemPopup.getSpecListSize() > 1, "Spec number has not increased");
        itemPopup.clickSaveButton();
        itemPopup.ensureLoaded();
        successMessageValidation.validateSuccessMessageVisible();
        successMessageValidation.validateSuccessMessageText("Catalog Items Added Successfully");
        itemPopup.clickCloseButton();
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
