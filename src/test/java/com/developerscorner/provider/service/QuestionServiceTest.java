package com.developerscorner.provider.service;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.developerscorner.provider.ProviderMocksConfig;
import com.developerscorner.provider.dto.QuestionDto;
import com.developerscorner.provider.exception.NotFoundException;
import com.developerscorner.provider.model.Question;
import com.developerscorner.provider.model.User;
import com.developerscorner.provider.repository.QuestionRepository;
import com.developerscorner.provider.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = {QuestionService.class, UserService.class, UserServiceImple.class, ProviderMocksConfig.class})
class QuestionServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private QuestionRepository questionRepo;
	@Autowired
	private QuestionService questionService;
	
	private List<Question> questions = null;
	private User user = null;
	private Question question;
	
	@BeforeClass
	public void setup() {
		MockitoAnnotations.openMocks(this);
		questions = new ArrayList<>();
		
		user = User.builder().id(1L).firstName("first name").lastName("last name").nickName("nick name").type("Mentor")
				.email("first.name@email.com").password("123456").build();
		
		Question q1 = Question.builder()
				.id(1L)
				.username("username1")
				.user(user)
				.createdAt(LocalDateTime.now())
				.tags("Java")
				.question("Java unit test with mockito")
				.build();
		
		question = q1;
		
		Question q2 = Question.builder()
				.id(2L)
				.username("username1")
				.user(user)
				.createdAt(LocalDateTime.now())
				.tags("Java")
				.question("Java generics example")
				.build();
		Question q3 = Question.builder()
				.id(3L)
				.username("username1")
				.user(user)
				.createdAt(LocalDateTime.now())
				.tags("JavaScript")
				.question("JavaScript scope")
				.build();
		
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
	}
	
	@Test
	void shouldFindAllQuestions() throws Exception {
		when(questionRepo.findAll()).thenReturn(questions);
		
		List<Question> result = questionService.findAllQuestions();
		
		verify(questionRepo).findAll();
		
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void shouldFindQuestionById() {
		when(questionRepo.findById(1L)).thenReturn(Optional.of(question));
		
		Question result = questionService.findById(question.getId());
		
		verify(questionRepo, atMost(2)).findById(question.getId());
		assertNotNull(result);
	}
	
	@Test
	public void shouldFindQuestionByUsername() {
		when(questionRepo.findByUsername(question.getUsername())).thenReturn(Optional.of(question));
		
		Question result = questionService.findByUsername(question.getUsername());
		
		verify(questionRepo).findByUsername(question.getUsername());
		assertNotNull(result);
	}
	
	@Test
	public void shouldFindQuestionByUserId() {
		when(questionRepo.findByUserId(question.getUser().getId())).thenReturn(questions);
		
		List<Question> result = questionService.findByUserId(question.getUser().getId());
		
		verify(questionRepo).findByUserId(question.getUser().getId());
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void shouldSaveAQuestion() throws Exception {
		QuestionDto dto = QuestionDto.builder()
				.userId(1L)
				.username("new user")
				.tags("new tags")
				.question("new question inserted").build();
		
		when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
		when(questionRepo.save(any(Question.class))).thenReturn(null);
		
		questionService.saveQuestion(dto);
		
		verify(questionRepo).save(any(Question.class));
	}
	
	@Test
	public void shouldUpdateAQuestion() throws Exception {
		QuestionDto dto = QuestionDto.builder()
				.userId(1L)
				.username("new user")
				.tags("new tags")
				.question("new question inserted").build();
		
		when(questionRepo.findById(question.getId())).thenReturn(Optional.of(question));
		when(questionRepo.save(any(Question.class))).thenReturn(null);
		
		questionService.updateQuestion(question.getId(), dto);
		
		verify(questionRepo, atMost(3)).save(any(Question.class));
	}

	@Test
	public void shouldDeleteAQuestion() throws Exception {
		when(questionRepo.findById(question.getId())).thenReturn(Optional.of(question));
		//when(questionRepo.deleteById(anyLong())).thenReturn(null);
		
		questionService.deleteById(question.getId());
		
		verify(questionRepo).deleteById(anyLong());
	}
	
	// ===================== Negative tests =====================
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "There are no questions.")
	public void shouldThrowNotFoundExceptionIfNoQuestionsExists() {
		when(questionRepo.findAll()).thenReturn(new ArrayList<>());
		//doThrow(new NotFoundException("There are no questions.")).when(questionRepo).findAll();
		questionService.findAllQuestions();
		//verify(questionRepo).findAll();
	}
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "Question not found")
	public void shouldThrowNotFoundExceptionIfQuestionByIdNotExists() {
		when(questionRepo.findById(100L)).thenReturn(Optional.empty());
		
		questionService.findById(100L);
		verify(questionRepo).findById(100L);
	}
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "Question not found")
	public void shouldThrowNotFoundExceptionIfQuestionByUsernameNotExists() {
		when(questionRepo.findByUsername("username not exists")).thenReturn(Optional.empty());
		
		questionService.findByUsername("username not exists");
		verify(questionRepo).findByUsername("username not exists");
	}
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "There are no questions.")
	public void shouldThrowNotFoundExceptionIfQuestionByUserIdNotExists() {
		when(questionRepo.findByUserId(1000L)).thenReturn(new ArrayList<>());
		
		questionService.findByUserId(1000L);
		verify(questionRepo).findByUserId(1000L);
	}
	
	@Test(expectedExceptions = NotFoundException.class, expectedExceptionsMessageRegExp = "User not found")
	public void shouldThrowNotFoundExceptionSavingAQuestionIfUserNotExists() {
		when(userRepo.findById(anyLong())).thenReturn(Optional.empty());
		when(questionRepo.save(any(Question.class))).thenReturn(null);
		
		QuestionDto dto = QuestionDto.builder()
				.userId(10000L)
				.username("new dto")
				.tags("new tags dto")
				.question("new question inserted.").build();
		
		questionService.saveQuestion(dto);
	}
	
}
