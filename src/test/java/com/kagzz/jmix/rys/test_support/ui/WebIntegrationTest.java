package com.kagzz.jmix.rys.test_support.ui;

import com.kagzz.jmix.rys.RentYourStuffApplication;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "com.kagzz.jmix.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
public abstract class WebIntegrationTest {
}