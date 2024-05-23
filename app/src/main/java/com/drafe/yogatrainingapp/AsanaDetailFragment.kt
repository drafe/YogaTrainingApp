package com.drafe.yogatrainingapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.drafe.yogatrainingapp.databinding.AsanaDetailFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

class AsanaDetailFragment: Fragment() {
    private val args: AsanaDetailFragmentArgs by navArgs()
    private var _binding: AsanaDetailFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val asanaDetailViewModel: AsanaDetailViewModel by viewModels {
        AsanaDetailViewModelFactory(args.asanaId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AsanaDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                asanaDetailViewModel.asana.collect { asana ->
                    asana?.let { updateUi(it) }
                }
            }
        }
    }

    private fun updateUi(asana: Asana) {
        binding.apply {
            asanName.text = asana.nameEng
            asanaNameHindi.text = asana.nameHin

            // Загрузка иконки асаны из assets
            try {
                val assetManager = requireContext().assets
                val inputStream = assetManager.open("poses/${asana.imgName}")
                val drawable = Drawable.createFromStream(inputStream, null)
                imageView.setImageDrawable(drawable)
            } catch (e: IOException) {
                e.printStackTrace()
                // Обработайте ошибку (например, установите изображение по умолчанию)
            }
        }
    }
}

class AsanaDetailViewModelFactory(private val asanaId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass:Class<T>): T{
        return AsanaDetailViewModel(asanaId) as T
    }

}

class AsanaDetailViewModel(asanaId: UUID): ViewModel() {
    private val asanaRepository = YogaRepository.get()

    private val _asana: MutableStateFlow<Asana?> = MutableStateFlow(null)
    val asana: StateFlow<Asana?> = _asana.asStateFlow()

    init {
        viewModelScope.launch {
            _asana.value = asanaRepository.getAsanaById(asanaId)
        }
    }
}
