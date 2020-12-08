package com.example.alexandr.mytranslatormvvmkotlin.frameworks.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.alexandr.mytranslatormvvmkotlin.R
import com.example.alexandr.model.entity.DataModel
import com.example.alexandr.model.SearchResult
import com.example.alexandr.historyscreen.convertMeaningsToString
import com.example.alexandr.mytranslatormvvmkotlin.use_cases.MainInteractor
import com.example.alexandr.utils.network.isOnline
import com.example.alexandr.core.base.BaseActivity
import com.example.alexandr.mytranslatormvvmkotlin.frameworks.view.descriptionscreen.DescriptionActivity
import com.example.alexandr.historyscreen.history.HistoryActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
class MainActivity : BaseActivity<DataModel, MainInteractor>() {

    override lateinit var model: MainViewModel

    private val observer = Observer<DataModel> { renderData(it) }
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: com.example.alexandr.model.SearchResult) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        com.example.alexandr.historyscreen.convertMeaningsToString(
                            data.meanings!!
                        ),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViewModel()
        initViews()

    }


    override fun setDataToAdapter(data: List<com.example.alexandr.model.SearchResult>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniViewModel() {
        if (main_activity_recyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first") as Throwable
        }
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<DataModel> { renderData(it) })
    }

    private fun initViews() {
        search_button.setOnClickListener {
            isNetworkAvailable = isOnline(applicationContext)
            if (isNetworkAvailable) {
                model.getData(search_edit_text.text.toString(), isNetworkAvailable)
            } else {
                showNoInternetConnectionDialog()
            }
        }
        main_activity_recyclerview.adapter = adapter
    }

}
