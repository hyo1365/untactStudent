package com.hyo.untactStudent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hyo.untactStudent.dto.Article;
import com.hyo.untactStudent.dto.ResultData;
import com.hyo.untactStudent.util.Util;

@Service
public class ArticleService {

	private int articlesLastId;
	private List<Article> articles;

	public ArticleService() {
		articlesLastId = 0;
		articles = new ArrayList<>();
		articles.add(new Article(++articlesLastId, "2021-02-03 12:12:12", "2021-02-03 12:12:12", "제목1", "내용1"));
		articles.add(new Article(++articlesLastId, "2021-02-03 12:12:12", "2021-02-03 12:12:12", "제목2", "내용2"));
	}

	public Article getArticle(int id) {

		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	public List<Article> getArticles(String searchKeywordType, String searchKeyword) {
		if ( searchKeyword == null) {
			return articles;
		}
		
		List<Article> filterd = new ArrayList<>();
		
		for( Article article : articles) {
			boolean contains = false;
			
			if( searchKeywordType.equals("title")) {
				contains = article.getTitle().contains(searchKeyword);
			}
			
			
			else if( searchKeywordType.equals("body")) {
				contains = article.getBody().contains(searchKeyword);
			}
			
			else {
				contains = article.getTitle().contains(searchKeyword);
				if ( contains == false) {
					contains = article.getBody().contains(searchKeyword);
				}
			}
			
			
			
			if(contains) {
				filterd.add(article);
			}
		}
		return filterd;
		
	}

	public ResultData addArticle(String title, String body) {
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;
		articles.add(new Article(++articlesLastId, regDate, updateDate, title, body));

		return new ResultData("S-1", "게시물이 추가 되었습니다.", "id", articlesLastId);

	}

	public ResultData deleteArticle(int id) {

		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);
				return new ResultData("S-1", "게시물이 삭제 되었습니다.");

			}
		}
		return new ResultData("F-1", "게시물이 존재하지 않습니다.");

	}

	public ResultData doModifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		
		article.setTitle(title);
		article.setBody(body);
		article.setUpdateDate(Util.getNowDateStr());
		
		return new ResultData("S-1", "게시물이 수정 되었습니다.", "id", id);

	}
}
