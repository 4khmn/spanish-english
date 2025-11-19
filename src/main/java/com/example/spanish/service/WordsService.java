package com.example.spanish.service;

import com.example.spanish.model.Word;
import com.example.spanish.repository.WordsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public List<Integer> findAllUnits(){
        return wordsRepository.findAllUnits();
    }
    public List<String> findAllPartOfSpeech(){
        return wordsRepository.findAllPartOfSpeech();
    }

    public Word getRandomWordFromSet(Set<Integer> wordIds) {
        if (wordIds.isEmpty()) {
            return null;
        }
        List<Integer> idList = new ArrayList<>(wordIds);
        int randomId = idList.get(new Random().nextInt(idList.size()));
        return wordsRepository.findById(randomId).orElse(null);
    }

    public Word getRandomWordByUnitExcluding(Integer unit, Set<Integer> excludedIds) {
        List<Word> words = wordsRepository.findByUnit(unit);
        words.removeIf(word -> excludedIds.contains(word.getId()));
        return words.isEmpty() ? null : words.get(new Random().nextInt(words.size()));
    }

    public Word getRandomWordByCategoryExcluding(String category, Set<Integer> excludedIds){
        List<Word> words = wordsRepository.findByCategory(category);
        words.removeIf(word -> excludedIds.contains(word.getId()));
        return words.isEmpty() ? null : words.get(new Random().nextInt(words.size()));
    }

    public Word getRandomWordByPartOfSpeechExcluding(String part, Set<Integer> excludedIds) {
        List<Word> words = wordsRepository.findByPartOfSpeech(part);
        words.removeIf(word -> excludedIds.contains(word.getId()));
        return words.isEmpty() ? null : words.get(new Random().nextInt(words.size()));
    }







    public void addWord(Word word){
        wordsRepository.save(word);
    }


    public List<Word> getAllCards(){
        return (List<Word>) wordsRepository.findAll();
    }
}
