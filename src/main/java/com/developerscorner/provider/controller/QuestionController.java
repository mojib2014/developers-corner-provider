package com.developerscorner.provider.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.developerscorner.provider.dto.QuestionDto;
import com.developerscorner.provider.model.Question;
import com.developerscorner.provider.service.QuestionService;

@RestController
@RequestMapping(value = "/user")
public class QuestionController {
	
	
	private final QuestionService questionService;
	
	public QuestionController(QuestionService questionService) {
		super();
		this.questionService = questionService;
	}

	@GetMapping(value = "/questions")
	public ResponseEntity<List<Question>> listQuestions() throws Exception {
		List<Question> questions = questionService.findAllQuestions();
		return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
	}
	
	@PostMapping(value = "/questions")
	public ResponseEntity<Void> createQuestion(@RequestBody @Valid QuestionDto dto) throws Exception {
		questionService.saveQuestion(dto);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/questions/{id}")
	public ResponseEntity<Void> updateQuestion(@PathVariable(value = "id") Long id, @RequestBody @Valid QuestionDto dto) throws Exception {
		System.out.println(dto.toString());
		questionService.updateQuestion(id, dto);
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/questions/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Long id) throws Exception {
		Question question = questionService.findById(id);
		
		return new ResponseEntity<Question>(question, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/questions/{username}")
	public ResponseEntity<Question> getQuestionByUsername(@PathVariable(value = "username") String username) throws Exception {
		Question question = questionService.findByUsername(username);
		
		return new ResponseEntity<Question>(question, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/questions/question")
	public ResponseEntity<List<Question>> getQuestionByUserId(@RequestParam(value = "userId") Long userId) {
		List<Question> questions = questionService.findByUserId(userId);
		
		return new ResponseEntity<List<Question>>(questions, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(value = "/questions/{id}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable(value = "id") Long id) {
		questionService.deleteById(id);
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
}
