import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.kms.katalon.core.util.KeywordUtil

KeywordLogger log = new KeywordLogger()

/* Read the Gross Profit Plan Figure for Daily, Monthly and Yearly and confirm the values are not the same.
 * If these values are the same, it usually indicates an import problem
 * Created 03-12-2018 15:19PM by Pete Wilson
 * Updated 04-18-2018 11:34AM by Pete Wilson
 * Updated 08-09-2018 16:57PM by Pete Wilson
 * Updated 11-13-2018 11:42AM by Pete Wilson
 */

//Determine if the user is a Sales Rep or Manager
int accordionValue=CustomKeywords.'tools.commonCode.getUserRole'()

WebUI.navigateToUrl(GlobalVariable.baseurl + "/v3/sales_dashboard")

//Check to make sure the Sales Dashboard page loads. Look for the text, Daily Sales
CustomKeywords.'errorCheck.validateSalesDashboard.checkPageLoad'('Daily Sales')

// Read Daily Gross Profit Plan
siteDailyGrossProfit = WebUI.getText(findTestObject('ABC/Sales Dashboard/Common Objects/table-Sales Detail Values for Daily-MTD-YTD', [('divIndex') : accordionValue, ('rowIndex') : 5])).replaceAll("[^0-9-]","").toInteger()

// Read Monthly Gross Profit Plan
siteMTDGrossProfit = WebUI.getText(findTestObject('ABC/Sales Dashboard/Common Objects/table-Sales Detail Values for Daily-MTD-YTD', [('divIndex') : (accordionValue+2), ('rowIndex') : 5])).replaceAll("[^0-9-]","").toInteger()

// Read Yearly Gross Profit Plan
siteYTDGrossProfit = WebUI.getText(findTestObject('ABC/Sales Dashboard/Common Objects/table-Sales Detail Values for Daily-MTD-YTD', [('divIndex') : (accordionValue+4), ('rowIndex') : 5])).replaceAll("[^0-9-]","").toInteger()


log.logWarning('Daily Gross Profit Plan:=' + siteDailyGrossProfit)

log.logWarning('Monthly Gross Profit Plan:=' + siteMTDGrossProfit)

log.logWarning('Yearly Gross Profit Plan:=' + siteYTDGrossProfit)

if (siteDailyGrossProfit==siteMTDGrossProfit){
	log.logError('ERROR: Daily and Monthly Gross Profit Plan Figures are equal. This could indicate an error')
	KeywordUtil.markError('ERROR: Daily and Monthly Gross Profit Plan Figures. This could indicate an error')
} else {
	log.logWarning('SUCCESS: No duplicates')
}

if (siteMTDGrossProfit==siteYTDGrossProfit){
	log.logError('ERROR: Monthly and Yearly Gross Profit Plan Figures are equal. This could indicate an error')
	KeywordUtil.markError('ERROR: Monthly and Yearly Gross Profit Plan Figures. This could indicate an error')
} else {
	log.logWarning('SUCCESS: No duplicates')
}
