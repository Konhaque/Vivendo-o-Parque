package com.projetobeta.vidanoparque.funcionalidades.perfil;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.projetobeta.vidanoparque.R;
import com.projetobeta.vidanoparque.bd.Conexao;
import com.projetobeta.vidanoparque.bd.Repository;
import com.projetobeta.vidanoparque.bd.Usuario;

public class Informacoes extends Fragment {

    private ImageView edit;
    private ImageView save;
    private TextView profissao;
    private TextView formacao;
    private TextView endereco;
    private EditText editProfissao;
    private EditText editFormacao;
    private EditText editEndereco;
    private AlertDialog alertDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.informacoes,container,false);
    }

    @Override
    public void onStart() {
        iniciarObjetos();
        setEdit();
        salvar();
        verficarinformacoes();
        super.onStart();
    }

    private void iniciarObjetos(){
        edit = (ImageView) getActivity().findViewById(R.id.editar);
        profissao = (TextView) getActivity().findViewById(R.id.txtprofissao);
        formacao = (TextView) getActivity().findViewById(R.id.txtescolaridade);
        endereco = (TextView) getActivity().findViewById(R.id.txtendereco);
        editEndereco = (EditText) getActivity().findViewById(R.id.editendereco);
        editProfissao = (EditText) getActivity().findViewById(R.id.editprofissao);
        editFormacao = (EditText) getActivity().findViewById(R.id.editescolaridade);
        save = (ImageView) getActivity().findViewById(R.id.salvar);
    }

    private void setEdit(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habilitarEdicao();
            }
        });
    }

    private void habilitarEdicao(){
        profissao.setVisibility(View.GONE);
        formacao.setVisibility(View.GONE);
        endereco.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
        editFormacao.setVisibility(View.VISIBLE);
        editEndereco.setVisibility(View.VISIBLE);
        editProfissao.setVisibility(View.VISIBLE);
        editProfissao.setText(profissao.getText());
        editEndereco.setText(endereco.getText());
        editFormacao.setText(formacao.getText());
        save.setVisibility(View.VISIBLE);
    }

    private void verficarinformacoes(){
        Usuario usuario = new Repository(getContext()).getPerfil();
        editFormacao.setVisibility(View.GONE);
        editEndereco.setVisibility(View.GONE);
        editProfissao.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        profissao.setVisibility(View.VISIBLE);
        formacao.setVisibility(View.VISIBLE);
        endereco.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        if(usuario.getProfissao() != null ) profissao.setText(usuario.getProfissao());
        if(usuario.getFormacao() != null) formacao.setText(usuario.getFormacao());
        if(usuario.getEndereco() != null) endereco.setText(usuario.getEndereco());
    }

    private void salvar(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Salvar alterações");
                builder.setMessage("Você deseja salvar as alterações?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        if(verificarAlteracao()) {
                            alertDialog.dismiss();
                            LayoutInflater inflater = getLayoutInflater();
                            builder1.setView(inflater.inflate(R.layout.activity_main, null));
                            builder1.setCancelable(true);
                            alertDialog = builder1.create();
                            alertDialog.show();
                            Usuario usuario = new Usuario();
                            usuario.setId(new Repository(getContext()).getIdUsuario());
                            usuario.setProfissao(editProfissao.getText().toString());
                            usuario.setEndereco(editEndereco.getText().toString());
                            usuario.setFormacao(editFormacao.getText().toString());
                            new Conexao(getActivity(), alertDialog).salvarAlteracoesPerfil(usuario,formacao,profissao,endereco);
                            verficarinformacoes();
                        }else {
                            alertDialog.dismiss();
                            builder1.setTitle("Alterações não salvas!");
                            builder1.setMessage("Verifique as informações e tente novamente");
                            builder1.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog = builder1.create();
                            alertDialog.show();
                        }
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        verficarinformacoes();
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    private boolean verificarAlteracao(){
        boolean valido = false;
        if(editProfissao.getText().toString().length() == 0) valido = false;
        else if(editEndereco.getText().toString().length() == 0) valido = false;
        else if (editFormacao.getText().toString().length() == 0) valido = false;
        else valido = true;
        return valido;
    }


}
