package hr.ferit.mlenic.eland.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.ferit.mlenic.eland.R
import hr.ferit.mlenic.eland.adapters.LandAdapter
import hr.ferit.mlenic.eland.listeners.OnLandDeletedListener
import hr.ferit.mlenic.eland.listeners.OnLandLocatedListener
import hr.ferit.mlenic.eland.listeners.OnLandSelectedListener
import hr.ferit.mlenic.eland.model.Land
import hr.ferit.mlenic.eland.model.LandViewModel

class MainActivity : AppCompatActivity(), OnLandSelectedListener, OnLandDeletedListener{

    lateinit var viewModel: LandViewModel
    lateinit var landRV: RecyclerView
    lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        landRV = findViewById(R.id.rvLands)
        fabAdd = findViewById(R.id.fabAdd)
        landRV.layoutManager = LinearLayoutManager(this)
        val landAdapter = LandAdapter(this, this, this)
        landRV.adapter = landAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LandViewModel::class.java)

        viewModel.allLand.observe(this, Observer { list->list?.let{
            landAdapter.updateList(it)}
        }
        )

        fabAdd.setOnClickListener { val intent = Intent(this@MainActivity, NewLandActivity::class.java)
        startActivity(intent)
        this.finish()
        }
    }

    override fun onLandSelected(land: Land) {
        val intent = Intent(this@MainActivity, NewLandActivity::class.java)
        intent.putExtra("landType", "Edit")
        intent.putExtra("landName", land.landName)
        intent.putExtra("landCulture", land.landCulture)
        intent.putExtra("landArea", land.landArea)
        intent.putExtra("refNum", land.refNum)
        intent.putExtra("timeStamp", land.timeStamp)
        intent.putExtra("landComment", land.landComment)
        intent.putExtra("id", land.id)
        intent.putExtra("latitude", land.landLatitude)
        intent.putExtra("longitude", land.landLongitude)
        startActivity(intent)
        this.finish()
    }

    override fun onLandDeletedListener(land: Land) {
        viewModel.deleteLand(land)
        Toast.makeText(this, "${land.landName} deleted.", Toast.LENGTH_LONG).show()
    }
}