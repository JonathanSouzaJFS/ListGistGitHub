package br.com.listgistgithub.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import br.com.listgistgithub.R
import com.squareup.picasso.Picasso

class BidingUtils {

    companion object {

        @JvmStatic
        @BindingAdapter("bind:picassoLoad")
        fun loadImageView(image: ImageView, avatarUrl: String?) {
            if (avatarUrl != null) {
                Picasso.get().load(avatarUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(image)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:typeGist")
        fun loadTypeGist(textview: AppCompatTextView, files: Map<String, Map<String, Any>>) {
            textview.text = (
                    files.values.firstOrNull()?.getValue("type") ?: "Undefined")
                    as String
        }
    }
}

