package com.example.unscramble

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score=0
    private var _currentWordCount=0
    private var _currentScrambledWord:String="test"
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String
    val score:Int
        get()= _score
    val currentWordCount: Int
        get() = _currentWordCount
    val currentScrambleWord: String
        get() = _currentScrambledWord

    init {
        getNextWord()
        // Log.d("Word",_currentWordCount.toString())
    }
    private fun getNextWord(){

        currentWord= allWordsList.random()

        val temp= currentWord.toCharArray()
        temp.shuffle()
        while(temp.toString().equals(currentWord,false)){
            temp.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        }
        else{
            _currentWordCount++;
            _currentScrambledWord=String(temp)
            wordsList.add(currentWord)
        }
    }
    fun nextWord(): Boolean {
        return if(_currentWordCount< MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
    fun checkWord(s:String) : Boolean{
        return if(s.equals(currentWord,false)){
            // Log.d("word","$s $currentWord")
            increaseScore()
            true
        }
        else false
    }
    private fun increaseScore(){
        _score+=20;
    }
    fun reset(){
        _score=0
        _currentWordCount=0
        wordsList.clear()
        getNextWord()
    }
}