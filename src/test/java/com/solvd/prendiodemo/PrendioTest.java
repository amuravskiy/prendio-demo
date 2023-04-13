package com.solvd.prendiodemo;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.solvd.prendiodemo.domain.*;
import com.solvd.prendiodemo.service.LoginService;
import com.solvd.prendiodemo.utils.FileUtil;
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
import java.util.Map;
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
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        String fullName = dashboardPage.getFullName();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        BuyerSuppliersPage buyerSuppliersPage = buyerPage.clickSuppliers();
        buyerSuppliersPage.assertPageOpened();
        AddSupplierPopup addSupplierPopup = buyerSuppliersPage.clickAddSupplierButton();
        addSupplierPopup.assertVisibleWithTitle("Supplier Detail");
        String supplierName = addSupplierPopup.fillRequiredFieldsRandomly();
        addSupplierPopup.clickSave();
        buyerSuppliersPage.assertSuccessMessageVisibleWithText("Supplier Added Successfully", softAssert);
        addSupplierPopup.clickClose();
        addSupplierPopup.assertDisappeared();
        AccountPayablePage accountPayablePage = buyerSuppliersPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        AccountsPayableSuppliersPage apSuppliersPage = accountPayablePage.clickSuppliers();
        apSuppliersPage.assertPageOpened();
        apSuppliersPage = apSuppliersPage.search(supplierName);
        apSuppliersPage.assertPageOpened();
        BasePopup supplierInfoPopup = apSuppliersPage.clickSupplierByName(supplierName);
        supplierInfoPopup.assertVisible();
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
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        AllCartsPage allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        allCartsPage = allCartsPage.search(TEMPLATE_CART_NAME);
        allCartsPage.assertPageOpened();
        allCartsPage.assertCartWithNameFound(TEMPLATE_CART_NAME);
        CartPage cartPage = allCartsPage.clickFirstCart();
        cartPage.assertPageOpened();
        if (cartPage.isInfoPopupVisible()) {
            BasePopup okPopup = cartPage.getConfirmationPopup();
            okPopup.clickConfirmationButton();
            okPopup.assertDisappeared();
        }
        CartContents cartContents = cartPage.getCartContents();
        String templateCartId = cartPage.getId();
        BasePopup confirmationPopup = cartPage.clickDuplicateCart();
        confirmationPopup.assertVisibleWithTitle("CONFIRMATION");
        confirmationPopup.clickConfirmationButton();
        confirmationPopup.assertDisappeared();
        cartPage = new CartPage(getDriver());
        cartPage.assertPageOpened();
        CartContents duplicatedContents = cartPage.getCartContents();
        Assert.assertEquals(duplicatedContents, cartContents, "Cart contents does not match");
        cartPage.assertCartNameEquals(TEMPLATE_CART_NAME);
        cartPage.removeTemplateWord();
        cartPage.assertSuccessMessageVisibleWithText("Cart items saved", softAssert);
        String newCartName = cartPage.getCartName();
        String newCartId = cartPage.getId();
        cartPage.assertCartNameEquals(TEMPLATE_CART_NAME.replace(" Template", ""));
        dashboardPage = cartPage.clickDashboard();
        dashboardPage.assertPageOpened();
        allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        allCartsPage.assertCartPresent(newCartId);
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
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        SearchResultPage searchResultPage = dashboardPage.searchCatalog(CATALOG_QUERY);
        searchResultPage.assertPageOpened();
        searchResultPage.assertRetrieved();
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
        shipToPopup.assertVisibleWithTitle("Ship To Address List");
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
            basePopup.assertDisappeared();
            cartPage.ensureLoaded();
        }
        reqApprovalPopup.assertVisibleWithTitle("Requisition Approval");
        dashboardPage = cartPage.clickSubmitReqApproval();
        dashboardPage.assertPageOpened();
        Assert.assertTrue(dashboardPage.getOrderPreviewsCartName(0).contains(cartId), "Cart id is not found in first order");
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies department creation", testName = "Create and Edit Department Test")
    public void checkCreateAndEditDepartmentTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        AccountPayablePage accountPayablePage = dashboardPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        DepartmentPage departmentPage = accountPayablePage.clickDepartment();
        departmentPage.assertPageOpened();
        Assert.assertTrue(departmentPage.isAddButtonVisible(), "Add department page is not visible");
        DepSetupPopup depSetupPopup = departmentPage.clickAddDep();
        depSetupPopup.assertVisibleWithTitle("Department Setup");
        DepInfo infoEntered = depSetupPopup.fillFieldsRandomly();
        depSetupPopup.clickSave();
        depSetupPopup.assertDisappeared();
        departmentPage.assertSuccessMessageVisibleWithText("Department Added Successfully", softAssert);
        departmentPage = departmentPage.searchDepartmentByDesc(infoEntered.getDesc());
        departmentPage.assertPageOpened();
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        depSetupPopup.assertVisible();
        String expectedName = ("Department Setup - " + infoEntered.getName()).toLowerCase();
        Assert.assertEquals(depSetupPopup.getHeaderText().toLowerCase(), expectedName, "Department Setup popup department name not found");
        depSetupPopup.clickWatchers();
        Assert.assertTrue(depSetupPopup.getWatchersText().contains("0"));
        Assert.assertTrue(depSetupPopup.isWatchersTableEmpty(), "Watcher table is not empty");
        DepWatcherSetupPopup depWatcherSetupPopup = depSetupPopup.clickAddWatcher();
        depWatcherSetupPopup.assertVisibleWithTitle("Department Watcher Setup");
        WatcherInfo watcherInfoEntered = depWatcherSetupPopup.selectFirstWatcher();
        depWatcherSetupPopup.clickSave();
        depWatcherSetupPopup.assertDisappeared();
        departmentPage.assertSuccessMessageVisibleWithText("Department Watcher Added Successfully", softAssert);
        BasePopup confirmationPopup = depSetupPopup.close();
        confirmationPopup.assertVisibleWithTitle("CONFIRMATION");
        confirmationPopup.clickConfirmationButton();
        confirmationPopup.assertDisappeared();
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepUserPopup depUserPopup = depSetupPopup.clickUsers();
        String username = depUserPopup.selectAnyUser();
        departmentPage.assertSuccessMessageVisibleWithText("Saved Successfully", softAssert);
        depUserPopup.clickClose();
        depSetupPopup.ensureLoaded();
        depUserPopup.assertDisappeared();
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepInfo loadedInfo = depSetupPopup.getInfo();
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
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        ReceiverPage receiverPage = dashboardPage.clickReceiver();
        receiverPage.assertPageOpened();
        ReceiverScanPage scanPage = receiverPage.clickScan();
        scanPage.assertPageOpened();
        scanPage.addUploadFile();
        scanPage.assertIconVisible();
        scanPage.clickUpload();
        Assert.assertTrue(scanPage.isProgressBarVisible(), "Progress bar is not visible");
        Assert.assertTrue(scanPage.isProgressBarDisappeared(), "Progress did not disappear");
        Assert.assertEquals(scanPage.getUploadStatusText(), "100%");
        ReceiverScanMatchPage matchPage = receiverPage.clickScanMatch();
        matchPage.assertPageOpened();
        matchPage.assertFirstScanItemVisible();
        matchPage.checkFirstItem();
        SlipInfo infoEntered = matchPage.fillSlipInfoRandomly();
        matchPage.clickNext();
        matchPage.ensureLoaded();
        matchPage.assertSuccessMessageVisibleWithText("Entry Added Successfully", softAssert);
        AccountPayablePage accountPayablePage = matchPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        VouchersPage vouchersPage = accountPayablePage.clickVouchers();
        String invNumber = infoEntered.getInvoiceNumber();
        vouchersPage = vouchersPage.search(invNumber);
        vouchersPage.assertVoucherFound(invNumber);
        Assert.assertEquals(vouchersPage.getVoucherEntryByInvNumber(invNumber).getInvNumberText(), invNumber);
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies user profile update", testName = "User Profile Update Test")
    public void checkUserProfileUpdateTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        UserStatusWindow statusWindow = dashboardPage.getUserPhotoBlock().openUserStatus();
        statusWindow.assertUIObjectPresent();
        ProfilePage profilePage = statusWindow.clickViewProfileButton();
        profilePage.assertPageOpened();
        UserProfileInfo filledInfo = profilePage.fillProfileInfoRandomly();
        profilePage.hoverUploadButton();
        Assert.assertTrue(profilePage.isUploadButtonVisible(), "Upload button is not visible");
        ImageUploadPopup imageUploadPopup = profilePage.clickUploadButton();
        imageUploadPopup.assertVisibleWithTitle("Edit Profile Photo");
        imageUploadPopup.attachSamplePhoto();
        imageUploadPopup.imageAppeared();
        imageUploadPopup.clickUpload();
        imageUploadPopup.ensureLoaded();
        imageUploadPopup.assertDisappeared();
        profilePage.assertSuccessMessageVisibleWithText("Image Uploaded Successfully", softAssert);
        profilePage.clickSave();
        profilePage.ensureLoaded();
        profilePage.assertSuccessMessageVisibleWithText("User Profile Saved.", softAssert);
        profilePage.refresh();
        profilePage = new ProfilePage(getDriver());
        profilePage.assertPageOpened();
        Assert.assertEquals(profilePage.getProfileInfo(), filledInfo);
        Assert.assertTrue(profilePage.isOutLinkVisible(), "Out of office sign is not visible");
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies supplier creation", testName = "Creating and Editing Supplier Test")
    public void checkCreateSupplierTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        AddSupplierPopup addSupplierPopup = suppliersPage.clickAddSupplierButton();
        addSupplierPopup.assertVisibleWithTitle("Supplier Detail");
        Map<String, String> infoEntered = addSupplierPopup.fillInfoRandomly();
        addSupplierPopup.clickSave();
        suppliersPage.assertSuccessMessageVisibleWithText("Supplier Added Successfully", softAssert);
        addSupplierPopup.getPopupLeftMenu().clickAccountNumbers();
        Assert.assertTrue(addSupplierPopup.isAccountsSectionDisplayed(), "Account section is not displayed");
        AddAccountNumbersPopup addAccountNumbersPopup = addSupplierPopup.clickAdd();
        addAccountNumbersPopup.assertVisibleWithTitle("Add Account Numbers");
        AddressPopup addressPopup = addAccountNumbersPopup.clickSelectShipToAddress();
        addressPopup.assertVisible();
        infoEntered.put("shipToLine2", addressPopup.getAddressLine2Text(0));
        addressPopup.clickAddress(0);
        addressPopup.assertDisappeared();
        infoEntered.put("accountNumber", addAccountNumbersPopup.fillAccountNumberRandomly());
        addAccountNumbersPopup.clickSave();
        addAccountNumbersPopup.assertDisappeared();
        suppliersPage.assertSuccessMessageVisibleWithText("Account Added Successfully", softAssert);
        addSupplierPopup.clickClose();
        addSupplierPopup.assertDisappeared();
        String name = infoEntered.get("name");
        suppliersPage = suppliersPage.search(name);
        suppliersPage.assertSupplierFound(name);
        addSupplierPopup = suppliersPage.editSupplierByName(name);
        suppliersPage.ensureLoaded();
        addSupplierPopup.assertVisible();
        Map<String, String> infoRead = addSupplierPopup.getFullInfo();
        Assert.assertEquals(infoRead, infoEntered);
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies shipping address creation", testName = "Creating and Editing Shipping Address Test")
    public void checkCreateShippingAddressTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        AddressesPage addressesPage = buyerPage.clickAddresses();
        addressesPage.assertPageOpened();
        AddressSetupPopup addressSetupPopup = addressesPage.clickAddAddress();
        addressSetupPopup.assertVisibleWithTitle("Address Setup");
        Map<String, String> addressInfo = addressSetupPopup.fillInfoRandomly();
        addressSetupPopup.clickSave();
        addressesPage.assertSuccessMessageVisibleWithText("Saved Successfully", softAssert);
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.clickUsers();
        addressSetupPopup.assertUserSectionVisible();
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.checkAllDefault();
        addressSetupPopup.assertAllDefaultActive();
        addressSetupPopup.clickClose();
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.assertDisappeared();
        addressesPage = addressesPage.search(addressInfo.get("line1"));
        addressesPage.assertAddressFound(addressInfo.get("line1"));
        addressSetupPopup = addressesPage.editFirstAddress();
        addressSetupPopup.assertVisibleWithTitle("Address Setup");
        Assert.assertEquals(addressSetupPopup.getInfo(), addressInfo);
        softAssert.assertAll();
    }

    @MethodOwner(owner = "Andrew Nazarenko")
    @Test(description = "Verifies supplier item creation", testName = "Adding Supplier Item Test")
    public void checkAddSupplierItemTest() {
        SoftAssert softAssert = new SoftAssert();
        DashboardPage dashboardPage = new LoginService(getDriver()).login();
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        AddSupplierPopup supplierPopup = suppliersPage.clickAddSupplierButton();
        supplierPopup.assertVisibleWithTitle("Supplier Detail");
        supplierPopup.fillRequiredFieldsRandomly();
        supplierPopup.clickSave();
        suppliersPage.assertSuccessMessageVisibleWithText("Supplier Added Successfully", softAssert);
        supplierPopup.ensureLoaded();
        AddSupplierPopup catalogItemsPopup = supplierPopup.clickCatalogItems();
        buyerPage.ensureLoaded();
        catalogItemsPopup.assertCatalogItemsSectionOpened();
        AddSupplierItemPopup itemPopup = catalogItemsPopup.clickAddItem();
        catalogItemsPopup.ensureLoaded();
        itemPopup.assertVisible();
        Map<String, String> infoEntered = itemPopup.fillInfoRandomly();
        Assert.assertEquals(infoEntered.get("genericDesc"), infoEntered.get("desc"), "Description is not copied");
        itemPopup.clickAddSpec();
        Assert.assertTrue(itemPopup.getSpecListSize() > 1, "Spec number has not increased");
        itemPopup.clickSave();
        itemPopup.ensureLoaded();
        suppliersPage.assertSuccessMessageVisibleWithText("Catalog Items Added Successfully", softAssert);
        itemPopup.clickClose();
        itemPopup.assertDisappeared();
        supplierPopup = new BuyerSuppliersPage(getDriver()).getAddSupplierPopup();
        itemPopup = supplierPopup.clickOnAddedItemEdit();
        itemPopup.ensureLoaded();
        itemPopup.assertVisible();
        Map<String, String> infoRead = itemPopup.getInfo();
        Assert.assertEquals(infoRead, infoEntered);
        softAssert.assertAll();
    }
}
