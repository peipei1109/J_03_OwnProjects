package com.recommenderSystem.rs.intro;

import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import java.io.*;
import java.util.*;
/*
 * 支持文件读取的 FileDataModel，Mahout 没有对文件的格式做过多的要求，只要文件的内容满足以下格式：
每一行包括用户 ID, 物品 ID, 用户偏好
逗号隔开或者 Tab 隔开
*.zip 和 *.gz 文件会自动解压缩（Mahout 建议在数据量过大时采用压缩的数据存储）
支持数据库读取的 JDBCDataModel，Mahout 提供一个默认的 MySQL 的支持，它对用户偏好数据的存放有以下简单的要求：
用户 ID 列需要是 BIGINT 而且非空
物品 ID 列需要是 BIGINT 而且非空
用户偏好列需要是 FLOAT
 */
public class RecommenderIntro {

	private FileDataModel model;
	//private PearsonCorrelationSimilarity similarity; //UserSimilarity 这里不应该是usersimilarity么~~
	private UserSimilarity similarity;
	private NearestNUserNeighborhood neighborhood;
	private GenericUserBasedRecommender recommender;
	private int neighberhoodNum;

	
	// Users CF
	public RecommenderIntro(String filename, int num) throws Exception {
		neighberhoodNum = num;
		model = new FileDataModel(new File(filename));

		similarity = new PearsonCorrelationSimilarity(model);
		neighborhood = new NearestNUserNeighborhood(num, similarity, model);

		recommender = new GenericUserBasedRecommender(model, neighborhood,
				similarity);
	}
                                      
	public List<RecommendedItem> SimpleRecommend(int userid, int num)
			throws Exception {

		List<RecommendedItem> recommendations = recommender.recommend(userid,
				num);

		return recommendations;

	}

}
