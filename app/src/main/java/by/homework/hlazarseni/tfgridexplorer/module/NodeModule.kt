package by.homework.hlazarseni.tfgridexplorer.module

import by.homework.hlazarseni.tfgridexplorer.services.GridProxy
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory

//class MyClass(
//    private val nodeDao: NodeDao,
//    private val api: GridProxy,
//    private var counter: Int
//)

private const val BASE_URL = "https://gridproxy.grid.tf/"

val nodeModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create<GridProxy>() }

//    single { (counter: Int) -> MyClass(get(), get(), counter) }
//    singleOf(::MyClass)
//    factoryOf(::MyClass)
}