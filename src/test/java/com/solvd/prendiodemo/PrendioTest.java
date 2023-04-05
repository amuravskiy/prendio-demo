package com.solvd.prendiodemo;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.solvd.prendiodemo.gui.components.*;
import com.solvd.prendiodemo.gui.pages.*;
import com.solvd.prendiodemo.gui.pages.accountspayablepages.AccountsPayableSuppliersPage;
import com.solvd.prendiodemo.gui.pages.accountspayablepages.DepartmentPage;
import com.solvd.prendiodemo.gui.pages.accountspayablepages.VouchersPage;
import com.solvd.prendiodemo.gui.pages.buyerpages.*;
import com.solvd.prendiodemo.gui.pages.receiverpages.ReceiverScanMatchPage;
import com.solvd.prendiodemo.gui.pages.receiverpages.ReceiverScanPage;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.values.*;
import com.zebrunner.carina.utils.R;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrendioTest extends AbstractTest {

    private static int hoursOffsetFromUTC = R.CONFIG.getInt("hours_offset_from_utc");

    @Test(description = "Verifies login works properly")
    public void checkLoginTest() {
        String username = R.CONFIG.get("username");
        String password = R.CONFIG.get("password");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.assertPageOpened();
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(username, password);
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        Util.switchToTabOne(getDriver());
        dashboardPage.assertPageOpened();
    }

    @Test(description = "Verifies adding supplier leaves trail")
    public void checkAddingSupplierTrailTest() {
        DateTimeFormatter addSupplierTrailDateFormatter = DateTimeFormatter
                .ofPattern(R.CONFIG.get("add_supplier_date_format"))
                .withZone(ZoneOffset.ofHours(hoursOffsetFromUTC));
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        String fullName = dashboardPage.getFullname();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        AddSupplierPopup addSupplierPopup = suppliersPage.clickAddSupplierButton();
        addSupplierPopup.assertVisibleWithText("Supplier Detail");
        String supplierName = addSupplierPopup.fillRequiredFields();
        addSupplierPopup.clickSave();
        suppliersPage.assertSuccessMessageVisibleWithText("Supplier Added Successfully");
        addSupplierPopup.clickClose();
        addSupplierPopup.assertDisappeared();
        AccountPayablePage accountPayablePage = suppliersPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        AccountsPayableSuppliersPage accountsPayableSuppliersPage = accountPayablePage.clickSuppliers();
        accountsPayableSuppliersPage.assertPageOpened();
        accountsPayableSuppliersPage = accountsPayableSuppliersPage.search(supplierName);
        accountsPayableSuppliersPage.assertPageOpened();
        BasePopup supplierInfoPopup = accountsPayableSuppliersPage.clickSupplierByName(supplierName);
        supplierInfoPopup.assertVisible();
        String trailRecordText = supplierInfoPopup.getCreatedTrailText();
        String currentDateFormatted = addSupplierTrailDateFormatter.format(Instant.now());
        Matcher trailFullnameMatcher = Pattern.compile(R.CONFIG.get("trail_fullname_regex")).matcher(trailRecordText);
        Matcher trailDateMatcher = Pattern.compile(R.CONFIG.get("trail_date_regex")).matcher(trailRecordText);
        Assert.assertTrue(trailDateMatcher.find(), "Date is not found in trail text");
        Assert.assertTrue(trailFullnameMatcher.find(), "Full name is not found in trail text");
        String trailDate = trailDateMatcher.group(1);
        String trailFullname = trailFullnameMatcher.group(1);
        Assert.assertEquals(trailDate, currentDateFormatted);
        Assert.assertEquals(trailFullname, fullName);
    }

    @Test(description = "Verifies cart remains unchanged after duplication")
    public void checkCartTemplateTest() {
        String templateCartName = R.CONFIG.get("template_cart_name");
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        AllCartsPage allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        allCartsPage = allCartsPage.search(templateCartName);
        allCartsPage.assertPageOpened();
        Assert.assertTrue(allCartsPage.isCartWithNameFound(templateCartName));
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
        confirmationPopup.assertVisibleWithText("CONFIRMATION");
        confirmationPopup.clickConfirmationButton();
        confirmationPopup.assertDisappeared();
        cartPage = new CartPage(getDriver());
        CartContents duplicatedContents = cartPage.getCartContents();
        Assert.assertEquals(duplicatedContents.getPartNumbers(), cartContents.getPartNumbers());
        Assert.assertEquals(duplicatedContents.getDescriptions(), cartContents.getDescriptions());
        Assert.assertEquals(duplicatedContents.getTotals(), cartContents.getTotals());
        Assert.assertEquals(templateCartName, cartPage.getCartName());
        cartPage.removeTemplateWord();
        cartPage.assertSuccessMessageVisibleWithText("Cart items saved");
        String newOrderName = cartPage.getCartName();
        String newOrderId = cartPage.getId();
        Assert.assertEquals(cartPage.getCartName(), templateCartName.replace(" Template", ""));
        dashboardPage = cartPage.clickDashboard();
        dashboardPage.assertPageOpened();
        allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        allCartsPage.assertCartPresent(newOrderId);
        Assert.assertEquals(allCartsPage.getCartNameById(newOrderId), newOrderName);
        allCartsPage = allCartsPage.search(templateCartName);
        Assert.assertEquals(templateCartName, allCartsPage.getCartNameById(templateCartId));
        cartPage = allCartsPage.clickById(templateCartId);
        Assert.assertEquals(cartPage.getCartContents(), cartContents);
    }

    @Test(description = "Verifies cart order creation")
    public void checkCartUsingCatalogTest() {
        final int index = 0;
        String query = R.CONFIG.get("catalog_query");
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        SearchResultPage searchResultPage = dashboardPage.searchCatalog(query);
        searchResultPage.assertPageOpened();
        searchResultPage.assertRetrieved();
        Assert.assertTrue(searchResultPage.isItemsDisplayed());
        Assert.assertTrue(searchResultPage.isAllItemTitlesContainQuery(query), "Not all items contain query string in their title");
        ItemContents itemContents = searchResultPage.getItemContents(index);
        searchResultPage.clickAddToCart(index);
        Assert.assertTrue(searchResultPage.isCreateNewCartButtonDisplayed(index));
        Assert.assertTrue(searchResultPage.isAddInExistingCartButtonDisplayed(index));
        CartPage cartPage = searchResultPage.clickCreateNewCart(index);
        cartPage.assertPageOpened();
        ItemContents itemContentsInCart = cartPage.getFirstItemContents();
        Assert.assertEquals(itemContentsInCart, itemContents);
        ShipToPopup shipToPopup = cartPage.clickShipToButton();
        shipToPopup.assertVisibleWithText("Ship To Address List");
        String line1 = shipToPopup.chooseShipToAddress(0);
        cartPage.ensureLoaded();
        Assert.assertEquals(cartPage.getShipToAddressLine1Text(), line1);
        cartPage.setSelects();
        cartPage.clickApplyToAll();
        String cartId = cartPage.getId();
        Assert.assertTrue(cartPage.isItemSelectsAsCartSelects(0));
        BasePopup reqApprovalPopup = cartPage.clickSubmitCartButton();
        if (cartPage.isInfoPopupVisible()) {
            BasePopup BasePopup = cartPage.getConfirmationPopup();
            BasePopup.clickConfirmationButton();
            BasePopup.assertDisappeared();
            cartPage.ensureLoaded();
        }
        reqApprovalPopup.assertVisibleWithText("Requisition Approval");
        dashboardPage = cartPage.clickSubmitReqApproval();
        dashboardPage.assertPageOpened();
        Assert.assertTrue(dashboardPage.getOrderPreviewsCartName(0).contains(cartId));
    }

    @Test(description = "Verifies department creation")
    public void checkCreateAndEditDepartmentTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        AccountPayablePage accountPayablePage = dashboardPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        DepartmentPage departmentPage = accountPayablePage.clickDepartment();
        departmentPage.assertPageOpened();
        Assert.assertTrue(departmentPage.isAddButtonVisible());
        DepSetupPopup depSetupPopup = departmentPage.clickAddDep();
        depSetupPopup.assertVisible();
        Assert.assertEquals(depSetupPopup.getHeaderText(), "Department Setup");
        DepInfo infoEntered = depSetupPopup.fillFields();
        depSetupPopup.clickSave();
        depSetupPopup.assertDisappeared();
        departmentPage.assertSuccessMessageVisibleWithText("Department Added Successfully");
        departmentPage = departmentPage.searchDepartmentByDesc(infoEntered.getDesc());
        departmentPage.assertPageOpened();
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        depSetupPopup.assertVisible();
        String expectedName = ("Department Setup - " + infoEntered.getName()).toLowerCase();
        Assert.assertEquals(depSetupPopup.getHeaderText().toLowerCase(), expectedName);
        depSetupPopup.clickWatchers();
        Assert.assertTrue(depSetupPopup.getWatchersText().contains("0"));
        Assert.assertTrue(depSetupPopup.isWatchersTableEmpty());
        DepWatcherSetupPopup depWatcherSetupPopup = depSetupPopup.clickAddWatcher();
        depWatcherSetupPopup.assertVisible();
        Assert.assertEquals(depWatcherSetupPopup.getHeaderText(), "Department Watcher Setup");
        WatcherInfo watcherInfoEntered = depWatcherSetupPopup.fillWatcher();
        depWatcherSetupPopup.clickSave();
        depWatcherSetupPopup.assertDisappeared();
        departmentPage.assertSuccessMessageVisibleWithText("Department Watcher Added Successfully");
        BasePopup BasePopup = depSetupPopup.close();
        BasePopup.assertVisible();
        Assert.assertEquals(BasePopup.getHeaderText(), "CONFIRMATION");
        BasePopup.clickConfirmationButton();
        BasePopup.assertDisappeared();
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        depSetupPopup.clickUsers();
        String username = depSetupPopup.selectAnyUser();
        departmentPage.assertSuccessMessageVisibleWithText("Saved Successfully");
        depSetupPopup.clickClose();
        depSetupPopup.ensureLoaded();
        depSetupPopup.assertDisappeared();
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(infoEntered.getName());
        DepInfo loadedInfo = depSetupPopup.getInfo();
        Assert.assertEquals(loadedInfo, infoEntered);
        depSetupPopup.clickWatchers();
        Assert.assertEquals(depSetupPopup.getWatcherInfo(), watcherInfoEntered);
        depSetupPopup.clickUsers();
        Assert.assertEquals(depSetupPopup.getSelectedUserName(), username);
    }

    @Test(description = "Verifies receiver voucher creation")
    public void checkReceiverVoucherCreationTest() {
        DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("M/d/yyyy").withZone(ZoneOffset.ofHours(hoursOffsetFromUTC));
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        ReceiverPage receiverPage = dashboardPage.clickReceiver();
        receiverPage.assertPageOpened();
        ReceiverScanPage scanPage = receiverPage.clickScan();
        scanPage.assertPageOpened();
        scanPage.addUploadFile();
        Assert.assertTrue(scanPage.isIconVisible());
        scanPage.clickUpload();
        Assert.assertTrue(scanPage.isProgressBarVisible());
        Assert.assertTrue(scanPage.isProgressBarDisappeared());
        Assert.assertEquals(scanPage.getUploadStatusText(), "100%");
        ReceiverScanMatchPage matchPage = receiverPage.clickScanMatch();
        matchPage.assertPageOpened();
        Assert.assertTrue(matchPage.isScanItemVisible());
        matchPage.checkFirstItem();
        String currentDateFormatted = formatterShort.format(Instant.now());
        SlipInfo infoEntered = new SlipInfo.SlipInfoBuilder()
                .setRecDate(currentDateFormatted)
                .setInvoiceNumber(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .setInvDate(currentDateFormatted)
                .setInvoiceAmount(String.valueOf(RandomUtils.nextInt(1, 10_000)))
                .setDay(String.valueOf(Instant.now().atOffset(ZoneOffset.ofHours(hoursOffsetFromUTC)).getDayOfMonth()))
                .build();
        matchPage.fillSlipInfo(infoEntered);
        matchPage.clickNextButton();
        matchPage.ensureLoaded();
        matchPage.assertSuccessMessageVisibleWithText("Entry Added Successfully");
        AccountPayablePage accountPayablePage = matchPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        VouchersPage vouchersPage = accountPayablePage.clickVouchers();
        String invNumber = infoEntered.getInvoiceNumber();
        vouchersPage = vouchersPage.search(invNumber);
        vouchersPage.assertVoucherFound(invNumber);
        Assert.assertEquals(vouchersPage.getVoucherEntryByInvNumber(invNumber).getInvNumberText(), invNumber);
    }

    @Test(description = "Verifies user profile update")
    public void checkUserProfileUpdateTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        UserStatusWindow statusWindow = dashboardPage.getUserphotoblock().openUserStatus();
        statusWindow.assertUIObjectPresent();
        ProfilePage profilePage = statusWindow.clickViewProfileButton();
        profilePage.assertPageOpened();
        UserProfileInfo filledInfo = profilePage.fillProfileInfo();
        profilePage.hoverUploadButton();
        Assert.assertTrue(profilePage.isUploadButtonVisible());
        ImageUploadPopup imageUploadPopup = profilePage.clickUploadButton();
        imageUploadPopup.assertVisible();
        imageUploadPopup.attachPhoto();
        Assert.assertTrue(imageUploadPopup.imageAppeared());
        imageUploadPopup.clickUpload();
        imageUploadPopup.ensureLoaded();
        imageUploadPopup.assertDisappeared();
        profilePage.assertSuccessMessageVisibleWithText("Image Uploaded Successfully");
        profilePage.clickSave();
        profilePage.ensureLoaded();
        profilePage.assertSuccessMessageVisibleWithText("User Profile Saved.");
        profilePage.refresh();
        profilePage = new ProfilePage(getDriver());
        profilePage.assertPageOpened();
        Assert.assertEquals(profilePage.getProfileInfo(), filledInfo);
        Assert.assertTrue(profilePage.isOutLinkVisible());
    }

    @Test(description = "Verifies supplier creation")
    public void checkCreateSupplierTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        AddSupplierPopup addSupplierPopup = suppliersPage.clickAddSupplierButton();
        addSupplierPopup.assertVisible();
        Assert.assertEquals(addSupplierPopup.getHeaderText(), "Supplier Detail");
        Map<String, String> infoEntered = addSupplierPopup.fillInfo();
        addSupplierPopup.clickSave();
        suppliersPage.assertSuccessMessageVisibleWithText("Supplier Added Successfully");
        addSupplierPopup.getPopupLeftMenu().clickTabByName("Account Numbers");
        Assert.assertTrue(addSupplierPopup.isAccountsSectionDisplayed());
        AddAccountNumbersPopup addAccountNumbersPopup = addSupplierPopup.clickAdd();
        addAccountNumbersPopup.assertVisible();
        AddressPopup addressPopup = addAccountNumbersPopup.clickSelectShipToAddress();
        addressPopup.assertVisible();
        infoEntered.put("shipToLine2", addressPopup.getAddressLine2Text(0));
        addressPopup.clickAddress(0);
        addressPopup.assertDisappeared();
        infoEntered.put("accountNumber", addAccountNumbersPopup.fillAccountNumber());
        addAccountNumbersPopup.clickSave();
        addAccountNumbersPopup.assertDisappeared();
        suppliersPage.assertSuccessMessageVisibleWithText("Account Added Successfully");
        addSupplierPopup.clickClose();
        addSupplierPopup.assertDisappeared();
        suppliersPage = suppliersPage.search(infoEntered.get("name"));
        addSupplierPopup = suppliersPage.editSupplierByName(infoEntered.get("name"));
        suppliersPage.ensureLoaded();
        addSupplierPopup.assertVisible();
        Map<String, String> infoRead = addSupplierPopup.getFullInfo();
        Assert.assertEquals(infoRead, infoEntered);
    }

    @Test(description = "Verifies shipping address creation")
    public void checkCreateShippingAddressTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        AddressesPage addressesPage = buyerPage.clickAddresses();
        addressesPage.assertPageOpened();
        AddressSetupPopup addressSetupPopup = addressesPage.clickAddAddress();
        addressSetupPopup.assertVisible();
        Assert.assertEquals(addressSetupPopup.getHeaderText(), "Address Setup");
        Map<String, String> addressInfo = addressSetupPopup.fillInfo();
        addressSetupPopup.clickSave();
        addressSetupPopup.ensureLoaded();
        addressesPage.assertSuccessMessageVisibleWithText("Saved Successfully");
        addressSetupPopup.clickUsers();
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.checkAllDefault();
        addressSetupPopup.assertAllDefaultActive();
        addressSetupPopup.clickClose();
        addressSetupPopup.ensureLoaded();
        addressSetupPopup.assertDisappeared();
        addressesPage = addressesPage.search(addressInfo.get("line1"));
        addressesPage.assertAddressFound(addressInfo.get("line1"));
        addressSetupPopup = addressesPage.editFirstAddress();
        addressSetupPopup.assertVisible();
        Assert.assertEquals(addressSetupPopup.getHeaderText(), "Address Setup");
        Assert.assertEquals(addressSetupPopup.getInfo(), addressInfo);
    }

    @Test(description = "Verifies supplier item creation")
    public void checkAddSupplierItemTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        AddSupplierPopup supplierPopup = suppliersPage.clickAddSupplierButton();
        supplierPopup.assertVisible();
        Assert.assertEquals(supplierPopup.getHeaderText(), "Supplier Detail");
        supplierPopup.fillRequiredFields();
        supplierPopup.clickSave();
        suppliersPage.assertSuccessMessageVisibleWithText("Supplier Added Successfully");
        supplierPopup.ensureLoaded();
        AddSupplierPopup catalogItemsPopup = supplierPopup.clickCatalogItems();
        buyerPage.ensureLoaded();
        Assert.assertTrue(catalogItemsPopup.isCatalogItemsSectionOpened());
        AddSupplierItemPopup itemPopup = catalogItemsPopup.clickAddItem();
        catalogItemsPopup.ensureLoaded();
        itemPopup.assertVisible();
        Map<String, String> infoEntered = itemPopup.fillInfo();
        Assert.assertEquals(infoEntered.get("genericDesc"), infoEntered.get("desc"));
        itemPopup.clickAddSpec();
        Assert.assertTrue(itemPopup.getSpecNumber() > 1, "Spec number has not increased");
        itemPopup.clickSave();
        itemPopup.ensureLoaded();
        suppliersPage.assertSuccessMessageVisibleWithText("Catalog Items Added Successfully");
        itemPopup.clickClose();
        itemPopup.assertDisappeared();
        supplierPopup = new BuyerSuppliersPage(getDriver()).getAddSupplierPopup();
        itemPopup = supplierPopup.clickOnAddedItemEdit();
        itemPopup.ensureLoaded();
        itemPopup.assertVisible();
        Map<String, String> infoRead = itemPopup.getInfo();
        Assert.assertEquals(infoRead, infoEntered);
    }
}
