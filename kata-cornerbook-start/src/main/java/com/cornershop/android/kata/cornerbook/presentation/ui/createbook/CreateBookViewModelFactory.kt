package com.cornershop.android.kata.cornerbook.presentation.ui.createbook

/*object CreateBookViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass){
            when{
                isAssignableFrom(CreateBookViewModel::class.java) -> CreateBookViewModel(
                    Injection.getDomainModuleInjector().providesCreateBookUseCase()
                )
                else -> error("Unknown ViewModel class: ${modelClass.name}")
            } as T
        }
    }
}*/