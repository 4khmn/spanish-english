package com.example.spanish.service;

import com.example.spanish.model.Word;
import com.example.spanish.repository.WordsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WordsService {

    private final WordsRepository wordsRepository;

    public WordsService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public Word findById(int id){
        return wordsRepository.findById(id).orElse(null);
    }

    public boolean wordExist(){
        return (wordsRepository.findCountOfWords() != 0);
    }

    public List<String> findAllCategories(){
        return wordsRepository.findAllCategories();
    }

    public List<String> findAllUnits(){
        return wordsRepository.findAllUnits();
    }
    public List<String> findAllPartOfSpeech(){
        return wordsRepository.findAllPartOfSpeech();
    }


    public Word getRandomWordByUnit(int unit){
        return wordsRepository.getRandomWordByUnit(unit);
    }

    public Word getRandomWordByCategory(String category){
        return wordsRepository.getRandomWordByCategory(category);
    }

    public Word getRandomWordByPartOfSpeech(String partOfSpeech){
        return wordsRepository.getRandomWordByPartOfSpeech(partOfSpeech);
    }







    public void addWord(Word word){
        wordsRepository.save(word);
    }
}
