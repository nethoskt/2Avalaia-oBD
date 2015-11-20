package br.senac.pi.livrobd2prova;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.livrobd2prova.EstruturaBD.Livro;
import br.senac.pi.livrobd2prova.EstruturaBD.LivroBD;

public class ListaLivrosActivity extends AppCompatActivity {
    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private static final String campos[] = {"livro", "editora", "_id"};
    ListView listView;
    LivroBD livroBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livros);
                listView = (ListView) findViewById(R.id.listViewLivros);
                livroBD = new LivroBD(this);
                database = livroBD.getWritableDatabase();
        findViewById(R.id.btnVerLivros).setOnClickListener(verLivros());
        }
    private View.OnClickListener verLivros() {
        return new View.OnClickListener(){
          @Override
        public void onClick(View view){
              Cursor livros = database.query("livro", campos,null,null,null,null,null);
              if (livros.getCount()>0) {
                  dataSource = new SimpleCursorAdapter(ListaLivrosActivity.this, R.layout.row, livros, campos, new int[]{R.id.txtNomeLivro, R.id.txtEditoraLivro});
                  listView.setAdapter(dataSource);
              }else {
                  Toast.makeText(ListaLivrosActivity.this,getString(R.string.nenhum_cadastro),Toast.LENGTH_LONG).show();
              }
          }
        };
    }
    private AdapterView.OnItemClickListener deletarLivro(){
            return new AdapterView.OnItemClickListener(){
                @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                  final long livroSelecionado = id;
                  final int posicao = position;
                  AlertDialog.Builder builder = new AlertDialog.Builder(ListaLivrosActivity.this);
                  builder.setTitle("Opções");
                  builder.setMessage("O que deseja?");
                  builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int id) {
                          String codigo;
                          Livro l = new Livro();
                          Cursor livro = database.query("livro", campos, null, null, null, null, null);
                          livro.moveToPosition(posicao);
                          codigo = livro.getString(livro.getColumnIndexOrThrow("_id"));
                          Intent intent = new Intent(getApplicationContext(), EditaLivrosActivity.this);
                          intent.putExtra("id", codigo);
                          startActivity(intent);
                          finish();
                      }
                  });
                  builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int id) {
                          Log.i("livro", "ID do livro selecionado: " + livroSelecionado);
                          Livro livro = new Livro();
                          livro.setId(livroSelecionado);
                          livroBD.delete(livro);
                          listView.invalidateViews();
                      }
                  });
                    AlertDialog dialog = builder.create();
                    dialog.show();
              }
                };
    }
    }


