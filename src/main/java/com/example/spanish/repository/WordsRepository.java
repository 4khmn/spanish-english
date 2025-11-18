package com.example.spanish.repository;

import com.example.spanish.model.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordsRepository extends CrudRepository<Word, Integer> {
    Word findWordByCategory(String category);


    @Query("SELECT DISTINCT category FROM Word")
    List<String> findAllCategories();

    @Query("SELECT DISTINCT unit FROM Word ORDER BY unit")
    List<String> findAllUnits();

    @Query("SELECT DISTINCT partOfSpeech FROM Word")
    List<String> findAllPartOfSpeech();



    @Query("SELECT COUNT(*) FROM Word")
    int findCountOfWords();

    Word getRandomWordByUnit(int unit);


    Word getRandomWordByCategory(String category);

    Word getRandomWordByPartOfSpeech(String partOfSpeech);


}
