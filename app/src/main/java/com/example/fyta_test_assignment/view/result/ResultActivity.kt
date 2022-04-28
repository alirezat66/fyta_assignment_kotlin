package com.example.fyta_test_assignment.view.result

import ResultAdapter
import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyta_test_assignment.R
import com.example.fyta_test_assignment.di.Injection
import com.example.fyta_test_assignment.fundation.Utility
import com.example.fyta_test_assignment.model.response.search.SearchModel
import com.example.fyta_test_assignment.viewmodel.search.SearchViewModel
import java.io.File

class ResultActivity : AppCompatActivity() , View.OnClickListener{
    private val loadingLayout: ConstraintLayout by lazy { findViewById(R.id.layout_loading) }
    private val emptyLayout: ConstraintLayout by lazy { findViewById(R.id.layout_empty) }
    private val adapterLayout: ConstraintLayout by lazy { findViewById(R.id.layout_result_adapter) }
    private val btnRetake: Button by lazy { findViewById(R.id.btn_retake_photo_empty) }
    private val btnBack: Button by lazy { findViewById(R.id.btn_back) }
    private  val mResultRecyclerView: RecyclerView by lazy { findViewById(R.id.list_result) }
    private lateinit var file : File;

    private val viewModel by viewModels<SearchViewModel>{
        Injection.provideViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // showing the back button in action bar

        setupViewModel()
        file = intent.getSerializableExtra("file") as File
        viewModel.search(file)
        btnBack.setOnClickListener(this)
        btnRetake.setOnClickListener(this)
    }
    override fun onClick(view: View?) {

        finish()
    }

    private fun showDialog(searchTitle : String ) {
        Log.d(TAG,searchTitle)
        val dialog = Dialog(this)
        val dialogview = LayoutInflater.from(this)
            .inflate(R.layout.detail_search_dialog, null, false)
        dialog.setCancelable(true)
        dialog.setContentView(dialogview)
        val img = dialog.findViewById(R.id.img_search) as ImageView
        val title = dialog.findViewById(R.id.txt_title) as TextView
        img.setImageBitmap(Utility.fileToBitmap(file))
        title.text = searchTitle

        val width =
            (resources.displayMetrics.widthPixels * 0.90).toInt() //<-- int width=400;

        val height = (resources.displayMetrics.heightPixels * 0.50).toInt()

        dialog.window?.setLayout(width,height)
        dialog.window?.decorView?.setBackgroundColor(Color.TRANSPARENT);

        dialog.show()

    }


    private fun setupViewModel() {
        viewModel.results.observe(this, renderSearchResult)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)

    }

    private val isViewLoadingObserver = Observer<Boolean> {
        if(it){
            showLoading();
        }

    }
    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        showEmptyPage()
    }

    private fun initializeViews() {
        loadingLayout.isVisible = false
        emptyLayout.isVisible = false
        adapterLayout.isVisible = false
    }

    private fun showLoading() {
        Log.d(TAG, "we are in loading");
        initializeViews();
        loadingLayout.isVisible = true
    }


    private fun showEmptyPage() {
        Log.d(TAG, "we are in show empty");
        initializeViews();
        emptyLayout.isVisible = true
    }

    private fun showAdapter(results: List<SearchModel>) {
        Log.d(TAG, "we are in show adapter"+results.count());
        initializeViews();
        if(results.count() == 0){
            emptyLayout.isVisible = true
        }else{
            mResultRecyclerView.apply{
                layoutManager = LinearLayoutManager(this@ResultActivity, RecyclerView.VERTICAL, false)
                adapter = ResultAdapter(file = file, results, clickListener = { it.species?.scientificName?.let { it1 ->
                    showDialog(
                        it1
                    )
                } });

            }
            adapterLayout.isVisible = true

        }

    }

    //observers
    private val renderSearchResult =
        Observer<List<SearchModel>> {
            Log.v(TAG, "data updated $it")

            showAdapter(it)
        }
    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        showEmptyPage()
    }
}