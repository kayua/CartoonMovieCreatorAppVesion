package app.mosquito.appmosquito.appmosquito;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DaemonService extends AppCompatActivity {
    public static final String CHANNEL_ID = "Serviço de plano de fundo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String a = new String();
        SharedPreferences settings= getSharedPreferences("PersonalDatabase", 0);
        if (isServicoRodando(a) != true){

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("daemonActivity", "off");
            editor.commit();
            startService();

        }else{
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("daemonActivity", "on");
            editor.commit();
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
        Intent intent = new Intent(this, ActivityUserInterface.class);

        startActivity(intent);
    }

}