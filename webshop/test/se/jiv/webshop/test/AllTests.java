package se.jiv.webshop.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CategoryJUnit.class, ProductJUnit.class,
		ShoppingCartJUnit.class, UserJUnit.class })
public class AllTests {

}
