package com.rk.afterstart

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.developer.kalert.KAlertDialog
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.rk.afterstart.NewsAPI.NewsAPI
import com.rk.afterstart.Notification.Notification
import com.rk.afterstart.PhoneAuth.Numbers
import com.rk.afterstart.RecyclerView.RecyclerView
import com.rk.afterstart.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adView: AdView
    private var mInterstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private final val TAG = "MainActivity"

    val customMenu = com.rk.afterstart.HelperKt.CustomMenu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        loadIntertitialAd()
        MobileAds.initialize(this){}

        binding.IntersetitialAd.setOnClickListener {
            showadd()
        }
        binding.RewardedAd.setOnClickListener {
            showRewardedAd()
        }

        adView = findViewById(R.id.adView)
        auth = FirebaseAuth.getInstance()
        binding.Logout.setOnClickListener {
//            auth.signOut()
            startActivity(Intent(this, Numbers::class.java))
        }
        binding.toast.setOnClickListener {
            startActivity(Intent(this, CustomeTaostWithForm::class.java))
        }

        binding.menus.setOnClickListener {
            val menu = customMenu.custommenu(this, it)
        }
        binding.AlertDialogbox.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Alert Dialog Title")
            alertDialogBuilder.setIcon(R.drawable.add)
            alertDialogBuilder.setMessage("This is an example of an AlertDialog in Android.")
            alertDialogBuilder.setPositiveButton(
                "OK",
                DialogInterface.OnClickListener { dialog, which ->
                    SnToast.Builder().context(this).type(Type.SUCCESS).message("Delete")
                        .iconSize(34).textSize(18).animation(true).duration(1000)
                        .backgroundColor(R.color.green).build()
                })
            alertDialogBuilder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    SnToast.Builder().context(this).type(Type.SUCCESS).message("Delete")
                        .iconSize(34).textSize(18).animation(true).duration(1000)
                        .backgroundColor(R.color.green).build()
                })
            alertDialogBuilder.setNeutralButton(
                "Help",
                DialogInterface.OnClickListener { dialog, which ->
                    SnToast.Builder().context(this).type(Type.SUCCESS).message("Delete")
                        .iconSize(34).textSize(18).animation(true).duration(1000)
                        .backgroundColor(R.color.green).build()
                })
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        binding.customeAlertDialogBox.setOnClickListener {
            KAlertDialog(this)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .showCancelButton(true)
                .setCancelClickListener("No,cancel plx!") { sDialog: KAlertDialog ->
                    sDialog.setTitleText(null)
                        .setContentText("Your imaginary file is safe :)")
                        .showCancelButton(false)
                        .setConfirmClickListener("OK", null)
                        .changeAlertType(KAlertDialog.ERROR_TYPE)
                }
                .setConfirmClickListener("Yes,delete it!") { sDialog: KAlertDialog ->
                    sDialog.setTitleText("Deleted!")
                        .showCancelButton(false)
                        .setContentText(null)
                        .setConfirmClickListener("OK", null)
                        .changeAlertType(KAlertDialog.SUCCESS_TYPE)
                }
                .show()
        }

        binding.webview.setOnClickListener {
            startActivity(Intent(this, WebView1::class.java))
        }

        binding.gridview.setOnClickListener {
            startActivity(Intent(this, GridLayout::class.java))
        }

        binding.recyclerView.setOnClickListener {
            startActivity(Intent(this, RecyclerView::class.java))
        }

        binding.fragment.setOnClickListener {
            startActivity(Intent(this, Fragments::class.java))
        }

        binding.navgraph.setOnClickListener {
            startActivity(Intent(this, Navigation::class.java))
        }

        binding.implicitIntent.setOnClickListener {
            startActivity(Intent(this, EmplicitIntent::class.java))
        }

        binding.DataPass.setOnClickListener {
            startActivity(Intent(this, DataBundlePass::class.java))
        }

        binding.BottomNavigation.setOnClickListener {
            startActivity(Intent(this, BottomNavigationBar::class.java))
        }

        binding.DateTimePicker.setOnClickListener {
            startActivity(Intent(this, DateTimePicker::class.java))
        }

        val spinner = binding.spinner
        val spinnerList = listOf("Apple", "Banana", "Guava", "Lemon", "Orange", "Graph", "Kivi")
        val arraAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerList)
        arraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arraAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity, item, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.Registration.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }
        binding.KotlinCoroutines.setOnClickListener {
            startActivity(Intent(this, KotlinCoroutines::class.java))
        }
        binding.RealTimeDatabase.setOnClickListener {
            startActivity(Intent(this, RealTimeDatabase::class.java))
        }
        binding.ImageUpload.setOnClickListener {
            startActivity(Intent(this, ImageUpload::class.java))
        }
        binding.video.setOnClickListener {
            startActivity(Intent(this, VideoUpload::class.java))
        }
        binding.Notification.setOnClickListener {
            startActivity(Intent(this, Notification::class.java))
        }
        binding.sharedpreference.setOnClickListener {
            startActivity(Intent(this, SharedPreferences::class.java))
        }
        binding.retrofit.setOnClickListener {
            startActivity(Intent(this, NewsAPI::class.java))
        }

        binding.BioMetric.setOnClickListener {
            startActivity(Intent(this, BioMetric::class.java))
        }

        loadAd()
    }

    private fun showRewardedAd() {
        rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                rewardedAd = null
            }


            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        rewardedAd?.let { ad ->
            ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Log.d(TAG, "User earned the reward.")
            })
        } ?: run {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
    }

    private fun loadRewarded_ad() {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(TAG, it) }
                rewardedAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                rewardedAd = ad
            }
        })
    }

    private fun showadd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    mInterstitialAd = null
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad showed fullscreen content.")
                }
            }


        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    private fun loadIntertitialAd() {

        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError?.toString()?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })

    }


    private fun loadAd() {
        MobileAds.initialize(this) {}

        // Load an ad into the AdView.
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Toast.makeText(this@MainActivity, "Ad clicked", Toast.LENGTH_LONG).show()
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdLoad", "Ad failed to load with error code: $adError")
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                Log.d("AdLoad", "Ad loaded successfully")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadIntertitialAd()
        loadRewarded_ad()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

}