package logging;

import appium.Bot;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.testng.ITestResult;

public class Listener extends ReportPortalTestNGListener {
    @Override
    public void onTestFailure(ITestResult testResult) {
        Bot.takeScreenshot(testResult.getMethod().getMethodName());
        super.onTestFailure(testResult);
    }
}
