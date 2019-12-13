package com.gyula.kepek

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.browse.*
import kotlinx.android.synthetic.main.content_kep_reszletek.*

class KepReszletek : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kep_details)

        activateToolbar(true)

        val kep = intent.extras.getParcelable<Kep>(KEPATVITEL) as Kep

        kep_title.text = "Cím: " + kep.title

        Picasso.with(this).load(kep.url)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(kep_image)


    }

}
