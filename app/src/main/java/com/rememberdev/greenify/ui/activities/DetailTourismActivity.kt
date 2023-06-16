package com.rememberdev.greenify.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.rememberdev.greenify.R
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.databinding.ActivityDetailTourismBinding
import com.rememberdev.greenify.model.AllTourismImagesItem
import com.rememberdev.greenify.model.TourismImagesItem
import com.rememberdev.greenify.ui.viewmodels.DetailTourismViewModel
import com.rememberdev.greenify.ui.viewmodels.GiveRatingViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class DetailTourismActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTourismBinding

    private val detailTourismViewModel: DetailTourismViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private val giveRatingViewModel: GiveRatingViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var mIdUserPreference: IdUserPreference

    private var id: Int = 0

    private var extraValueID: String = ""
    private var extraValueNAME: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extraValueID = intent.getStringExtra("EXTRA_ID").toString()
        id = extraValueID!!.toInt()
        extraValueNAME = intent.getStringExtra("EXTRA_NAME").toString()

        supportActionBar?.title = "Detail Tourism"

        mIdUserPreference = IdUserPreference(this)

        setupViewModel()

        binding.actionGiveRating.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Give Rating")
            val dialogLayout = inflater.inflate(R.layout.alert_give_rating, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setView(dialogLayout)
            builder.setCancelable(false)
            builder.setPositiveButton("SEND") { dialogInterface, i ->
                //____________________________
                val userId = mIdUserPreference.getUserID()
                val tourismId = extraValueID
                val rating = editText.text
                if (rating.isEmpty()) {
                    Toast.makeText(this, "Please input your give rating", Toast.LENGTH_SHORT).show()
                } else{
                    giveRatingViewModel.giveRating(userId, tourismId, rating)
                    giveRatingViewModel.giveRatingSuccess.observe(this){
                        if (it){
                            Toast.makeText(
                                applicationContext,
                                "Give Rating Success",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                applicationContext,
                                "Cannot Give Rating",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                //____________________________
//                Toast.makeText(
//                    applicationContext,
//                    "$extraValueID ${mIdUserPreference.getUserID()} EditText is " + editText.text.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//                dialogInterface.dismiss()
            }
            builder.create()
            builder.show()
        }
    }

    private fun giveRating(rating: String) {
        val userId = mIdUserPreference.getUserID()
        val tourismId = extraValueID
        if (rating.isEmpty()) {
            Toast.makeText(this, "Please input your give rating", Toast.LENGTH_SHORT).show()
        } else{
            giveRatingViewModel.giveRating(userId, tourismId, rating.toDouble())
            giveRatingViewModel.giveRatingSuccess.observe(this){
                if (it){

                }else{

                }
            }
        }
    }

    private fun setupViewModel() {
        detailTourismViewModel.getTourismById(id)
        detailTourismViewModel.dataDetailtourism.observe(this) {
            binding.tvTourismName.text = it.placeName
            binding.tvTourismLocation.text = it.city
            binding.tvTourismRating.text = it.rating.toString()
            binding.tvTourismDescription.text = it.description
            showImageList(binding.ivTourismImage, it.tourismImages, 0)
            showImageList(binding.img1, it.tourismImages, 1);
            showImageList(binding.img2, it.tourismImages, 2);
            showImageList(binding.img3, it.tourismImages, 3);
            showImageList(binding.img4, it.tourismImages, 4);
            showImageList(binding.img5, it.tourismImages, 5);
            showImageList(binding.img6, it.tourismImages, 6);
            showImageList(binding.img7, it.tourismImages, 7);
            showImageList(binding.img8, it.tourismImages, 8);
        }
    }

    private fun showImageList(imageView: ImageView, list: List<AllTourismImagesItem>, int: Int) {
        if (list.isNotEmpty()) {
            if (list[int].imageUrl != "") {
                Glide.with(this)
                    .load(list[int].imageUrl)
                    .into(imageView)
            } else {
                Glide.with(this)
                    .load("https://www.salonlfc.com/wp-content/uploads/2018/01/image-not-found-scaled.png")
                    .into(imageView)
            }
        } else {
            Glide.with(this)
                .load("https://www.salonlfc.com/wp-content/uploads/2018/01/image-not-found-scaled.png")
                .into(imageView)
        }
    }
}