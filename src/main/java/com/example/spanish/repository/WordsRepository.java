package com.example.spanish.repository;

import com.example.spanish.model.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordsRepository extends CrudRepository<Word, Integer> {

    @Query("SELECT DISTINCT category FROM Word")
    List<String> findAllCategories();

    @Query("SELECT DISTINCT unit FROM Word ORDER BY unit")
    List<Integer> findAllUnits();

    @Query("SELECT DISTINCT partOfSpeech FROM Word")
    List<String> findAllPartOfSpeech();


    List<Word> findByCategory(String category);

    List<Word> findByUnit(int unit);

    List<Word> findByPartOfSpeech(String partOfSpeech);

    @Query("SELECT COUNT(*) FROM Word")
    int findCountOfWords();
    @Query(value = "SELECT * FROM words WHERE unit = :unit ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Word getRandomWordByUnit(@Param("unit") int unit);

    @Query(value = "SELECT * FROM words WHERE category = :category ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Word getRandomWordByCategory(@Param("category") String category);
    @Query(value = "SELECT * FROM words WHERE part_of_speech = :partOfSpeech ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Word getRandomWordByPartOfSpeech(@Param("partOfSpeech") String partOfSpeech);


}
