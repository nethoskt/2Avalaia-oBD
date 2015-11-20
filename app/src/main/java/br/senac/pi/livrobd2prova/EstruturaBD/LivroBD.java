package br.senac.pi.livrobd2prova.EstruturaBD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aluno on 20/11/2015.
 */
public class LivroBD extends SQLiteOpenHelper {
    private static final String TAG = "sql";
    private static final String BANCO_DADOS = "cursoandroid.sqlite";
    private static final int VERSAO_BANCO = 1;

    public LivroBD(Context context) {
        super(context, BANCO_DADOS, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela livros...");
        db.execSQL("CREATE TABLE IF NOT EXISTS livro (_id integer primary key autoincrement," +
                "livro text, editora text);");
        Log.d(TAG, "Tabela livro criada com sucesso!");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long save(Livro livro) {
        long id = livro.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put("livro", livro.getLivro());
            values.put("editora", livro.getEditora());
            if (id != 0) {
                String _id = String.valueOf(livro.getId());
                String[] whereArgs = new String[]{_id};
                int count = db.update("livro", values, "_id=?", whereArgs);
                return count;
            } else {
                id = db.insert("livro", "", values);
                return id;
            }
        }finally {
            db.close();
        }
    }
    public int delete(Livro livro) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete("livro", "_id=?", new String[]{String.valueOf(livro.getId())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }
    public List<Livro> findAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.query("livro", null, null, null, null, null, null, null);
            return toList(cursor);
        } finally {
            db.close();
        }
    }
    public List<Livro> toList(Cursor cursor) {
            List<Livro> livros = new ArrayList<Livro>();
        if (cursor.moveToFirst()) {
            do {
                Livro livro = new Livro();
                livros.add(livro);
                livro.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                livro.setLivro(cursor.getString(cursor.getColumnIndex("livro")));
                livro.setEditora(cursor.getString(cursor.getColumnIndex("editora")));
            } while (cursor.moveToNext());
        }
        return livros;
    }
}



