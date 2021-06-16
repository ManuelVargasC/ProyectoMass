package pe.edu.proyectofinalmass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import pe.edu.proyectofinalmass.ui.entidades.Validaciones;

public class RegistroUsuarioActivity extends AppCompatActivity {

    EditText etCorreo, etUsuarioNuevo, etPasswordNuevo;
    Button btnNuevoUsuario;
    String usuario, password, correo;
    Validaciones objValidar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        etCorreo = findViewById(R.id.etCorreo);
        etUsuarioNuevo = findViewById(R.id.etUsuarioNuevo);
        etPasswordNuevo = findViewById(R.id.etPasswordNuevo);
        btnNuevoUsuario = findViewById(R.id.btnNuevoUsuario);
        objValidar = new Validaciones();

        //Trim() -> se usa para eliminar los espacios al momento de guardar datos en la bd
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = etCorreo.getText().toString().trim();
                usuario = etUsuarioNuevo.getText().toString().trim();
                password = etPasswordNuevo.getText().toString().trim();

                if (!usuario.isEmpty() && !password.isEmpty() && !correo.isEmpty()) {
                    ejecutarServicio("http://192.168.0.7/bd_tiendamass/insertar_usuario.php");
                } else {
                    Toast.makeText(RegistroUsuarioActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Validamos el email mediante nuestra clase Validaciones
                if(!response.isEmpty() && objValidar.isEmail(correo)){
                    Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegistroUsuarioActivity.this, "Correo invalido",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistroUsuarioActivity.this, "No se guardó el usuario",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", etCorreo.getText().toString());
                parametros.put("usuario", etUsuarioNuevo.getText().toString());
                parametros.put("password", etPasswordNuevo.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}