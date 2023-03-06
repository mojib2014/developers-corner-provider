package com.developerscorner.provider.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerscorner.provider.repository.QuestionRepository;
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
	private UserService userService;

	public List<Question> findAllQuestions() {
		List<Question> questions = questionRepository.findAll();

		if (questions.isEmpty())
			throw new NotFoundException("There are no questions.");
		return questions;
	}

	public Question findById(Long id) {
		return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found"));
	}

	public Question findByUsername(String username) {
		return questionRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Question not found"));
	}

	public List<Question> findByUserId(Long userId) {
		List<Question> questions = questionRepository.findByUserId(userId);
		if (questions.isEmpty())
			throw new NotFoundException("There are no questions.");

		return questions;
	}

	public void saveQuestion(QuestionDto dto) {
		User user = userService.findById(dto.getUserId());

		Question question = Question.builder()
				.username(dto.getUsername())
				.tags(dto.getTags())
				.question(dto.getQuestion())
				.createdAt(LocalDateTime.now())
				.user(user).build();

		questionRepository.save(question);
	}

	public void updateQuestion(Long id, QuestionDto dto) throws Exception {
		Question question = findById(id);
		question.setUsername(dto.getUsername());
		question.setTags(dto.getTags());
		question.setQuestion(dto.getQuestion());

		questionRepository.save(question);
	}

	public void deleteById(Long id) {
		Question question = findById(id);
		questionRepository.deleteById(question.getId());
	}

}