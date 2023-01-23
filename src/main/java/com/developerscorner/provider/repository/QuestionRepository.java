package com.developerscorner.provider.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.developerscorner.provider.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	Optional<Question> findByUsername(String username);
	List<Question> findByUserId(Long userId);
	@Modifying
	@Query("delete from Question b where b.id=:id")
	void deleteById(Long id);
}
