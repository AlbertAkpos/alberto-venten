package me.alberto.albertoventen.screens.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.alberto.albertoventen.databinding.FragmentFilterBinding

/**
 * A simple [Fragment] subclass.
 */
class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

    private val filterViewModel: FilterViewModel by lazy {
        ViewModelProvider(
            this,
            FilterViewModel.Factory()
        ).get(FilterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)

        binding.filterRecyclerView.adapter = FilterAdapter(FilterItemClickListener {
            val action = FilterFragmentDirections
                .actionFilterFragmentToCarOwnersFragment(it)
            this.findNavController().navigate(action)
        })

        binding.viewModel = filterViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}
