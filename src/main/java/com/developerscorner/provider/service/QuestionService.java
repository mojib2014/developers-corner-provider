package com.developerscorner.provider.service;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerscorner.provider.repository.QuestionRepository;
import com.developerscorner.provider.repository.UserRepository;
import com.developerscorner.provider.dto.QuestionDto;
import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.Question;
import com.developerscorner.provider.model.User;

@Service
@Transactional
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private UserRepository userRepository;


	public List<Question> findAllQuestions() throws Exception {
		List<Question> questions = questionRepository.findAll();

		if (questions.isEmpty())
			throw new NotFoundException("There are no questions.");
		return questions;
	}


	public Question findById(Long id) {
		return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found"));
	}

	
	public Question findByUsername(String username) {
		return questionRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Question not found"));
	}
	

	public List<Question> findByUserId(Long userId) {
		return questionRepository.findByUserId(userId);
	}


	public void saveQuestion(QuestionDto q) throws Exception {
		User user = userRepository.findById(q.getUserId()).orElseThrow(() -> new NotFoundException("Question not found"));
		
		Question question = Question.builder()
				.username(q.getUsername())
				.tags(q.getTags())
				.question(q.getQuestion())
				.createdAt(LocalDate.now())
				.user(user).build();
		
		questionRepository.save(question);
	}


	public void updateQuestion(Long id, QuestionDto dto) throws Exception {
		Question question = questionRepository.findById(id).orElseThrow(() -> new Exception("Question not found"));
		question.setUsername(dto.getUsername());
		question.setTags(dto.getTags());
		question.setQuestion(dto.getQuestion());
	
		questionRepository.save(question);
	}


	public void deleteById(Long id) {
		questionRepository.deleteById(id);
	}

}