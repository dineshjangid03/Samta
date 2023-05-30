package com.samta.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.samta.model.AnswerRequest;
import com.samta.model.Question;
import com.samta.model.QuestionDTO;
import com.samta.model.ReturnNextQuestionDTO;
import com.samta.model.ReturnQuestionDTO;
import com.samta.repo.QuestionRepository;

@RestController
@RequestMapping("/api")
public class MyAPIController {

	@Autowired
    private QuestionRepository questionRepository;
    
	@Autowired
    private RestTemplate restTemplate;

    @PostMapping("/fetch-questions")
    public ResponseEntity<String> fetchAndStoreQuestions() {
    	
        String apiUrl = "https://jservice.io/api/random?count=5";
        ResponseEntity<QuestionDTO[]> response = restTemplate.getForEntity(apiUrl, QuestionDTO[].class);
        QuestionDTO[] questionDTOs = response.getBody();
        

        if (questionDTOs != null) {
            for (QuestionDTO questionDTO : questionDTOs) {
                Question question = new Question();
                question.setCategory(questionDTO.getCategory().getTitle());
                question.setQuestion(questionDTO.getQuestion());
                question.setAnswer(questionDTO.getAnswer());
                questionRepository.save(question);
            }
            return ResponseEntity.ok("Questions fetched and stored successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch questions.");
        }
        
    }
    
    @GetMapping("/play")
    public ResponseEntity<ReturnQuestionDTO> getQuestion() {
        Question randomQuestion = getRandomQuestionFromDatabase();
        if (randomQuestion != null) {
        	
        	ReturnQuestionDTO rq=new ReturnQuestionDTO();
        	rq.setQuestion(randomQuestion.getQuestion());
        	rq.setQuestionId(randomQuestion.getId());
        	
            return ResponseEntity.ok(rq);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/next")
    public ResponseEntity<ReturnNextQuestionDTO> getNextQuestion(@RequestBody AnswerRequest answerRequest) {
        Long questionId = answerRequest.getQuestionId();

        Question currentQuestion = questionRepository.findById(questionId).orElse(null);
        if (currentQuestion != null) {
            String correctAnswer = currentQuestion.getAnswer();
            Question nextQuestion = getRandomQuestionFromDatabase();
            
            ReturnQuestionDTO rq=new ReturnQuestionDTO();
        	rq.setQuestion(nextQuestion.getQuestion());
        	rq.setQuestionId(nextQuestion.getId());
        	

            ReturnNextQuestionDTO response = new ReturnNextQuestionDTO();
            response.setAnswer(correctAnswer);
            response.setRq(rq);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Question getRandomQuestionFromDatabase() {
        List<Question> allQuestions = questionRepository.findAll();
        
        int totalQuestions = allQuestions.size();

        if (totalQuestions > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(totalQuestions);
            return allQuestions.get(randomIndex);
        } else {
            return null;
        }
    }

}
