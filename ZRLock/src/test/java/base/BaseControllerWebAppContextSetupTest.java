package base;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 单元测试基类
 * 
 * @author liuxuming
 *
 */
//XML风格
@RunWith(SpringJUnit4ClassRunner.class)	//使用junit4进行测试 
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:spring/root-context.xml") })
public class BaseControllerWebAppContextSetupTest {

	@Autowired
	protected WebApplicationContext wac;
	protected MockMvc mockMvc;
	
	protected MockHttpServletRequest mockRequest;  
	protected MockHttpServletResponse mockResponse;  
	
	//单元测试标记
	protected final static String TESTFALG  = "单元测试";	
	
	@Before
	public void setUp() {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(wac);
		//增加过滤器
		mockMvc = defaultMockMvcBuilder.build();
		 
		mockRequest = new MockHttpServletRequest();
		mockRequest.setCharacterEncoding("UTF-8");
		mockResponse = new MockHttpServletResponse();
	}

}