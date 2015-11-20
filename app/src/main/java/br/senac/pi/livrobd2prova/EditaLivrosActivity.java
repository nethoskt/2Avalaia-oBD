package br.senac.pi.livrobd2prova;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.senac.pi.livrobd2prova.EstruturaBD.Livro;
import br.senac.pi.livrobd2prova.EstruturaBD.LivroBD;

public class EditaLivrosActivity extends AppCompatActivity {
    private LivroBD livroBD;
    private SQLiteDatabase db;
    private EditText edtEditarNomeLivro, edtEditarEditoraLivro;
    private TextView txtIdLivro;
    private String id;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_livros);
        id = getIntent().getStringExtra("id");
        livroBD = new LivroBD(this);
        txtIdLivro = (TextView) findViewById(R.id.txtIdLivro);
        edtEditarNomeLivro = (EditText) findViewById(R.id.edtAlteraNomeLivro);
        edtEditarEditoraLivro = (EditText) findViewById(R.id.edtAlteraEditoraLivro);
        cursor = carregaCarro(Integer.parseInt(id));
        txtIdLivro.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        edtEditarNomeLivro.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        edtEditarEditoraLivro.setText(cursor.getString(cursor.getColumnIndexOrThrow("marca")));
        findViewById(R.id.btnAlterar).setOnClickListener(editarLivro());
    }

    private Cursor carregaCarro(int id) {
        db = LivroBD.getWritableDatabase();
        String[] campos = {"_id", "livro", "editora"};
        String whereArgs = String.valueOf(id);
        cursor = db.query("livro", campos, whereArgs, null, null, null, null);
        if(cursor!= null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    private View.OnClickListener editarLivro() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = livroBD.getWritableDatabase();
                ContentValues values = new ContentValues();
                String whereArgs = id;
                Log.i("curso", "ID capturado: " + id);
                values.put("livro", edtEditarNomeLivro.getText().toString());
                values.put("editora", edtEditarEditoraLivro.getText().toString());
                db.update("livro", values, "_id = " + whereArgs, null);
                db.close();
            }
        };
    }
}
