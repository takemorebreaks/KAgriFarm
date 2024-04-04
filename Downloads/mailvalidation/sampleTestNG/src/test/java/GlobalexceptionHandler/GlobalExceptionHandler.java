package GlobalexceptionHandler;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class GlobalExceptionHandler implements ITestListener {
    public void onTestFailure(ITestResult result) {
        // Check if the test result has an exception
        if (result.getThrowable() != null) {
            System.out.println("Exception occurred in test: " + result.getMethod().getMethodName());
            result.getThrowable().printStackTrace();
        }
    }

    // This method handles the UserAlreadyExistException
    public void HandleUserAlreadyExistException(CredantialError exception){
        // Handle the exception (e.g., log it, report it, etc.)
        System.out.println("User already exists: " + exception.getMessage());
        // Additional handling code as needed
    }
}
