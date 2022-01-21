package testsuiterunnerjunit5;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(LoginFastTestCategory.class)
@SuiteClasses({UserLoginTest.class})
public class TestSuiteRunnerQuickTest {

}
