package hr.ferit.mlenic.eland.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import hr.ferit.mlenic.eland.R
import hr.ferit.mlenic.eland.model.Land
import hr.ferit.mlenic.eland.model.LandViewModel
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.addTextChangedListener
import java.text.SimpleDateFormat
import java.util.*
import android.text.TextWatcher

class NewLandActivity : AppCompatActivity() {

    lateinit var landNameEdit: TextInputEditText
    lateinit var landCultureEdit: TextInputEditText
    lateinit var landAreaEdit: TextInputEditText
    lateinit var landRefNum: TextInputEditText
    lateinit var landCommentEdit: TextInputEditText
    lateinit var landLatitudeEdit: EditText
    lateinit var landLongitudeEdit: EditText
    lateinit var updateBtn: Button
    lateinit var locateBtn: Button

    lateinit var viewModel: LandViewModel
    var landID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_land)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LandViewModel::class.java)

        landNameEdit = findViewById(R.id.tietName)
        landRefNum = findViewById(R.id.tietRefNum)
        landCultureEdit = findViewById(R.id.tietCulture)
        landAreaEdit = findViewById(R.id.tietArea)
        landCommentEdit = findViewById(R.id.tietComm)
        updateBtn = findViewById(R.id.bUpdate)
        locateBtn = findViewById(R.id.bLocate)
        landLatitudeEdit = findViewById(R.id.etLatitude)
        landLongitudeEdit = findViewById(R.id.etLongitude)

        val landType = intent.getStringExtra("landType")
        if (landType.equals("Edit")) {
            val landName = intent.getStringExtra("landName")
            val landRef = intent.getIntExtra("refNum", 0)
            val landCulture = intent.getStringExtra("landCulture")
            val landArea = intent.getDoubleExtra("landArea", 0.0)
            val landComment = intent.getStringExtra("landComment")
            val landLatitude = intent.getDoubleExtra("latitude", 0.0)
            val landLongitude = intent.getDoubleExtra("longitude", 0.0)

            landID = intent.getIntExtra("landID", -1)

            updateBtn.setText(getString(R.string.updateLand))
            landNameEdit.setText(landName)
            landRefNum.setText(landRef.toString())
            landCultureEdit.setText(landCulture)
            landAreaEdit.setText(landArea.toString())
            landCommentEdit.setText(landComment)
            landLatitudeEdit.setText(landLatitude.toString())
            landLongitudeEdit.setText(landLongitude.toString())

        } else {
            updateBtn.text = getString(R.string.addLand)
        }

        updateBtn.setOnClickListener {
            val landName = landNameEdit.text.toString()
            val landRef = landRefNum.text.toString().toInt()
            val landCulture = landCultureEdit.text.toString()
            val landArea = landAreaEdit.text.toString().toDouble()
            val landComment = landCommentEdit.text.toString()
            val landLatitude = landLatitudeEdit.text.toString().toDouble()
            val landLongitude = landLongitudeEdit.text.toString().toDouble()

            if (landType.equals("Edit")) {
                if (landName.isNotEmpty()) {
                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                    val currDateAndTime: String = dateFormat.format(Date())
                    val updatedLand = Land(landRef, landName, currDateAndTime, landCulture, landArea, landLongitude, landLatitude, landComment)
                    updatedLand.id = landID
                    viewModel.updateLand(updatedLand)
                    Toast.makeText(this, "Land updated", Toast.LENGTH_LONG).show()
                }
            } else {
                if (landName.isNotEmpty()) {
                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                    val currDateAndTime: String = dateFormat.format(Date())
                    viewModel.addLand(Land(
                                    landRef,
                                    landName,
                                    currDateAndTime,
                                    landCulture,
                                    landArea,
                                    landLatitude,
                                    landLongitude,
                                    landComment))
                    Toast.makeText(this, "$landName added", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }

        locateBtn.setOnClickListener {
            val intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}

//www.geeksforgeeks.org/how-to-build-a-simple-note-android-app-using-mvvm-and-room-database/