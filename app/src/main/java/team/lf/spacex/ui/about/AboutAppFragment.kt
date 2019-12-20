package team.lf.spacex.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import team.lf.spacex.databinding.FragmentAboutAppBinding

class AboutAppFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutAppBinding.inflate(inflater, container,false)
        return binding.root
    }

}