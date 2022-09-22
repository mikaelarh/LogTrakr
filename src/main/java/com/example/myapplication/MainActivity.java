package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText Search;
    TextView C1, C2, C3, C4, C5, C6, C7, C8, C9, C10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        C1 = findViewById(R.id.c1);
        C2 = findViewById(R.id.c2);
        C3 = findViewById(R.id.c3);
        C4 = findViewById(R.id.c4);
        C5 = findViewById(R.id.c5);
        C6 = findViewById(R.id.c6);
        C7 = findViewById(R.id.c7);
        C8 = findViewById(R.id.c8);
        C9 = findViewById(R.id.c9);
        C10 = findViewById(R.id.c10);
        Search = findViewById(R.id.Search);
        ImageButton search = findViewById(R.id.Searchb);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Search.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo de busqueda Vacio", Toast.LENGTH_SHORT).show();
                } else {
                    buscar("http://10.1.31.9/invent/buscar.php?search=" + Search.getText().toString() + "");
                }
            }
        });
        ImageButton Clear = findViewById(R.id.ClearButton);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.setText(null);
                C1.setText(null);
                C2.setText(null);
                C3.setText(null);
                C4.setText(null);
                C5.setText(null);
                C6.setText(null);
                C7.setText(null);
                C8.setText(null);
                C9.setText(null);
                C10.setText(null);
                Toast.makeText(getApplicationContext(), "¡Limpieza Completada!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscar(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.startsWith("null")) {
                                response = response.substring("null".length(), response.length());
                            }
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("Data");
                            Toast.makeText(getApplicationContext(), "!DATOS ENCONTRADOS¡", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                C1.setText(obj.getString("Consecutivo"));
                                C2.setText(obj.getString("Sitio"));
                                C3.setText(obj.getString("Lote"));
                                C4.setText(obj.getString("Largo"));
                                C5.setText(obj.getString("Perimetro"));
                                C6.setText(obj.getString("CID"));
                                C7.setText(obj.getString("RFN"));
                                C8.setText(obj.getString("OAut"));
                                C9.setText(obj.getString("Xcoord"));
                                C10.setText(obj.getString("Ycoord"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}