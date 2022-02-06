package com.example.chapter1

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

@SuppressLint("StaticFieldLeak") //AsyncTask로 인한메모리 누수 방지
class MainActivity : AppCompatActivity() , OnDeleteListner {

    lateinit var db: MemoDatabase

    var memoList = listOf<MemoEntity>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //db 지정 싱글톤 패턴 !!로 db가 있다고 확정
        db = MemoDatabase.getInstance(this)!!

        button_add.setOnClickListener {
            val memo = MemoEntity(null, edittext_memo.text.toString())
            edittext_memo.setText("")
            insertMemo(memo)
        }

        recyclerView.layoutManager =LinearLayoutManager(this)

        getAllMemos() //처음 실행시 모든 데이터 가져오기
    }

    // INSERT
    fun insertMemo (memo: MemoEntity) {
        //1. MainThread vs WorkerThread(Background Thread)
        //화면과 관련된 모든 행동은 main에서 데이터는 worker

        val insertTask = object : AsyncTask<Unit,Unit,Unit >(){
            override fun doInBackground(vararg p0: Unit?) {
                db.memoDAO().insert(memo)
            }
            // insert후 확인
            @SuppressLint("StaticFieldLeak")
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        insertTask.execute()
    }
    // GET DATA
    fun getAllMemos () {
        val getTask = ( object : AsyncTask<Unit, Unit,Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            @SuppressLint("StaticFieldLeak")
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                // 저장한것을 recycle에 반영
                setRecycleView(memoList)
            }
        }).execute()
    }
    // DELETE DATA
    fun deleteMemo (memo: MemoEntity) {
        val deleteTask = object : AsyncTask<Unit, Unit,Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        deleteTask.execute()
    }
    // SET RECYCLE VIEW
    // adapter를 사용해 recyclerView에 연결
    fun setRecycleView (memoList : List<MemoEntity>) {
        recyclerView.adapter = MyAdapter(this, memoList, this)
    }

    override fun onDeleteListner(memo: MemoEntity) {
        deleteMemo(memo)
    }

}