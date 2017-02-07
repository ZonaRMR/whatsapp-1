package com.raulfmiranda.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.raulfmiranda.whatsapp.R;
import com.raulfmiranda.whatsapp.helper.Preferencias;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codArea;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = (EditText) findViewById(R.id.edit_nome);
        telefone = (EditText) findViewById(R.id.edit_telefone);
        codPais = (EditText) findViewById(R.id.edit_cod_pais);
        codArea = (EditText) findViewById(R.id.edit_cod_area);
        cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        // Definir as máscaras
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskCodPais = new MaskTextWatcher(codPais, simpleMaskCodPais);
        MaskTextWatcher maskCodArea = new MaskTextWatcher(codArea, simpleMaskCodArea);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);

        telefone.addTextChangedListener(maskTelefone);
        codPais.addTextChangedListener(maskCodPais);
        codArea.addTextChangedListener(maskCodArea);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString() +
                        codArea.getText().toString() +
                        telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                //Gerar token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("TOKEN", usuario.get("token") + usuario.get("nome") + usuario.get("telefone"));
            }
        });
    }
}
