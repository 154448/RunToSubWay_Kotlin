package com.example.rts_fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.rts_fragment.retrofitData.Body
import com.example.rts_fragment.retrofitData.Gyeongui
import com.example.rts_fragment.retrofitData.GyeonguiObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadTimeInfo{

    fun loadTimeInfo(test: MutableLiveData<MutableList<String>>){
        val call = GyeonguiObject.getApi.changeEnd()
        call.enqueue(object: Callback<Gyeongui> {
            override fun onResponse(call: Call<Gyeongui>, response: Response<Gyeongui>) {
                if(response.isSuccessful()){
                    response.body()?.let{
                        Log.d("MainActivity",it.toString())
                        it.body.forEach{ data ->
                            Log.d("MainActivity", data.toString())
                        }
                    }
                    test.postValue(dataSave(response.body()!!.body))
                }else{
                    Log.d("MainActivity", "response가 실패")
                }
            }

            override fun onFailure(call: Call<Gyeongui>, t: Throwable) {
                Log.d("MainActivity", "ErrorMsg: $t, 인터넷 연결 오류")
            }
        })

    }

fun dataSave(body: ArrayList<Body>): MutableList<String> {  //메인 액티비티에서 loadTimeInfo 함수를 실행시켰다면 body에 data가 들어있음.
    var trainInfoArray = mutableListOf<String>("일산행", "9:00", "문산행", "9:00", "청량리행", "9:10", "서울역행", "12:00")
    var count = 0
    for(i in body.indices){
        val array = splitString(body[i].arvlMsg2, body[i].trainLineNm)
        when(body[i].updnLine){
            "상행" -> {
                if(count >= 2) continue
                trainInfoArray.add(count*2,array[1])
                trainInfoArray.add(count*2+1,array[0])
                Log.d("MainActivity", trainInfoArray[count*2])
                Log.d("MainActivity", trainInfoArray[count*2+1])
                count++

            }
            "하행" -> {
                if(count == 4) break
                trainInfoArray.add(count*2,array[1])
                trainInfoArray.add(count*2+1,array[0])
                Log.d("MainActivity", trainInfoArray[count*2])
                Log.d("MainActivity", trainInfoArray[count*2+1])
                count++
            }
        }
    }
    return trainInfoArray
}

    private fun splitString(str1: String, str2: String):Array<String> {
        val returnArr = arrayOf<String>("","")
        if(str1.length > 8){
            val targetNum = str1.split("[", "]")
            val arvlMsg = targetNum[1] + "번째 전역"
            returnArr[0] = arvlMsg
        }
        else {
            returnArr[0] = str1
        }
        val targetStr = str2.split(" - ", "방면")
        val trainLine = targetStr[0] + targetStr[2]
        returnArr[1] = trainLine

        return returnArr
}
}


