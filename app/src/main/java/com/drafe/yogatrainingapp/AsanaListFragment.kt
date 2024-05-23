package com.drafe.yogatrainingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.drafe.yogatrainingapp.databinding.AsanaListFragmentBinding
import kotlinx.coroutines.launch

class AsanaListFragment: Fragment() {
    private var _binding: AsanaListFragmentBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val asanaViewModel: AsanaListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AsanaListFragmentBinding.inflate(inflater, container, false)
        binding.asanaRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                asanaViewModel.asanaList.collect { asanaList ->
                    binding.asanaRecyclerView.adapter =
                        AsanaListAdapter(asanaList) {asanaId ->
////                            findNavController().navigate(
////                                AsanaListFragmentDirections.showAsanaDetail(asanaId)
//                            )
                        }
                }
            }
        }
    }
}