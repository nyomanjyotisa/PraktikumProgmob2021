package id.jyotisa.praktikumprogmob2021.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import es.dmoral.toasty.Toasty;
import id.jyotisa.praktikumprogmob2021.AddJobActivity;
import id.jyotisa.praktikumprogmob2021.DetailActivity;
import id.jyotisa.praktikumprogmob2021.ListActivity;

public class DBHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "db_jobify";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tb_job";
    private static final String ID_COLUMN = "id";
    private static final String COMPANY_NAME_COLUMN = "companyName";
    private static final String COUNTRY_COLUMN = "country";
    private static final String JOB_TITLE_COLUMN = "jobTitle";
    private static final String JOB_DESCRIPTION_COLUMN = "jobDescription";
    private static final String JOB_TYPE_COLUMN = "jobType";
    private static final String BENEFITS_COLUMN = "benefits";
    private static final String SALARY_COLUMN = "salary";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                +TABLE_NAME+"("
                +ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COMPANY_NAME_COLUMN+" TEXT, "
                +COUNTRY_COLUMN+" TEXT, "
                +JOB_TITLE_COLUMN+" TEXT, "
                +JOB_DESCRIPTION_COLUMN+" TEXT, "
                +JOB_TYPE_COLUMN+" TEXT, "
                +BENEFITS_COLUMN+" TEXT, "
                +SALARY_COLUMN+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertJob (String companyName, String country, String jobTitle, String jobDesc, String jobType, String benefits, Integer salary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPANY_NAME_COLUMN, companyName);
        contentValues.put(COUNTRY_COLUMN, country);
        contentValues.put(JOB_TITLE_COLUMN, jobTitle);
        contentValues.put(JOB_DESCRIPTION_COLUMN, jobDesc);
        contentValues.put(JOB_TYPE_COLUMN, jobType);
        contentValues.put(BENEFITS_COLUMN, benefits);
        contentValues.put(SALARY_COLUMN, salary);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor readJobs() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT*FROM tb_job ORDER BY id DESC");
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void deleteData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE_NAME, ID_COLUMN + "=" + id, null) > 0){
            Toasty.success(context, "Delete Success", Toast.LENGTH_SHORT, true).show();
        }else {
            Toasty.error(context, "Delete Failed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void updateJob (Integer id, String companyName, String country, String jobTitle, String jobDesc, String jobType, String benefits, Integer salary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPANY_NAME_COLUMN, companyName);
        contentValues.put(COUNTRY_COLUMN, country);
        contentValues.put(JOB_TITLE_COLUMN, jobTitle);
        contentValues.put(JOB_DESCRIPTION_COLUMN, jobDesc);
        contentValues.put(JOB_TYPE_COLUMN, jobType);
        contentValues.put(BENEFITS_COLUMN, benefits);
        contentValues.put(SALARY_COLUMN, salary);
        if(db.update(TABLE_NAME, contentValues, ID_COLUMN + "=" + id, null) > 0){
            Toasty.success(context, "Update Success", Toast.LENGTH_SHORT, true).show();
            Intent intent = new Intent(context, ListActivity.class);
            context.startActivity(intent);
        }else {
            Toasty.error(context, "Update Failed", Toast.LENGTH_SHORT, true).show();
        }
    }
}
