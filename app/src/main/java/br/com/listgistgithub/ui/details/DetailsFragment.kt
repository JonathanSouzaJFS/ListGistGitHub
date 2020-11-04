package br.com.listgistgithub.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.listgistgithub.R
import br.com.listgistgithub.utils.BidingUtils
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        BidingUtils.loadImageView(ownerPhoto, args.ownerPhoto)
        ownerDescription.text = args.ownerDescription ?: ""
        ownerName.text = args.ownerName ?: ""
    }
}