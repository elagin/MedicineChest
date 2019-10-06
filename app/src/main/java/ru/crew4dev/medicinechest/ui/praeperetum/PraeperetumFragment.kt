package ru.crew4dev.medicinechest.ui.praeperetum

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.crew4dev.medicinechest.R

class PraeperetumFragment : Fragment() {

    companion object {
        fun newInstance() : PraeperetumFragment{
            return PraeperetumFragment()
        }
    }

    private lateinit var viewModel: PraeperetumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.praeperetum_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PraeperetumViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
