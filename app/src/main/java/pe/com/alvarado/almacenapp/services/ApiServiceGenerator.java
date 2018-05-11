package pe.com.alvarado.almacenapp.services;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class ApiServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiService.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit;

    private ApiServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        if(retrofit == null) {


            // Retrofit Token: https://futurestud.io/tutorials/retrofit-token-authentication-on-android
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {

                    try {

                        Request originalRequest = chain.request();

                        // Load Token from SharedPreferences (firsttime is null)
                        String token = PreferencesManager.getInstance().get(PreferencesManager.PREF_TOKEN);
                        Log.d(TAG, "Loaded Token: " + token);

                        if(token == null){
                            // Firsttime assuming login
                            return chain.proceed(originalRequest);
                        }

                        // Request customization: add request headers
                        Request modifiedRequest = originalRequest.newBuilder()
                                .header("Authorization", token)
                                .build();

                        return chain.proceed(modifiedRequest); // Call request with token


                    }catch (Exception e){
                        Log.e(TAG, e.toString());
                        FirebaseCrash.report(e);
                        throw e;
                    }

                }
            });

            retrofit = builder.client(httpClient.build()).build();

        }
        return retrofit.create(serviceClass);
    }

}