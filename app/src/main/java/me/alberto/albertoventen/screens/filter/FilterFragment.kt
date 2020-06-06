package me.alberto.albertoventen.screens.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.google.android.material.snackbar.Snackbar
import me.alberto.albertoventen.databinding.FragmentFilterBinding
import me.alberto.albertoventen.util.LoadingError

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

        observe()
        installTls12(requireActivity().applicationContext)
        return binding.root
    }

    private fun observe() {
        filterViewModel.status.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            if (it is LoadingError) {
                Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG).show()
            }

        })
    }

    private fun installTls12(context: Context) {
        try {
            ProviderInstaller.installIfNeeded(context)
        } catch (e: GooglePlayServicesRepairableException) {
            // Prompt the user to install/update/enable Google Play services.
            GoogleApiAvailability.getInstance()
                .showErrorNotification(context, e.connectionStatusCode)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Snackbar.make(binding.root, "Need to update google play services", Snackbar.LENGTH_LONG)
                .show()
        }
    }

}
