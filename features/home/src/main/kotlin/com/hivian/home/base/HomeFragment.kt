package com.hivian.home.base

import androidx.navigation.fragment.NavHostFragment
import com.hivian.common.base.BaseFragment
import com.hivian.common.base.BaseViewModel
import com.hivian.home.R
import com.hivian.home.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home
) {
    private val viewModel: HomeViewModel by viewModel()

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        setupToolbar()
        setupNavController()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    /**
     * Configure app custom support action bar.
     */
    private fun setupToolbar() {
        setHasOptionsMenu(true)
        requireCompatActivity().setSupportActionBar(viewBinding.toolbar)
    }

    private fun setupNavController() {
        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_home) as? NavHostFragment
        val navController = nestedNavHostFragment?.navController
        navController?.run {
            viewModel.navigationControllerChanged(this)
        }
    }

}