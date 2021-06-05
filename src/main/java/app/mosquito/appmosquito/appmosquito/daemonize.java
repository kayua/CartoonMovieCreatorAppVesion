package app.mosquito.appmosquito.appmosquito;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class daemonize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String a = new String();

        if (isServicoRodando(a) != true){

            startService();

        }else{

          stopService();

        };

        tela();

    }

    private boolean isServicoRodando(String servico){

        servico = "app.mosquito.appmosquito.appmosquito.ForegroundService";
        boolean toReturnServicoIsRodando = false;

        try{

            ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

            for (ActivityManager.RunningServiceInfo servicosRodando : activityManager.getRunningServices(Integer.MAX_VALUE)) {

                Log.i("COMPARANDO", "Serviço: "+ servico +  " com: " + servicosRodando.service.getClassName());

                if (servico.equals(servicosRodando.service.getClassName())) {

                    toReturnServicoIsRodando = true;
                    break;
                }

            }

        }catch (Exception ex) {

            toReturnServicoIsRodando = false;

        }

        return toReturnServicoIsRodando;

    }

    public void startService() {

        Toast.makeText(this, "Aplicativo rodando em plano de fundo", Toast.LENGTH_LONG).show();
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Plano de fundo");

        ContextCompat.startForegroundService(this, serviceIntent);

    }

    public void stopService() {

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    public void tela() {
        Intent intent = new Intent(this, Activity_user_interface.class);
        startActivity(intent);
    }

}