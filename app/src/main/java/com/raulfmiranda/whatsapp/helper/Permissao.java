package com.raulfmiranda.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes) {

        if(Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<>();

            //Percorre as permissões passadas, verificando uma a uma se já tem a permissão liberada
            for (String permissao : permissoes) {
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) listaPermissoes.add(permissao);
            }

            if(listaPermissoes.isEmpty()) return true;

            //Substitui o código abaixo: listaPermissoes.toArray(new String[0])
            //String[] novasPermissoes = new String[listaPermissoes.size()];
            //listaPermissoes.toArray(novasPermissoes);

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, listaPermissoes.toArray(new String[0]), requestCode);
        }

        return true;
    }
}
