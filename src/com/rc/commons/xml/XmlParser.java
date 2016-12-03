package com.rc.commons.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>公共方法类</p>
 * <p>XML解析器（SAX）</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class XmlParser extends DefaultHandler {
	private String strValue;
	
	private HashMap inputParas;
	
	/**
	 * 处理文档前的工作
	 * @throws SAXException
	 */
	public void startDocument() throws SAXException {
		inputParas = new HashMap();
	}

	/**
	 * characters
	 * @param arg0 char[]
	 * @param arg1 int
	 * @param arg2 int
	 * @throws SAXException
	 */
	public void characters(char[] arg0, int arg1, int arg2)
		throws SAXException {
		strValue = new String(arg0, arg1, arg2);
	}

	/**
	 * endElement
	 * @param namespaceURI String
	 * @param localName String
	 * @param qName String
	 * @throws SAXException
	 */
	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
		if (!"in".equalsIgnoreCase(qName)
			&& !"multi".equalsIgnoreCase(qName)) {
			if (inputParas.get(qName) == null) {
				ArrayList values = new ArrayList();
				values.add(strValue);
				inputParas.put(qName, values);
			} else {
				ArrayList temps = (ArrayList) inputParas.get(qName);
				temps.add(strValue);
			}
		}
	}

	/**
	 * 对每一个开始元属进行处理
	 * @param namespaceURI String
	 * @param localName String
	 * @param rawName String
	 * @param attrs Attributes
	 * @throws SAXException
	 */
	public void startElement(
		String namespaceURI,
		String localName,
		String rawName,
		Attributes attrs)
		throws SAXException {
	}
	/**
	 *
	 * @throws SAXException
	 */
	public void endDocument() throws SAXException {

	}

	/**
	 * 完成解析工作
	 * @param xmlString String
	 * @throws Exception
	 * @return HashMap
	 */
	public HashMap parser(String xmlString) throws Exception {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		try {
			// 创建一个解析器SAXParser对象
			saxParser = spf.newSAXParser();
			// 注册handler，并进行解析
			StringReader sr = new StringReader(xmlString);
			saxParser.parse(new org.xml.sax.InputSource(sr), this);
			return inputParas;

		} catch (SAXException se) {

			throw se;
		} catch (IOException ioe) {

			throw ioe;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
