package com.rc.commons.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author ht 索引生成
 * 
 */
public class Indexer {
	private static String INDEX_DIR = "D:\\test\\key_index2";// 索引存放目录
	private static String DATA_DIR = "D:\\test\\keyword2\\";// 小文件存放的目录

	public static void main(String[] args) throws Exception {

		long start = new Date().getTime();
		int numIndexed = index(new File(INDEX_DIR), new File(DATA_DIR));// 调用index方法
		long end = new Date().getTime();
		System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
	}

	/**
	 * 索引dataDir下的.txt文件，并储存在indexDir下，返回索引的文件数量
	 * 
	 * @param indexDir
	 * @param dataDir
	 * @return int
	 * @throws IOException
	 */
	public static int index(File indexDir, File dataDir) throws IOException {

		if (!dataDir.exists() || !dataDir.isDirectory()) {
			throw new IOException(dataDir + " does not exist or is not a directory");
		}
		// 实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir), analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
		indexDirectory(writer, dataDir);// 调用indexDirectory方法
		int numIndexed = writer.numDocs();
		writer.optimize();
		writer.close();
		return numIndexed;
	}

	/**
	 * 循环遍历目录下的所有.txt文件并进行索引
	 * 
	 * @param writer
	 * @param dir
	 * @throws IOException
	 */
	private static void indexDirectory(IndexWriter writer, File dir) throws IOException {

		File[] files = dir.listFiles();

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(writer, f); // recurse
			} else if (f.getName().endsWith(".txt")) {
				indexFile(writer, f, i + 1);
			}
		}
	}

	/**
	 * 对单个txt文件进行索引
	 * 
	 * @param writer
	 * @param f
	 * @throws IOException
	 */
	private static void indexFile(IndexWriter writer, File f, int i) throws IOException {

		if (f.isHidden() || !f.exists() || !f.canRead()) {
			return;
		}

		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = new Document();
		doc.add(new Field("contents", getString(f), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("contentsa", getString(f), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("contentsb", getString(f), Field.Store.YES, Field.Index.ANALYZED));
		// doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES,
		// Field.Index.ANALYZED));
		doc.add(new NumericField("sort", Field.Store.YES, true).setIntValue(Integer.parseInt(f.getName().split("\\.")[0])));
		doc.add(new Field("filename", f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		// doc.add(new NumericField("num",i,Field.Store.YES,false));
		// doc.add(new Field("sort", new FileReader("")));
		writer.addDocument(doc);
	}

	public static String getString(File f) throws IOException {
		FileInputStream is = new FileInputStream(f);
		StringBuffer sb = new StringBuffer("");
		while (true) {
			byte[] bytes = new byte[100];
			int iLen = is.read(bytes);
			if (iLen <= 0) {
				break;
			}
			sb.append(new String(bytes, 0, iLen, "utf-8"));
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
}
