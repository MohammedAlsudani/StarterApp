package com.common.di

import androidx.lifecycle.ViewModel
import com.common.modules.ViewModelKey
import com.common.viewmodel.CommonViewModel

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CommonViewModel::class)
    abstract fun commonViewModel(commonViewModel: CommonViewModel): ViewModel

}