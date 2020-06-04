package me.alberto.albertoventen.screens.carowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.alberto.albertoventen.databinding.FragmentCarOwnersBinding
import me.alberto.albertoventen.util.LoadingError

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

        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.status.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            if (it is LoadingError) {
                Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG).show()
            }
        })
    }

}
