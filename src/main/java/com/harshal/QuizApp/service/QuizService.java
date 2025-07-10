package com.harshal.QuizApp.service;

import com.harshal.QuizApp.dao.QuestionDao;
import com.harshal.QuizApp.dao.QuizDao;
import com.harshal.QuizApp.model.Question;
import com.harshal.QuizApp.model.QuestionWrapper;
import com.harshal.QuizApp.model.Quiz;
import com.harshal.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);


        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
       Optional<Quiz> quiz =  quizDao.findById(id);

       List<Question> questionFromDB = quiz.get().getQuestions();
       List<QuestionWrapper> questionsForUsers = new ArrayList<>();
        for(Question q : questionFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUsers.add(qw);
        }

       return new  ResponseEntity<>(questionsForUsers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);

    }
}
