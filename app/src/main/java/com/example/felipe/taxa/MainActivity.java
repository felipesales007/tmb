package com.example.felipe.taxa;

import android.annotation.TargetApi;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity
{
    ////////////////////////////////////////// VARIAVEIS ///////////////////////////////////////////
    EditText edit_altura, edit_peso, edit_idade;
    ImageButton btn_calcular, btn_limpar;
    RadioButton radioButton_masculino, radioButton_feminino, radioButton_leve, radioButton_moderado, radioButton_intenso;
    CheckBox checkBox_semana, checkBox_mes;
    ////////// VARIAVEIS /////////
    double P, A, I, AF, Resultado, Resultado_semana, Resultado_mes;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////// FUNÇÃO DE AVISOS EM TELA //////////////////////////////////
    public void alerta(String titulo, String mensagem)
    {
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);

        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setNeutralButton("OK", null);
        alerta.show();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /////////////////////////////////////// PADRÃO /////////////////////////////////////////////
        getSupportActionBar().hide();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////// DECLARAÇÕES //////////////////////////////////////////
        edit_altura = (EditText) findViewById(R.id.edit_altura);
        edit_peso = (EditText) findViewById(R.id.edit_peso);
        edit_idade = (EditText) findViewById(R.id.edit_idade);

        btn_calcular = (ImageButton) findViewById(R.id.btn_calcular);
        btn_limpar = (ImageButton) findViewById(R.id.btn_limpar);

        radioButton_masculino = (RadioButton) findViewById(R.id.radioButton_masculino);
        radioButton_feminino = (RadioButton) findViewById(R.id.radioButton_feminimo);
        radioButton_leve = (RadioButton) findViewById(R.id.radioButton_leve);
        radioButton_moderado = (RadioButton) findViewById(R.id.radioButton_moderado);
        radioButton_intenso = (RadioButton) findViewById(R.id.radioButton_intenso);

        checkBox_semana = (CheckBox) findViewById(R.id.checkBox_semana);
        checkBox_mes = (CheckBox) findViewById(R.id.checkBox_mes);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////// FUNÇÃO LIMPAR CAMPOS /////////////////////////////////////
        btn_limpar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edit_altura.setText("");
                edit_peso.setText("");
                edit_idade.setText("");

                radioButton_masculino.setChecked(false);
                radioButton_feminino.setChecked(false);
                radioButton_leve.setChecked(false);
                radioButton_moderado.setChecked(false);
                radioButton_intenso.setChecked(false);

                checkBox_semana.setChecked(false);
                checkBox_mes.setChecked(false);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////// FUNÇÃO CALCULAR ///////////////////////////////////////
        btn_calcular.setOnClickListener(new View.OnClickListener()
        {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view)
            {
                ////////////////////////// CONDIÇÕES PARA DADOS EM BRANCO //////////////////////////
                if(
                        edit_altura.getText().length() == 0 ||
                        edit_peso.getText().length() == 0 ||
                        edit_idade.getText().length() == 0 ||
                                (radioButton_masculino.isChecked() == false && radioButton_feminino.isChecked() == false) ||
                                (radioButton_leve.isChecked() == false && radioButton_moderado.isChecked() == false && radioButton_intenso.isChecked() == false))
                {
                    alerta("Atenção", "Os dados altura, peso, idade, sexo e nível de atividade física devem ser preenchidos!");
                    return;
                }
                ////////////////////////////////////////////////////////////////////////////////////

                ///////////////////////////////// CÁLCULO //////////////////////////////////////////
                else
                {
                    A = Double.parseDouble(edit_altura.getText().toString());
                    P = Double.parseDouble(edit_peso.getText().toString());
                    I = Double.parseDouble(edit_idade.getText().toString());

                    //////////////////////////// SELEÇÃO SE MASCULINO //////////////////////////////
                    if(radioButton_masculino.isChecked())
                    {
                        if(radioButton_leve.isChecked())
                        {
                            AF = 1.55;
                        }
                        if(radioButton_moderado.isChecked())
                        {
                            AF = 1.78;
                        }
                        if(radioButton_intenso.isChecked())
                        {
                            AF = 2.10;
                        }

                        if(checkBox_semana.isChecked() || checkBox_mes.isChecked())
                        {
                            if(checkBox_semana.isChecked() && checkBox_mes.isChecked())
                            {
                                Resultado = 66.5 + (14 * P) + (5 * A) - (6.7 * I);
                                Resultado = Resultado * AF;
                                Resultado_semana = Resultado * 7;
                                Resultado_mes = Resultado * 30;
                                alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias\nSua TMB é de " +Resultado_semana+ " kcal semanal\nSua TMB é de " +Resultado_mes+ " kcal mensal");
                                return;
                            }

                            if(checkBox_semana.isChecked())
                            {
                                Resultado = 66.5 + (14 * P) + (5 * A) - (6.7 * I);
                                Resultado = Resultado * AF;
                                Resultado_semana = Resultado * 7;
                                alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias\nSua TMB é de " +Resultado_semana+ " kcal semanal");
                                return;
                            }

                            if(checkBox_mes.isChecked())
                            {
                                Resultado = 66.5 + (14 * P) + (5 * A) - (6.7 * I);
                                Resultado = Resultado * AF;
                                Resultado_mes = Resultado * 30;
                                alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias\nSua TMB é de " +Resultado_mes+ " kcal mensal");
                                return;
                            }
                        }
                        else
                        {
                            Resultado = 66.5 + (14 * P) + (5 * A) - (6.7 * I);
                            Resultado = Resultado * AF;
                            alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias");
                            return;
                        }
                    }
                    ////////////////////////////////////////////////////////////////////////////////

                    ///////////////////////////// SELEÇÃO SE FEMININO //////////////////////////////
                    else
                    {
                        if(radioButton_leve.isChecked())
                        {
                            AF = 1.56;
                        }
                        if(radioButton_moderado.isChecked())
                        {
                            AF = 1.64;
                        }
                        if(radioButton_intenso.isChecked())
                        {
                            AF = 1.82;
                        }

                        if(checkBox_semana.isChecked() || checkBox_mes.isChecked())
                        {
                            if(checkBox_semana.isChecked() && checkBox_mes.isChecked())
                            {
                                Resultado = 65.5 + (9.6 * P) + (1.8 * A) - (4.7 * I);
                                Resultado = Resultado * AF;
                                Resultado_semana = Resultado * 7;
                                Resultado_mes = Resultado * 30;
                                alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias\nSua TMB é de " +Resultado_semana+ " kcal semanal\nSua TMB é de " +Resultado_mes+ " kcal mensal");
                                return;
                            }

                            if(checkBox_semana.isChecked())
                            {
                                Resultado = 65.5 + (9.6 * P) + (1.8 * A) - (4.7 * I);
                                Resultado = Resultado * AF;
                                Resultado_semana = Resultado * 7;
                                alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias\nSua TMB é de " +Resultado_semana+ " kcal semanal");
                                return;
                            }

                            if(checkBox_mes.isChecked())
                            {
                                Resultado = 65.5 + (9.6 * P) + (1.8 * A) - (4.7 * I);
                                Resultado = Resultado * AF;
                                Resultado_mes = Resultado * 30;
                                alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias\nSua TMB é de " +Resultado_mes+ " kcal mensal");
                                return;
                            }
                        }
                        else
                        {
                            Resultado = 65.5 + (9.6 * P) + (1.8 * A) - (4.7 * I);
                            Resultado = Resultado * AF;
                            alerta("Taxa Metabólica Basal", "Sua TMB é de " +Resultado+ " kcal diárias");
                            return;
                        }
                    }
                    ////////////////////////////////////////////////////////////////////////////////
                }
                ////////////////////////////////////////////////////////////////////////////////////
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
}
