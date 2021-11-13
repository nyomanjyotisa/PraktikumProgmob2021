package id.jyotisa.praktikumprogmob2021.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_jobify";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tb_job";
    private static final String COMPANY_NAME_COLUMN = "companyName";
    private static final String COUNTRY_COLUMN = "country";
    private static final String JOB_TITLE_COLUMN = "jobTitle";
    private static final String JOB_DESCRIPTION_COLUMN = "jobDescription";
    private static final String JOB_TYPE_COLUMN = "jobType";
    private static final String BENEFITS_COLUMN = "benefits";
    private static final String SALARY_COLUMN = "salary";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                +TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, "
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
        db.execSQL("DELETE FROM tb_job WHERE id='" + id + "'");
    }
}
