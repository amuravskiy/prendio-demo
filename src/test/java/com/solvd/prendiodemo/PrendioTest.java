package com.solvd.prendiodemo;

import com.qaprosoft.carina.core.foundation.AbstractTest;
import com.solvd.prendiodemo.gui.components.*;
import com.solvd.prendiodemo.gui.pages.*;
import com.solvd.prendiodemo.gui.pages.accountspayablepages.AccountsPayableSuppliersPage;
import com.solvd.prendiodemo.gui.pages.accountspayablepages.DepartmentPage;
import com.solvd.prendiodemo.gui.pages.accountspayablepages.VouchersPage;
import com.solvd.prendiodemo.gui.pages.buyerpages.BuyerSuppliersPage;
import com.solvd.prendiodemo.gui.pages.receiverpages.ReceiverScanMatchPage;
import com.solvd.prendiodemo.gui.pages.receiverpages.ReceiverScanPage;
import com.solvd.prendiodemo.utils.Util;
import com.solvd.prendiodemo.values.*;
import com.zebrunner.carina.utils.R;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class PrendioTest extends AbstractTest {

    private static int hoursOffsetFromUTC = R.CONFIG.getInt("hours_offset_from_utc");
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(ZoneOffset.ofHours(hoursOffsetFromUTC));

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
        addSupplierPopup.assertUIObjectNotPresent();
        AccountPayablePage accountPayablePage = suppliersPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        AccountsPayableSuppliersPage accountsPayableSuppliersPage = accountPayablePage.clickSuppliers();
        accountsPayableSuppliersPage.assertPageOpened();
        BasePopup supplierInfoPopup = accountsPayableSuppliersPage.clickSupplierByName(supplierName);
        Assert.assertTrue(supplierInfoPopup.isUIObjectPresent());
        String trailRecordText = supplierInfoPopup.getCreatedTrailText();
        Assert.assertTrue(trailRecordText.contains(fullName));
        String currentDateFormatted = formatter.format(Instant.now());
        Assert.assertTrue(trailRecordText.contains(currentDateFormatted));
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
        Assert.assertEquals(cartContents.getPartNumbers(), duplicatedContents.getPartNumbers());
        Assert.assertEquals(cartContents.getDescriptions(), duplicatedContents.getDescriptions());
        Assert.assertEquals(cartContents.getTotals(), duplicatedContents.getTotals());
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

    @Test(description = "Verifies supplier creation")
    public void checkCreateAndEditDepartmentTest() {
        DashboardPage dashboardPage = Util.loginAs(getDriver());
        dashboardPage.assertPageOpened();
        AccountPayablePage accountPayablePage = dashboardPage.clickAccountsPayable();
        accountPayablePage.assertPageOpened();
        DepartmentPage departmentPage = accountPayablePage.clickDepartment();
        departmentPage.assertPageOpened();
        Assert.assertTrue(departmentPage.isAddButtonVisible());
        DepartmentSetupPopup depSetupPopup = departmentPage.clickAddDep();
        Assert.assertTrue(depSetupPopup.isVisible());
        Assert.assertEquals(depSetupPopup.getHeaderText(), "Department Setup");
        DepInfo enteredDepInfo = depSetupPopup.fillFields();
        depSetupPopup.clickSave();
        Assert.assertTrue(depSetupPopup.isDisappeared());
        Assert.assertEquals(departmentPage.getSuccessMessageText(), "Department Added Successfully");
        depSetupPopup = departmentPage.editDepByName(enteredDepInfo.getName());
        Assert.assertTrue(depSetupPopup.isVisible());
        String expectedName = ("Department Setup - " + enteredDepInfo.getName()).toLowerCase();
        Assert.assertEquals(depSetupPopup.getHeaderText().toLowerCase(), expectedName);
        depSetupPopup.clickWatchers();
        Assert.assertEquals(depSetupPopup.getWatchersText(), "Watchers (0)");
        Assert.assertTrue(depSetupPopup.isWatchersTableEmpty());
        DepWatcherSetupPopup depWatcherSetupPopup = depSetupPopup.clickAddWatcher();
        Assert.assertTrue(depWatcherSetupPopup.isVisible());
        Assert.assertEquals(depWatcherSetupPopup.getHeaderText(), "Department Watcher Setup");
        WatcherInfo watcherInfoEntered = depWatcherSetupPopup.fillWatcher();
        depWatcherSetupPopup.clickSave();
        Assert.assertTrue(depWatcherSetupPopup.isDisappeared());
        Assert.assertTrue(departmentPage.isSuccessMessageVisible());
        Assert.assertEquals(departmentPage.getSuccessMessageText(), "Department Watcher Added Successfully");
        YesNoPopup confirmationPopup = depSetupPopup.close();
        Assert.assertTrue(confirmationPopup.isVisible());
        Assert.assertEquals(confirmationPopup.getHeaderText(), "CONFIRMATION");
        confirmationPopup.clickYes();
        Assert.assertTrue(confirmationPopup.isDisappeared());
        depSetupPopup = departmentPage.editDepByName(enteredDepInfo.getName());
        String username = depSetupPopup.selectAnyUser();
        Assert.assertTrue(departmentPage.isSuccessMessageVisible());
        Assert.assertEquals(departmentPage.getSuccessMessageText(), "Saved Successfully");
        depSetupPopup.clickClose();
        depSetupPopup.ensureLoaded();
        Assert.assertTrue(depSetupPopup.isDisappeared());
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
        String currentDateFormatted = StringUtils.stripStart(formatter.format(Instant.now()), "0");
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
}
