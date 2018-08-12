package com.wipro.lbg.controller.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.wipro.lbg.controller.CrawlerController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	//	classes = { CrawleConfig.class },
		locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
@WebAppConfiguration
public class CrawlerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();

	}

	private static final String regx = "(http|https)://(\\w+\\.)+(com)";

	@Test
	public void testCrawleController() throws Exception {

		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
//		ResultMatcher isBedRequest = MockMvcResultMatchers.status().isBadRequest();

		// validate request url for success
		MockHttpServletRequestBuilder successBuilder = MockMvcRequestBuilders.get("/").requestAttr("crawlerUrl","htps://wiprodigital.com");
		this.mockMvc.perform(successBuilder).andExpect(ok) // HTTP 200 returned
				.andExpect(forwardedUrl("/WEB-INF/pages/index.jsp"));
	}
	
	@Test
	public void testHandleRequestInternal() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("crawlerUrl", "htps://wiprodigital.com");
		CrawlerController c = new CrawlerController();
		ModelAndView mav = c.handleRequestInternal(request,response);
		Assert.assertEquals("index", mav.getViewName());
	}
}
