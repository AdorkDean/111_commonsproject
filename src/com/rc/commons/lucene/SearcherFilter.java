package com.rc.commons.lucene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @Title: Searcher.java
 * @Description:
 * @author yinbinhome@163.com
 * @date 2011-10-31 下午02:38:40
 * @version V1.0
 */
public class SearcherFilter {
	// private static String INDEX_DIR =
	// InfoUtil.getInstance().getInfo("lucene", "INDEX_DIR");// 索引所在的路径
	private static String INDEX_DIR = "D:\\test\\key_index2";

	public static void main(String[] args) throws Exception {
		File indexDir = new File(INDEX_DIR);
		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new Exception(indexDir + " does not exist or is not a directory.");
		}
		// search(indexDir);// 调用search方法进行查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("cateid", "-1");
		map.put("brandid", "-1");
		map.put("price1", "-1");
		map.put("price2", "9.38");
		map.put("isrx", "");
		map.put("type", "");
		map.put("islocal", "");
		map.put("sort", "4");
		map.put("order", "0");
		map.put("keyword", "内");

		IndexObject io=search7(indexDir, map, 1, 5);// 调用search方法进行查询
//		for(IndexModel m:list){
//			System.out.println(m.getGoodsid());
//			System.out.println(java.net.URLDecoder.decode(m.getGoodsname(),"utf-8"));
//			System.out.println(m.getPrice());
//			System.out.println(m.getRealprice());
//			System.out.println(m.getProductid());
//			System.out.println(java.net.URLDecoder.decode(m.getProductname(),"utf-8"));
//			System.out.println(java.net.URLDecoder.decode(m.getFunction(),"utf-8"));
//			System.out.println(m.getSpecifiction());
//			System.out.println("======================");
//		}
		JSONObject jsonObject = JSONObject.fromObject(io);
		
	
		System.out.println(jsonObject.toString());
		 JSONObject jobject = JSONObject.fromObject(jsonObject.toString());
		 System.out.println(jobject.get("page"));
		 System.out.println(jobject.get("totalpages"));
		 JSONArray list = jobject.getJSONArray("models");
		for (int i = 0; i < list.size(); i++) {
			JSONObject job = (JSONObject) list.get(i);
			System.out.println(job.get("goodsid"));
			System.out.println(java.net.URLDecoder.decode(job.get("goodsname").toString(),"utf-8"));
		}
		
	}

	/**
	 * Map cateid:cateid -1=全部 
	 * brandid:brandid -1=全部 
	 * price:price -1=全部 
	 * isrx:isrx 1-处方 0-非处方 
	 * type:type 1=中药 2=西药 
	 * islocal:islocal 1=国产 0=进口 
	 * sort:sort 1=相关度（即默认） 2=销量 3=好评度 4=关注度 5=价格 
	 * order:order 1=降序 0-升序 
	 * keyword:keyword 关键字 
	 * page页码
	 * size每页个数
	 * @param indexDir
	 * @param map
	 * @throws Exception
	 */
	public static IndexObject search7(File indexDir, Map<String, String> map, int page, int size) throws Exception {
		long start = new Date().getTime();// end time
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only

		String cateid = map.get("cateid");
		String brandid = map.get("brandid");
		String price1 = map.get("price1");
		String price2 = map.get("price2");
		String isrx = map.get("isrx");
		String type = map.get("type");
		String islocal = map.get("islocal");
		String order = map.get("order");
		String sort = map.get("sort");
		String keyword = map.get("keyword");

		Analyzer analyzer = new IKAnalyzer(true);
		BooleanQuery query = new BooleanQuery();
		Query query1;
		Query query2;
		Query query3;
		Query query4;
		Query query5;
		Query query6;
		Query query7;

		if (!cateid.equals("-1") && cateid != null && cateid.length() > 0) {
			query1 = new QueryParser(Version.LUCENE_30, "cateid", analyzer).parse(cateid);
			query.add(query1, BooleanClause.Occur.MUST);
		}
		if (!brandid.equals("-1") && brandid != null && brandid.length() > 0) {
			query2 = new QueryParser(Version.LUCENE_30, "brandid", analyzer).parse(brandid);
			query.add(query2, BooleanClause.Occur.MUST);
		}
		if (isrx != null && isrx.length() > 0) {
			query3 = new QueryParser(Version.LUCENE_30, "isrx", analyzer).parse(isrx);
			query.add(query3, BooleanClause.Occur.MUST);
		}
		if (type != null && type.length() > 0) {
			query4 = new QueryParser(Version.LUCENE_30, "type", analyzer).parse(type);
			query.add(query4, BooleanClause.Occur.MUST);
		}
		if (islocal != null && islocal.length() > 0) {
			query5 = new QueryParser(Version.LUCENE_30, "islocal", analyzer).parse(islocal);
			query.add(query5, BooleanClause.Occur.MUST);
		}

		if ((price1 != null && price1.length() > 0) && (price2 != null && price2.length() > 0)) {
			if((price1.equals("-1") || price2.equals("-1") )){
				query6 = NumericRangeQuery.newDoubleRange("price", 0.0, 99999999.0, true, true);
			}else{
				query6 = NumericRangeQuery.newDoubleRange("price", Double.parseDouble(price1), Double.parseDouble(price2), true, true);
			}
			
			query.add(query6, BooleanClause.Occur.MUST);
		}

		if (keyword != null && keyword.length() > 0) {
			query7 = new QueryParser(Version.LUCENE_30, "searchStr", analyzer).parse(keyword);
			query.add(query7, BooleanClause.Occur.MUST);
		}
		boolean b = order.equals("1") ? true : false;
		Sort sortObject = new Sort();
		if (sort.equals("2")) {
			sortObject = new Sort(new SortField("sale", SortField.INT, b));
		} else if (sort.equals("3")) {
			sortObject = new Sort(new SortField("comment", SortField.DOUBLE, b));
		} else if (sort.equals("4")) {
			sortObject = new Sort(new SortField("click", SortField.INT, b));
		} else if (sort.equals("5")) {
			sortObject = new Sort(new SortField("price", SortField.DOUBLE, b));
		}

		TopFieldDocs result = is.search(query, null, is.maxDoc(), sortObject);

		ScoreDoc[] sd = result.scoreDocs;
		System.out.println("一共返回：" + sd.length + "条数据");
		int s = (page - 1) * size+1;
		int d = s + size;
		List<IndexModel> list = new ArrayList<IndexModel>();
		for (int i = 0; i < sd.length; i++) {
			if(i>=s-1 && i<d-1){
				Document document = is.doc(sd[i].doc);
				System.out.print("id=" + document.getField("goodsid").stringValue() + ";");
				System.out.println("price=" + document.getField("price").stringValue());
				String[] r = heighlight(document.getField("searchStr").stringValue(), query, analyzer, "a-" + i);
				IndexModel m = new IndexModel();
				m.setFunction(java.net.URLEncoder.encode(r[2],"utf-8"));
				m.setGoodsid(document.getField("reStr").stringValue().split("\\$\\#\\$")[0]);
				m.setGoodsname(java.net.URLEncoder.encode(r[0],"utf-8"));
				m.setPrice(document.getField("reStr").stringValue().split("\\$\\#\\$")[2]);
				m.setRealprice(document.getField("reStr").stringValue().split("\\$\\#\\$")[3]);
				m.setProductid(document.getField("reStr").stringValue().split("\\$\\#\\$")[4]);
				m.setProductname(java.net.URLEncoder.encode(r[1],"utf-8"));
				m.setSpecifiction(document.getField("reStr").stringValue().split("\\$\\#\\$")[6]);
				list.add(m);
			}
		}
		IndexObject io=new IndexObject();
		io.setModels(list);
		io.setPage(page+"");
		io.setTotalpages(new Double(Math.ceil((double)sd.length/(double)size)).intValue()+"");
		long end = new Date().getTime();// end time
		System.out.println("Found " + sd.length + " document(s) (in " + (end - start) + " milliseconds)':");
		return io;
	}

	/**
	 * 高亮显示结果
	 * 
	 * @param contents
	 * @param query
	 * @param analyzer
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	public static String[] heighlight(String contents, Query query, Analyzer analyzer, String name) throws IOException, InvalidTokenOffsetsException {
		String filename = "d:/" + name + ".html";

		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");

		TokenStream tokens = analyzer.tokenStream("contents", new StringReader(contents));

		QueryScorer scorer = new QueryScorer(query);

		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));

		String result = highlighter.getBestFragments(tokens, contents, 100, "...");

		FileWriter writer = new FileWriter(filename);
		writer.write("<html>");
		writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		writer.write("<style>\n" + ".highlight {\n" + " background: yellow;\n" + "}\n" + "</style>");
		writer.write("<body>");
		writer.write(result + "<br>");
		writer.write("</body></html>");
		writer.close();
		String[] r = result.split("\\$\\#\\$");
		return r;
	}
}
