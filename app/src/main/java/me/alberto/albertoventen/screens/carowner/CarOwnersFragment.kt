package me.alberto.albertoventen.screens.carowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.alberto.albertoventen.databinding.FragmentCarOwnersBinding

/**
 * A simple [Fragment] subclass.
 */
class CarOwnersFragment : Fragment() {

    private lateinit var binding: FragmentCarOwnersBinding

    private lateinit var viewModel: CarOwnerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarOwnersBinding.inflate(inflater, container, false)

        val filter = CarOwnersFragmentArgs.fromBundle(requireArguments()).filter
        viewModel =
            ViewModelProvider(this, CarOwnerViewModel.Factory(filter, requireContext())).get(
                CarOwnerViewModel::class.java
            )

        binding.carOwnerRecyclerView.adapter = CarOwnerAdapter()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
