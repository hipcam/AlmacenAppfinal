package pe.com.alvarado.almacenapp.activities;
import pe.com.alvarado.almacenapp.R;
import pe.com.alvarado.almacenapp.models.Producto;
import pe.com.alvarado.almacenapp.services.ApiService;
import pe.com.alvarado.almacenapp.services.ApiServiceGenerator;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private Integer id;

    private ImageView fotoImage;
    private TextView nombreText;
    private TextView detallesText;
    private TextView precioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fotoImage = findViewById(R.id.foto_image);
        nombreText = findViewById(R.id.nombre_text);
        detallesText = findViewById(R.id.detalles_text);
        precioText = findViewById(R.id.precio_text);

        id = getIntent().getExtras().getInt("ID");
        Log.e(TAG, "id:" + id);

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Producto> call = service.showProducto(id);

        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Producto producto = response.body();
                        Log.d(TAG, "producto: " + producto);

                        String url = ApiService.API_BASE_URL + "/images/" + producto.getImagen();
                        Picasso.with(DetailActivity.this).load(url).into(fotoImage);

                        nombreText.setText(producto.getNombre());
                        detallesText.setText(producto.getDetalles());
                        precioText.setText("Precio: S/. " + producto.getPrecio());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}

