package Testcases;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.Main;


public class Login extends Main {

	@Test(dataProvider ="getData")
	public void logindata(String username,String pwd) throws InterruptedException {
		sendkeys("username_xpath",username);
		Thread.sleep(1000);
		sendkeys("password_xpath",pwd);
		
		click("submit_xpath");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[contains(text(),'Sign Out')]")).click();;
	}

	@DataProvider
	public static Object[][] getData() {
		
		String sheetname = "logindata";
		int rows = excel.getRowCount(sheetname);
		int cols = excel.getColumnCount(sheetname);
		Object[][] data = new Object[rows-1][cols];
		
		for(int Rownum=2;Rownum<=rows;Rownum++) {
			for(int Colnum=0;Colnum<cols;Colnum++) {
				data[Rownum-2][Colnum]=excel.getCellData(sheetname, Colnum, Rownum);
			}
		}
		
		
		return data;
	}
	
	
}
