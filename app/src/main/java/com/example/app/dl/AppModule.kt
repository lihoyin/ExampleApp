package com.example.app.dl

import android.content.Context
import androidx.room.Room
import com.example.app.data.local.ExampleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): ExampleDatabase {
        return Room.databaseBuilder(
            context = applicationContext,
            klass = ExampleDatabase::class.java,
            name = "app-database"
        ).build()
    }

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                url("https://pokeapi.co/api/v2/")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            expectSuccess = true
        }
    }
}
