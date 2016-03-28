import data.JsonTaskDataAccessTest;
import data.SettingManagerTest;
import org.junit.runner.JUnitCore;

public class TestRunner {
    public static void main(String[] args) {
        JUnitCore.runClasses(JsonTaskDataAccessTest.class, SettingManagerTest.class);

    }
}
