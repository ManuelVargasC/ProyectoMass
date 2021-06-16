package pe.edu.proyectofinalmass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pe.edu.proyectofinalmass.ui.fragments.ConsultarProductoFragment;
import pe.edu.proyectofinalmass.ui.fragments.ConsultarStockFragment;
import pe.edu.proyectofinalmass.ui.fragments.EditeDeleteProductosFragment;
import pe.edu.proyectofinalmass.ui.fragments.EditedeleteProveedoresFragment;
import pe.edu.proyectofinalmass.ui.fragments.InicioFragment;
import pe.edu.proyectofinalmass.ui.fragments.ListarCategoriasFragment;
import pe.edu.proyectofinalmass.ui.fragments.ListarProductosFragment;
import pe.edu.proyectofinalmass.ui.fragments.RegistrarProveedorFragment;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,
        ListarCategoriasFragment.OnFragmentInteractionListener,InicioFragment.OnFragmentInteractionListener,
        ListarProductosFragment.OnFragmentInteractionListener, ConsultarProductoFragment.OnFragmentInteractionListener,
        EditeDeleteProductosFragment.OnFragmentInteractionListener, RegistrarProveedorFragment.OnFragmentInteractionListener, EditedeleteProveedoresFragment.OnFragmentInteractionListener,
        ConsultarStockFragment.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("appLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                //Cerrar Sesion, destruye la activity y te regresa al Login
                Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Cerró sesión", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager adminFragment =
                getSupportFragmentManager();
        if (id == R.id.Inicio) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new InicioFragment())
                    .commit();
        } else if (id == R.id.Categorias) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new ListarCategoriasFragment())
                    .commit();
        }else if (id == R.id.Productos) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new ListarProductosFragment())
                    .commit();
        }else if (id == R.id.ConsultarProducto) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new ConsultarProductoFragment())
                    .commit();
        }else if (id == R.id.EditarEliminarProducto) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new EditeDeleteProductosFragment())
                    .commit();
        }else if (id == R.id.RegistrarProveedor) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new RegistrarProveedorFragment())
                    .commit();
        }else if (id == R.id.MantenimientoProveedor) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new EditedeleteProveedoresFragment())
                    .commit();
        }else if (id == R.id.ConsultaStock) {

            adminFragment.beginTransaction()
                    .replace(R.id.contenedor
                            , new ConsultarStockFragment())
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}