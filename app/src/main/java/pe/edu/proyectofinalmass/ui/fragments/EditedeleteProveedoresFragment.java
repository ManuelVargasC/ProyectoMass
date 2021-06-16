package pe.edu.proyectofinalmass.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.edu.proyectofinalmass.R;
import pe.edu.proyectofinalmass.ui.entidades.Productos;
import pe.edu.proyectofinalmass.ui.entidades.Proveedores;

public class EditedeleteProveedoresFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    EditText etNombreProveedorC, etRazonSocialProveedorC, etRucProveedorC, etDireccionProveedorC,
            etnTelefonoProveedorC;
    Button btnActualizarProveedorC, btnEliminarProveedorC;
    ImageButton btnConsultarProveedorC;
    ProgressDialog pDialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public EditedeleteProveedoresFragment() {

    }

    public static EditedeleteProveedoresFragment newInstance(String param1, String param2) {
        EditedeleteProveedoresFragment fragment = new EditedeleteProveedoresFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_editedelete_proveedores, container, false);

        etNombreProveedorC=(EditText) vista.findViewById(R.id.etNombreProveedorC);
        etRazonSocialProveedorC= (EditText) vista.findViewById(R.id.etRazonSocialProveedorC);
        etRucProveedorC= (EditText) vista.findViewById(R.id.etRucProveedorC);
        etDireccionProveedorC= (EditText) vista.findViewById(R.id.etDireccionProveedorC);
        etnTelefonoProveedorC= (EditText) vista.findViewById(R.id.etnTelefonoProveedorC);
        btnActualizarProveedorC=(Button) vista.findViewById(R.id.btnActualizarProveedorC);
        btnConsultarProveedorC=(ImageButton) vista.findViewById(R.id.btnConsultarProveedorC);
        btnEliminarProveedorC=(Button) vista.findViewById(R.id.btnEliminarProveedorC);
        request= Volley.newRequestQueue(getContext());

        btnConsultarProveedorC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });

        btnActualizarProveedorC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webServiceActualizar("http://192.168.0.7/bd_tiendamass/update_proveedor.php");
            }
        });
        btnEliminarProveedorC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webServiceEliminar("http://192.168.0.7/bd_tiendamass/delete_proveedor.php");
            }
        });
        return vista;
    }

    private void webServiceEliminar(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "El proveedor fue eliminadado", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre",etNombreProveedorC.getText().toString());
                return parametros;
            }
        };
        request.add(stringRequest);

    }

    private void webServiceActualizar(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Operación exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre",etNombreProveedorC.getText().toString());
                parametros.put("razon_social",etRazonSocialProveedorC.getText().toString());
                parametros.put("ruc",etRucProveedorC.getText().toString());
                parametros.put("direccion",etDireccionProveedorC.getText().toString());
                parametros.put("telefono",etnTelefonoProveedorC.getText().toString());
                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private void cargarWebService() {
        pDialog=new ProgressDialog(getContext());
        pDialog.setMessage("Cargando...");
        pDialog.show();
        String url="http://192.168.0.7/bd_tiendamass/consultar_proveedor_nombre.php?nombre="+etNombreProveedorC.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                Proveedores proveedores=new Proveedores();

                JSONArray json=response.optJSONArray("nombres");
                JSONObject jsonObject=null;

                try {
                    jsonObject=json.getJSONObject(0);
                    proveedores.setRazon_social(jsonObject.optString("razon_social"));
                    proveedores.setRuc(jsonObject.optString("ruc"));
                    proveedores.setDireccion(jsonObject.optString("direccion"));
                    proveedores.setTelefono(jsonObject.optString("telefono"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                etRazonSocialProveedorC.setText(proveedores.getRazon_social());
                etRucProveedorC.setText(proveedores.getRuc());
                etDireccionProveedorC.setText(proveedores.getDireccion());
                etnTelefonoProveedorC.setText(proveedores.getTelefono());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), " No se econtraron datos "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                pDialog.hide();
                Log.d("ERROR: ", error.toString());
            }
        });
        request.add(jsonObjectRequest);
    }
    private void limpiarFormulario(){
        etNombreProveedorC.setText("");
        etDireccionProveedorC.setText("");
        etnTelefonoProveedorC.setText("");
        etRazonSocialProveedorC.setText("");
        etRucProveedorC.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pDialog.hide();
        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
}