package com.statepersistenceonorientationchange

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GenerateLetters: ViewModel(){
    val liveNumberList: MutableLiveData<ArrayList<Int>> by lazy{
        MutableLiveData<ArrayList<Int>>()
    }
    private val letters = (1..20).toList()
    var numberList = arrayListOf<Int>()

    fun start(): ArrayList<Int>{
        while(numberList.size < 10) {
            val randomNumber = letters.random()
            if (!numberList.contains(randomNumber)) {
                numberList += randomNumber
            }
        }
        return numberList
    }

    fun change(integerList: ArrayList<Int>): ArrayList<Int> {
        val temp = integerList.random()
        if (integerList.indexOf(temp) == 0) {
            integerList.indexOf(temp)
                .also { integerList.remove(integerList[it]); integerList.add(it + 1, temp)}
        } else {
                integerList.indexOf(temp)
                    .also { integerList.remove(integerList[it]); integerList.add(it - 1, temp)
            }
        }
        return integerList
    }

    fun save(saveState: Bundle, value: ArrayList<Int>){
        saveState.putIntegerArrayList("NUMBERS_LIST", value)
    }
    fun load(loadState: Bundle): ArrayList<Int>{
        val n = loadState.getIntegerArrayList("NUMBERS_LIST")!!
        return n
    }
}