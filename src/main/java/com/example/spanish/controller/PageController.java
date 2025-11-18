package com.example.spanish.controller;

import com.example.spanish.model.Word;
import com.example.spanish.service.WordsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    private final WordsService wordsService;

    public PageController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping("/main-page")
    public String getMainPage(Model model){
        model.addAttribute("newWord", false);
        return "main-page";
    }
    @PostMapping("/choose-category")
    public String showCards(Model model){
        List<String> categories = wordsService.findAllCategories();
        List<String> units = wordsService.findAllUnits();
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
                           Model model){
        if (category!=null){
            Word randomWordByCategory = wordsService.getRandomWordByCategory(category);
            model.addAttribute("word", randomWordByCategory);
        }
        else if (unit!=null){
            Word randomWordByUnit = wordsService.getRandomWordByUnit(unit);
            model.addAttribute("word", randomWordByUnit);
        }
        else{
            Word randomWordByPartOfSpeech = wordsService.getRandomWordByPartOfSpeech(part);
            model.addAttribute("word", randomWordByPartOfSpeech);
        }
        return "card";
   }

   @PostMapping("/check-word")
   public String checkWord(@RequestParam int wordId,
                         @RequestParam String spanish,
                         Model model){
       Word word = wordsService.findById(wordId);
       boolean correct = word.getSpanish().equalsIgnoreCase(spanish.trim());
       model.addAttribute("word", word);
       model.addAttribute("correct", correct);

       return "result";

   }

   @PostMapping("/add-word")
    public String addWord(Word word, Model model){
        wordsService.addWord(word);
        model.addAttribute("newWord", true);
        return "main-page";
   }




}
