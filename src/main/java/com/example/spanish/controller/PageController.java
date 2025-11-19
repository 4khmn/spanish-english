package com.example.spanish.controller;

import com.example.spanish.model.Word;
import com.example.spanish.service.WordsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PageController {

    private final WordsService wordsService;

    public PageController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping("/main-page")
    public String getMainPage(HttpSession session,
                              Model model){
        session.removeAttribute("viewedWords");
        session.removeAttribute("reviewMode");
        session.removeAttribute("incorrectWords");
        model.addAttribute("newWord", false);
        return "main-page";
    }
    @PostMapping("/choose-category")
    public String showCards(Model model){
        List<String> categories = wordsService.findAllCategories();
        List<Integer> units = wordsService.findAllUnits();
        List<String> partsOfSpeech = wordsService.findAllPartOfSpeech();

            model.addAttribute("categories", categories);
            model.addAttribute("units", units);
            model.addAttribute("partOfSpeech", partsOfSpeech);
            model.addAttribute("wordExist", wordsService.wordExist());
       return "filter";
   }
   @PostMapping("/show-cards")
   public String showCards(@RequestParam(required = false) String category,
                           @RequestParam(required = false) Integer unit,
                           @RequestParam(required = false) String part,
                           HttpSession session,
                           Model model){
       model.addAttribute("category", category);
       model.addAttribute("unit", unit);
       model.addAttribute("part", part);

       model.addAttribute("allWordsViewed", false);

       // Получаем или создаем Set просмотренных слов для этой сессии
       Set<Integer> viewedWords = (Set<Integer>) session.getAttribute("viewedWords");
       if (viewedWords == null) {
           viewedWords = new HashSet<>();
           session.setAttribute("viewedWords", viewedWords);
       }

       Set<Integer> incorrectWords = (Set<Integer>) session.getAttribute("incorrectWords");
       if (incorrectWords == null) {
           incorrectWords = new HashSet<>();
           session.setAttribute("incorrectWords", incorrectWords);
       }
       // Проверяем режим повторения (если есть неправильные слова)
       Boolean reviewMode = (Boolean) session.getAttribute("reviewMode");
       if (reviewMode == null) {
           reviewMode = false;
       }

       Word randomWord = null;

       if (reviewMode && !incorrectWords.isEmpty()) {
           randomWord = wordsService.getRandomWordFromSet(incorrectWords);
           model.addAttribute("reviewMode", true);
       }
       if (randomWord == null) {
           if (category != null) {
               randomWord = wordsService.getRandomWordByCategoryExcluding(category, viewedWords);
           } else if (unit != null) {
               randomWord = wordsService.getRandomWordByUnitExcluding(unit, viewedWords);
           } else {
               randomWord = wordsService.getRandomWordByPartOfSpeechExcluding(part, viewedWords);
           }
       }

       if (randomWord != null) {
           // Добавляем слово в просмотренные
           if (!reviewMode) {
               viewedWords.add(randomWord.getId());
           }

           model.addAttribute("word", randomWord);
       } else {
           // Все слова просмотрены
           if (!incorrectWords.isEmpty()) {
               session.setAttribute("reviewMode", true);
               model.addAttribute("reviewMode", true);
               randomWord = wordsService.getRandomWordFromSet(incorrectWords);
               if (randomWord != null) {
                   model.addAttribute("word", randomWord);
               } else {
                   model.addAttribute("allWordsViewed", true);
                   session.removeAttribute("viewedWords");
                   session.removeAttribute("incorrectWords");
                   session.removeAttribute("reviewMode");
               }
           } else {
               model.addAttribute("allWordsViewed", true);
               session.removeAttribute("viewedWords");
               session.removeAttribute("incorrectWords");
               session.removeAttribute("reviewMode");
           }
       }

       model.addAttribute("correct", null);
       return "card";
   }


   @GetMapping("/all-cards")
   public String allCards(Model model){
        List<Word> allWords = wordsService.getAllCards();
        model.addAttribute("allWords", allWords);
        return "all-cards";
   }
   @PostMapping("/check-word")
   public String checkWord(@RequestParam int wordId,
                         @RequestParam String spanish,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) Integer unit,
                           @RequestParam(required = false) String part,
                         HttpSession session,
                         Model model){
       Word word = wordsService.findById(wordId);
       boolean correct = word.getSpanish().equalsIgnoreCase(spanish.trim());
       Set<Integer> incorrectWords = (Set<Integer>) session.getAttribute("incorrectWords");
       if (incorrectWords == null) {
           incorrectWords = new HashSet<>();
           session.setAttribute("incorrectWords", incorrectWords);
       }
       Boolean reviewMode = (Boolean) session.getAttribute("reviewMode");


       if (!correct){
           incorrectWords.add(word.getId());
           session.setAttribute("incorrectWords", incorrectWords);
       } else if(reviewMode != null && reviewMode){
           incorrectWords.remove(word.getId());
           session.setAttribute("incorrectWords", incorrectWords);
       }
       model.addAttribute("category", category);
       model.addAttribute("unit", unit);
       model.addAttribute("part", part);
       model.addAttribute("word", word);
       model.addAttribute("correct", correct);
       model.addAttribute("allWordsViewed", false);
       model.addAttribute("reviewMode", reviewMode);

       return "card";

   }

   @PostMapping("/add-word")
    public String addWord(Word word, Model model){
        wordsService.addWord(word);
        model.addAttribute("newWord", true);
        return "main-page";
   }
}
