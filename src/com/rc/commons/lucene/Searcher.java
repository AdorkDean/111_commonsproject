package com.rc.commons.lucene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
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
public class Searcher {
//	private static String INDEX_DIR = InfoUtil.getInstance().getInfo("lucene", "INDEX_DIR");// 索引所在的路径
	private static String INDEX_DIR = "D:\\test\\key_index2";
	public static void main(String[] args) throws Exception {
		File indexDir = new File(INDEX_DIR);
		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new Exception(indexDir + " does not exist or is not a directory.");
		}
		// search(indexDir);// 调用search方法进行查询
		search7(indexDir);// 调用search方法进行查询
	}

	/**
	 * 查询
	 * 
	 * @param indexDir
	 * @param q
	 * @throws Exception
	 */
	public static void search(File indexDir) throws Exception {
		
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		String[] key = new String[] { "尹滨", "滕丽莉", "贝雷" };
		String[] fields = { "contents", "contentsa", "contentsb" };
		BooleanClause.Occur[] flags = new BooleanClause.Occur[] { BooleanClause.Occur.MUST, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
		Analyzer analyzer = new IKAnalyzer(true);
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, key, fields, flags, analyzer);
		TopScoreDocCollector collector = TopScoreDocCollector.create(is.maxDoc(), false);
		long start = new Date().getTime();// start time

		is.search(query, collector);

		TopDocs td = collector.topDocs(0, 5);
		ScoreDoc[] sd = td.scoreDocs;
		for (ScoreDoc scdoc : sd) {
			Document document = is.doc(scdoc.doc);
			System.out.println(document.getField("filename"));

		}

		long end = new Date().getTime();// end time
		System.out.println("Found " + collector.getTotalHits() + " document(s) (in " + (end - start) + " milliseconds)':");
	}

	public static void search2(File indexDir) throws CorruptIndexException, IOException {
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		Term t1 = new Term("contents", "尹");
		Term t2 = new Term("contentsa", "体温计");
		Term t3 = new Term("contentsb", "体温计");

		TermQuery q1 = new TermQuery(t1);
		TermQuery q2 = new TermQuery(t2);
		TermQuery q3 = new TermQuery(t3);

		BooleanQuery query = new BooleanQuery();
		query.add(q1, BooleanClause.Occur.MUST);
		query.add(q2, BooleanClause.Occur.MUST);
		query.add(q3, BooleanClause.Occur.MUST);

		TopScoreDocCollector collector = TopScoreDocCollector.create(is.maxDoc(), false);
		is.search(query, collector);
		TopDocs td = collector.topDocs(0, 5);
		ScoreDoc[] sd = td.scoreDocs;
		for (ScoreDoc scdoc : sd) {
			Document document = is.doc(scdoc.doc);
			System.out.println(document.getField("filename"));

		}
	}

	public static void search3(File indexDir) throws Exception {
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		String[] key = new String[] { "尹滨", "滕丽莉", "贝雷" };
		String[] fields = { "contents", "contentsa", "contentsb" };
		BooleanClause.Occur[] flags = new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
		Analyzer analyzer = new IKAnalyzer(true);
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, key, fields, flags, analyzer);
		TopScoreDocCollector collector = TopScoreDocCollector.create(is.maxDoc(), false);
		long start = new Date().getTime();// start time

		TopFieldDocs result = is.search(query, null, is.maxDoc(), new Sort(new SortField("sort", SortField.STRING, true)));

		ScoreDoc[] sd = result.scoreDocs;
		System.out.println("一共返回：" + sd.length + "条数据");

		for (ScoreDoc scdoc : sd) {
			Document document = is.doc(scdoc.doc);
			System.out.println(document.getField("filename"));

		}

		long end = new Date().getTime();// end time
		System.out.println("Found " + collector.getTotalHits() + " document(s) (in " + (end - start) + " milliseconds)':");
	}

	public static void search4(File indexDir) throws Exception {
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		String[] key = new String[] { "脾胃虚弱"};
		String[] fields = { "reStr"};
		BooleanClause.Occur[] flags = new BooleanClause.Occur[] { BooleanClause.Occur.MUST };
		Analyzer analyzer = new IKAnalyzer(true);
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, key, fields, flags, analyzer);
		TopScoreDocCollector collector = TopScoreDocCollector.create(is.maxDoc(), false);
		long start = new Date().getTime();// start time

		TopFieldDocs result = is.search(query, null, is.maxDoc(), new Sort(new SortField("price", SortField.DOUBLE, true)));

		ScoreDoc[] sd = result.scoreDocs;
		System.out.println("一共返回：" + sd.length + "条数据");
		int i = 0;
		for (ScoreDoc scdoc : sd) {
			Document document = is.doc(scdoc.doc);
//			System.out.println(document.getField("filename").stringValue());
//			System.out.println(document.getField("contents").stringValue());
			heighlight(document.getField("reStr").stringValue(), query, analyzer, "a-" + i++);
		}

		long end = new Date().getTime();// end time
		// System.out.println("Found " + collector.getTotalHits() +
		// " document(s) (in " + (end - start) + " milliseconds)':");
	}

	/**
	 * 范围+字段查询
	 * @param indexDir
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException 
	 */
	public static void search5(File indexDir) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		Analyzer analyzer = new IKAnalyzer(true);
		Query query1=new QueryParser(Version.LUCENE_30,"contents",analyzer).parse("脾胃虚弱");
