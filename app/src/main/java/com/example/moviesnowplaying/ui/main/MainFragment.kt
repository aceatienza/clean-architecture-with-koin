package com.example.moviesnowplaying.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesnowplaying.databinding.MainFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var moviesAdapter: MoviesAdapter

    /**
     *     note: the correct import is "org.koin.androidx.viewmodel.ext.android.viewModel",
     *     not org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel, or import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
     */
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)

        moviesAdapter = MoviesAdapter()
        binding.rvMoviesShort.adapter = moviesAdapter
        binding.rvMoviesShort.layoutManager = LinearLayoutManager(context)

        mainViewModel.getMovies()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.movies.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty())
                moviesAdapter.update(emptyList())
            else
                moviesAdapter.update(it)
        })
    }
}