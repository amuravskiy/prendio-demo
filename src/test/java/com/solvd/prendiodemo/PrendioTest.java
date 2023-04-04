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
import org.apache.commons.lang3.RandomStringUtils;
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
    private static DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("M/d/yyyy").withZone(ZoneOffset.ofHours(hoursOffsetFromUTC));

    @Test(description = "Verifies login works properly")
    public void checkLoginTest() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.assertPageOpened();
        OneLoginPortalPage oneLoginPortalPage = loginPage.login(R.CONFIG.get("username"), R.CONFIG.get("password"));
        DashboardPage dashboardPage = oneLoginPortalPage.goToPrendio();
        Util.switchToTabOne(getDriver());
        dashboardPage.assertPageOpened();
    }

    @Test(description = "Verifies adding supplier leaves trail")
    public void checkAddingSupplierTrailTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        String fullName = dashboardPage.getFullName();
        BuyerPage buyerPage = dashboardPage.clickBuyer();
        buyerPage.assertPageOpened();
        BuyerSuppliersPage suppliersPage = buyerPage.clickSuppliers();
        suppliersPage.assertPageOpened();
        AddSupplierPopup addSupplierPopup = suppliersPage.clickAddSupplierButton();
        addSupplierPopup.assertUIObjectPresent();
        Assert.assertEquals(addSupplierPopup.getHeaderText(), "Supplier Detail");
        String supplierName = addSupplierPopup.fillRequiredFields();
        addSupplierPopup.clickSave();
        Assert.assertTrue(suppliersPage.isSuccessMessageVisible());
        Assert.assertEquals(suppliersPage.getSuccessMessageText(), "Supplier Added Successfully");
        addSupplierPopup.clickClose();
        addSupplierPopup.assertDisappeared();
        AccountPayablePage accountPayablePage = suppliersPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        AccountsPayableSuppliersPage accountsPayableSuppliersPage = accountPayablePage.clickSuppliers();
        accountsPayableSuppliersPage.assertPageOpened();
        BasePopup supplierInfoPopup = accountsPayableSuppliersPage.clickSupplierByName(supplierName);
        Assert.assertTrue(supplierInfoPopup.isUIObjectPresent());
        String trailRecordText = supplierInfoPopup.getCreatedTrailText();
        DateTimeFormatter addSupplierTrailDateFormatter = DateTimeFormatter
                .ofPattern(R.CONFIG.get("add_supplier_date_format"))
                .withZone(ZoneOffset.ofHours(hoursOffsetFromUTC));
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
        String templateCartName = "Toner Order Template";
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        AllCartsPage allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        allCartsPage = allCartsPage.search(templateCartName);
        allCartsPage.assertPageOpened();
        Assert.assertTrue(allCartsPage.isCartWithNameFound(templateCartName));
        CartPage cartPage = allCartsPage.clickFirstCart();
        cartPage.assertPageOpened();
        if (cartPage.isInfoPopupVisible()) {
            cartPage.clickOkOnInfoPopup();
            Assert.assertTrue(cartPage.isInfoPopupDisappeared());
        }
        CartContents cartContents = cartPage.getCartContents();
        String templateCartId = cartPage.getId();
        cartPage.clickDuplicateCart();
        Assert.assertTrue(cartPage.isConfirmPopupVisible());
        Assert.assertEquals(cartPage.getConfirmationPopup().getHeaderText(), "CONFIRMATION");
        cartPage.clickOkOnConfirmPopup();
        Assert.assertTrue(cartPage.isConfirmPopupDisappeared());
        cartPage = new CartPage(getDriver());
        CartContents duplicatedContents = cartPage.getCartContents();
        Assert.assertEquals(duplicatedContents.getPartNumbers(), cartContents.getPartNumbers());
        Assert.assertEquals(duplicatedContents.getDescriptions(), cartContents.getDescriptions());
        Assert.assertEquals(duplicatedContents.getTotals(), cartContents.getTotals());
        Assert.assertEquals(templateCartName, cartPage.getCartName());
        cartPage.removeTemplateWord();
        Assert.assertTrue(cartPage.isSuccessMessageVisible());
        Assert.assertEquals(cartPage.getSuccessMessageText(), "Cart items saved");
        String newOrderName = cartPage.getCartName();
        String newOrderId = cartPage.getId();
        Assert.assertEquals(cartPage.getCartName(), templateCartName.replace(" Template", ""));
        dashboardPage = cartPage.clickDashboard();
        dashboardPage.assertPageOpened();
        allCartsPage = dashboardPage.clickViewAllCarts();
        allCartsPage.assertPageOpened();
        Assert.assertEquals(allCartsPage.getTopCartId(), newOrderId);
        Assert.assertEquals(allCartsPage.getTopCartName(), newOrderName);
        allCartsPage = allCartsPage.search(templateCartName);
        Assert.assertEquals(templateCartName, allCartsPage.getCartNameById(templateCartId));
        cartPage = allCartsPage.clickById(templateCartId);
        Assert.assertEquals(cartPage.getCartContents(), cartContents);
    }

    @Test(description = "Verifies cart order creation")
    public void checkCartUsingCatalogTest() {
        final String query = R.CONFIG.get("catalog_query");
        final int index = 0;
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        SearchResultPage searchResultPage = dashboardPage.searchCatalog(query);
        searchResultPage.assertPageOpened();
        Assert.assertTrue(searchResultPage.waitRetrieving(R.CONFIG.getInt("retrieving_timeout")));
        Assert.assertTrue(searchResultPage.isItemsDisplayed());
        Assert.assertTrue(searchResultPage.isAllItemTitlesContainQuery(query));
        ItemContents itemContents = searchResultPage.getItemContents(index);
        searchResultPage.clickAddToCart(index);
        Assert.assertTrue(searchResultPage.isCreateNewCartButtonDisplayed(index));
        Assert.assertTrue(searchResultPage.isAddInExistingCartButtonDisplayed(index));
        CartPage cartPage = searchResultPage.clickCreateNewCart(index);
        cartPage.assertPageOpened();
        ItemContents itemContentsInCart = cartPage.getFirstItemContents();
        Assert.assertEquals(itemContentsInCart, itemContents);
        cartPage.clickShipToButton();
        Assert.assertTrue(cartPage.isShipToPopupVisible());
        Assert.assertEquals(cartPage.getShipToPopupTitle(), "Ship To Address List");
        String line1 = cartPage.getAddressLine1(0);
        cartPage.chooseShipToAddress(0);
        Assert.assertEquals(line1, cartPage.getshipToAddressText());
        cartPage.setSelects();
        cartPage.clickApplyToAll();
        String cartId = cartPage.getId();
        Assert.assertTrue(cartPage.isItemSelectsAsCartSelects(0));
        cartPage.clickSubmitCartButton();
        if (cartPage.isInfoPopupVisible()) {
            cartPage.clickOkOnInfoPopup();
            Assert.assertTrue(cartPage.isInfoPopupDisappeared());
            cartPage.ensureLoaded();
        }
        Assert.assertTrue(cartPage.isReqApprovalPopupVisible());
        Assert.assertEquals(cartPage.getReqApprovalPopupTitle(), "Requisition Approval");
        Assert.assertTrue(cartPage.isSubmitReqApprovalClickable(), "Sumbit requisition approval button in not clickable");
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
        Assert.assertTrue(depSetupPopup.isVisible());
        Assert.assertEquals(depSetupPopup.getHeaderText(), "Department Setup");
        DepInfo enteredDepInfo = depSetupPopup.fillFields();
        depSetupPopup.clickSave();
        depSetupPopup.assertDisappeared();
        Assert.assertEquals(departmentPage.getSuccessMessageText(), "Department Added Successfully");
        depSetupPopup = departmentPage.editDepByName(enteredDepInfo.getName());
        Assert.assertTrue(depSetupPopup.isVisible());
        String expectedName = ("Department Setup - " + enteredDepInfo.getName()).toLowerCase();
        Assert.assertEquals(depSetupPopup.getHeaderText().toLowerCase(), expectedName);
        depSetupPopup.clickWatchers();
        Assert.assertTrue(depSetupPopup.getWatchersText().contains("0"));
        Assert.assertTrue(depSetupPopup.isWatchersTableEmpty());
        DepWatcherSetupPopup depWatcherSetupPopup = depSetupPopup.clickAddWatcher();
        Assert.assertTrue(depWatcherSetupPopup.isVisible());
        Assert.assertEquals(depWatcherSetupPopup.getHeaderText(), "Department Watcher Setup");
        WatcherInfo watcherInfoEntered = depWatcherSetupPopup.fillWatcher();
        depWatcherSetupPopup.clickSave();
        depWatcherSetupPopup.assertDisappeared();
        Assert.assertTrue(departmentPage.isSuccessMessageVisible());
        Assert.assertEquals(departmentPage.getSuccessMessageText(), "Department Watcher Added Successfully");
        YesNoPopup confirmationPopup = depSetupPopup.close();
        Assert.assertTrue(confirmationPopup.isVisible());
        Assert.assertEquals(confirmationPopup.getHeaderText(), "CONFIRMATION");
        confirmationPopup.clickYes();
        confirmationPopup.assertDisappeared();
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(enteredDepInfo.getName());
        depSetupPopup.clickUsers();
        String username = depSetupPopup.selectAnyUser();
        Assert.assertTrue(departmentPage.isSuccessMessageVisible());
        Assert.assertEquals(departmentPage.getSuccessMessageText(), "Saved Successfully");
        depSetupPopup.clickClose();
        depSetupPopup.ensureLoaded();
        depSetupPopup.assertDisappeared();
        departmentPage = new DepartmentPage(getDriver());
        depSetupPopup = departmentPage.editDepByName(enteredDepInfo.getName());
        DepInfo loadedInfo = depSetupPopup.getInfo();
        Assert.assertEquals(loadedInfo, enteredDepInfo);
        depSetupPopup.clickWatchers();
        Assert.assertEquals(depSetupPopup.getWatcherInfo(), watcherInfoEntered);
        depSetupPopup.clickUsers();
        Assert.assertEquals(depSetupPopup.getSelectedUserName(), username);
    }

    @Test(description = "Verifies receiver voucher creation")
    public void checkReceiverVoucherCreationTest() {
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
        SlipInfo slipInfoEntered = new SlipInfo.SlipInfoBuilder()
                .setRecDate(currentDateFormatted)
                .setInvoiceNumber(RandomStringUtils.randomNumeric(4))
                .setInvDate(currentDateFormatted)
                .setInvoiceAmount(RandomStringUtils.randomNumeric(4))
                .setDay(String.valueOf(Instant.now().atOffset(ZoneOffset.ofHours(hoursOffsetFromUTC)).getDayOfMonth()))
                .build();
        matchPage.fillSlipInfo(slipInfoEntered);
        matchPage.clickNextButton();
        matchPage.ensureLoaded();
        Assert.assertEquals(matchPage.getSuccessMessageText(), "Entry Added Successfully");
        AccountPayablePage accountPayablePage = matchPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        VouchersPage vouchersPage = accountPayablePage.clickVouchers();
        vouchersPage = vouchersPage.search(slipInfoEntered.getInvoiceNumber());
        Assert.assertEquals(vouchersPage.getFirstVoucherInvDateText(), slipInfoEntered.getInvDate());
        Assert.assertEquals(vouchersPage.getFirstVoucherInvNumberText(), slipInfoEntered.getInvoiceNumber());
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
        Assert.assertTrue(imageUploadPopup.isVisible());
        imageUploadPopup.attachPhoto();
        Assert.assertTrue(imageUploadPopup.imageAppeared());
        imageUploadPopup.clickUpload();
        imageUploadPopup.ensureLoaded();
        imageUploadPopup.assertDisappeared();
        Assert.assertEquals(profilePage.getSuccessMessageText(), "Image Uploaded Successfully");
        profilePage.clickSave();
        profilePage.ensureLoaded();
        Assert.assertEquals(profilePage.getSuccessMessageText(), "User Profile Saved.");
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
        Assert.assertTrue(addSupplierPopup.isVisible());
        Assert.assertEquals(addSupplierPopup.getHeaderText(), "Supplier Detail");
        Map<String, String> infoEntered = addSupplierPopup.fillInfo();
        addSupplierPopup.clickSave();
        Assert.assertEquals(suppliersPage.getSuccessMessageText(), "Supplier Added Successfully");
        addSupplierPopup.getPopupLeftMenu().clickTabByName("Account Numbers");
        Assert.assertTrue(addSupplierPopup.isAccountsSectionDisplayed());
        AddAccountNumbersPopup addAccountNumbersPopup = addSupplierPopup.clickAdd();
        Assert.assertTrue(addAccountNumbersPopup.isVisible());
        AddressPopup addressPopup = addAccountNumbersPopup.clickSelectShipToAddress();
        Assert.assertTrue(addressPopup.isVisible());
        infoEntered.put("shipToLine2", addressPopup.getAddressLine2Text(0));
        addressPopup.clickAddress(0);
        addressPopup.assertDisappeared();
        infoEntered.put("accountNumber", addAccountNumbersPopup.fillAccountNumber());
        addAccountNumbersPopup.clickSave();
        addAccountNumbersPopup.assertDisappeared();
        Assert.assertEquals(suppliersPage.getSuccessMessageText(), "Account Added Successfully");
        addSupplierPopup.clickClose();
        addSupplierPopup.assertDisappeared();
        suppliersPage = suppliersPage.search(infoEntered.get("name"));
        addSupplierPopup = suppliersPage.editSupplierByName(infoEntered.get("name"));
        Assert.assertTrue(addSupplierPopup.isVisible());
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
        Assert.assertTrue(addressSetupPopup.isVisible());
        Assert.assertEquals(addressSetupPopup.getHeaderText(), "Address Setup");
        Map<String, String> addressInfo = addressSetupPopup.fillInfo();
        addressSetupPopup.clickSave();
        addressSetupPopup.ensureLoaded();
        Assert.assertEquals(addressesPage.getSuccessMessageText(), "Saved Successfully");
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
        Assert.assertTrue(addressSetupPopup.isVisible());
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
        Assert.assertTrue(supplierPopup.isVisible());
        Assert.assertEquals(supplierPopup.getHeaderText(), "Supplier Detail");
        supplierPopup.fillRequiredFields();
        supplierPopup.clickSave();
        Assert.assertEquals(suppliersPage.getSuccessMessageText(), "Supplier Added Successfully");
        supplierPopup.ensureLoaded();
        AddSupplierPopup catalogItemsPopup = supplierPopup.clickCatalogItems();
        buyerPage.ensureLoaded();
        Assert.assertTrue(catalogItemsPopup.isCatalogItemsSectionOpened());
        AddSupplierItemPopup itemPopup = catalogItemsPopup.clickAddItem();
        catalogItemsPopup.ensureLoaded();
        Assert.assertTrue(itemPopup.isVisible());
        Map<String, String> infoEntered = itemPopup.fillInfo();
        Assert.assertEquals(infoEntered.get("genericDesc"), infoEntered.get("desc"));
        itemPopup.clickAddSpec();
        Assert.assertTrue(itemPopup.getSpecNumber() > 1);
        itemPopup.clickSave();
        itemPopup.ensureLoaded();
        Assert.assertEquals(suppliersPage.getSuccessMessageText(), "Catalog Items Added Successfully");
        itemPopup.clickClose();
        itemPopup.assertDisappeared();
        supplierPopup = new BuyerSuppliersPage(getDriver()).getAddSupplierPopup();
        itemPopup = supplierPopup.clickOnAddedItemEdit();
        itemPopup.ensureLoaded();
        Assert.assertTrue(itemPopup.isVisible());
        Map<String, String> infoRead = itemPopup.getInfo();
        Assert.assertEquals(infoRead, infoEntered);
    }
}