//		Query query2=new QueryParser(Version.LUCENE_30,"contentsa",analyzer).parse("滕丽莉");
//		Query query3=new QueryParser(Version.LUCENE_30,"contentsb",analyzer).parse("贝 雷");
		
//		Query q4=NumericRangeQuery.newIntRange("sort", 890, 969, true, true);//加上这个报错


		BooleanQuery query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.MUST);
//		query.add(query2, BooleanClause.Occur.SHOULD);
//		query.add(query3, BooleanClause.Occur.SHOULD);
//		query.add(q4, BooleanClause.Occur.SHOULD);   //加上这个报错

		TopFieldDocs result = is.search(query, null, is.maxDoc(), new Sort(new SortField("price", SortField.DOUBLE, true)));

		ScoreDoc[] sd = result.scoreDocs;
		int i=0;
		for (ScoreDoc scdoc : sd) {
			Document document = is.doc(scdoc.doc);
			System.out.println(document.getField("filename").stringValue());
			heighlight(document.getField("contents").stringValue(), query, analyzer, "a-" + i++);
		}
	}
	
	public static void search6(File indexDir) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		Analyzer analyzer = new IKAnalyzer(true);
		Query query1=new QueryParser(Version.LUCENE_30,"reStr",analyzer).parse("脾胃虚弱");
//		Query query2=new QueryParser(Version.LUCENE_30,"contentsa",analyzer).parse("滕丽莉");
//		Query query3=new QueryParser(Version.LUCENE_30,"contentsb",analyzer).parse("贝 雷");
		
		Query q4=NumericRangeQuery.newDoubleRange("price", new Double(0), new Double(2000.00), true, true);


		BooleanQuery query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.MUST);
//		query.add(query2, BooleanClause.Occur.SHOULD);
//		query.add(query3, BooleanClause.Occur.SHOULD);
		query.add(q4, BooleanClause.Occur.MUST);

		TopFieldDocs result = is.search(query, null, is.maxDoc(), new Sort(new SortField("price", SortField.DOUBLE, true)));

		ScoreDoc[] sd = result.scoreDocs;
		System.out.println(sd.length);
		
		//高亮设置  
//        Analyzer analyzer = new IKAnalyzer();//设定分词器  
        SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<B>","</B>");//设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀  
        Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));  
        highlighter.setTextFragmenter(new SimpleFragmenter(150));//设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
        
        
		int i=0;
		for (ScoreDoc scdoc : sd) {
			Document doc = is.doc(scdoc.doc);
			System.out.println(doc.getField("price").stringValue());
            heighlight(doc.getField("reStr").stringValue(), query, analyzer, "a-" + i++);
		}
	}
	public static void search7(File indexDir) throws Exception {
		long start = new Date().getTime();// end time
		IndexSearcher is = new IndexSearcher(FSDirectory.open(indexDir), true);// read-only
		String[] key = new String[] { "营 养 保 健","营养保健"};
		String[] fields = {"reStr","reStr"};
		BooleanClause.Occur[] flags = new BooleanClause.Occur[] { BooleanClause.Occur.SHOULD ,BooleanClause.Occur.SHOULD };
		Analyzer analyzer = new IKAnalyzer(true);
		Query q1 = MultiFieldQueryParser.parse(Version.LUCENE_30, key, fields, flags, analyzer);
		Query q2=NumericRangeQuery.newDoubleRange("price", new Double(0), new Double(8), true, true);
		
		
		BooleanQuery query = new BooleanQuery();
		query.add(q1, BooleanClause.Occur.MUST);
		query.add(q2, BooleanClause.Occur.MUST);

		TopFieldDocs result = is.search(query, null, is.maxDoc(), new Sort(new SortField("price", SortField.DOUBLE, true)));

		ScoreDoc[] sd = result.scoreDocs;
		System.out.println("一共返回：" + sd.length + "条数据");
		for (int i=0;i<sd.length;i++) {
			Document document = is.doc(sd[i].doc);
			System.out.print("id="+document.getField("goodsid").stringValue()+";");
			System.out.println("price="+document.getField("price").stringValue());
			heighlight(document.getField("reStr").stringValue(), query, analyzer, "a-" + i);
		}

		long end = new Date().getTime();// end time
		 System.out.println("Found " + sd.length +
		 " document(s) (in " + (end - start) + " milliseconds)':");
	}
	public static void heighlight(String contents, Query query, Analyzer analyzer, String name) throws IOException, InvalidTokenOffsetsException {
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
		writer.write(result+"<br>");
//		writer.write(price+"<br>");
		writer.write("</body></html>");
		writer.close();
	}
}
