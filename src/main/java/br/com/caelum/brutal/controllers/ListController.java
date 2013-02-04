package br.com.caelum.brutal.controllers;

import static br.com.caelum.vraptor.view.Results.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.brutal.dao.QuestionDAO;
import br.com.caelum.brutal.dao.TagDAO;
import br.com.caelum.brutal.model.Question;
import br.com.caelum.brutal.model.Tag;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class ListController {

	private final QuestionDAO questions;
	private final Result result;
	private final TagDAO tags;
	private final HttpServletRequest req;

	public ListController(QuestionDAO questions, TagDAO tags, Result result, HttpServletRequest req) {
		this.questions = questions;
		this.tags = tags;
		this.result = result;
		this.req = req;
	}

	@Get("/")
	public void home() {
		result.include("questions", questions.all());
	}
	
	@Get("/list/unanswered")
	public void unanswered() {
		result.include("questions", questions.unanswered());
		result.use(page()).of(ListController.class).home();
	}
	
	@Get("/list/withTag/{tagName}")
	public void withTag(String tagName) {
		Tag tag = tags.findByName(tagName);
		List<Question> questionsWithTag = questions.withTag(tag);
		result.include("questions", questionsWithTag);
		result.use(page()).of(ListController.class).home();
	}

}
