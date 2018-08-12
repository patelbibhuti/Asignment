package com.wipro.lbg.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.wipro.lbg.model.Crawler;
import com.wipro.lbg.services.SimpleWebCrawler;

/**
 * @author Bibhuti
 *
 *  The Controller to get the request for Web URL and return list of
 *  discovered URL from service as a model
 * 
 */
public class CrawlerController extends AbstractController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerController.class);

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// create A MODEL PATH
		ModelAndView model = new ModelAndView("index");

		List<Crawler> crawleList = new LinkedList<Crawler>();
		String param = request.getParameter("crawlerUrl");

		// validate if the request is empty/null
		if (StringUtils.isEmpty(param)) {
			model.addObject("crawleList", crawleList);
			LOGGER.debug("Request parameter is null/empty URL should be like: https://wiprodigital.com/");
			return model;
		}

		// add the list of model for discovered URL
		SimpleWebCrawler cralwer = new SimpleWebCrawler();
		cralwer.discoverLinks(param);
		crawleList = cralwer.getCrawledList();
		model.addObject("crawleList", crawleList);

		return model;

	}

}