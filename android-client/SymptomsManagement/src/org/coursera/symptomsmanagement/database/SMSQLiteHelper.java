package org.coursera.symptomsmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SMSQLiteHelper extends SQLiteOpenHelper {
	
	private static final String LOG_TAG = SMSQLiteHelper.class.getCanonicalName();

	private static final String DATABASE_NAME = "SymptomsManagement.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_CREATION_DOCTOR = "CREATE TABLE doctor ('doctor_id' INTEGER PRIMARY KEY,'first_name' TEXT,'last_name' TEXT);";
	private static final String TABLE_CREATION_MEDICATION = "CREATE TABLE 'medication' ('medication_id' INTEGER PRIMARY KEY,'medication_name' TEXT,'description' TEXT);";
	private static final String TABLE_CREATION_PATIENT = "CREATE TABLE patient ('patient_id' INTEGER PRIMERY KEY,'medical_record_number' REAL,'first_name' TEXT,'last_name' TEXT,'birthdate' TEXT,'doctor_id' INTEGER,FOREIGN KEY(doctor_id) REFERENCES doctor(doctor_id));";
	private static final String TABLE_CREATION_CHECKIN = "CREATE TABLE 'checkin' ('checkin_id' INTEGER PRIMARY KEY,'throat_pain_level' INTEGER,'eat_dificulty_level' INTEGER,'checkin_timestamp' INTEGER,'patient_id' INTEGER, FOREIGN KEY(patient_id) REFERENCES patient(patient_id));";
	private static final String TABLE_CREATION_PATIENT_MEDICATION = "CREATE TABLE 'patient_medication' ('patient_id' INTEGER,'medication_id' INTEGER,PRIMARY KEY(patient_id, medication_id),FOREIGN KEY(patient_id) REFERENCES patient(patient_id),FOREIGN KEY(medication_id) REFERENCES medication(patient_id));";
	private static final String TABLE_CREATION_CHECKIN_MEDICATION = "CREATE TABLE 'checkin_medication' ('checkin_id' INTEGER,'medication_id' INTEGER,'medication_token' INTEGER,PRIMARY KEY(checkin_id, medication_id),FOREIGN KEY(checkin_id) REFERENCES checkin(checkin_id),FOREIGN KEY(medication_id) REFERENCES medication(medication_id));";


	public SMSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATION_DOCTOR);
		db.execSQL(TABLE_CREATION_MEDICATION);
		db.execSQL(TABLE_CREATION_PATIENT);
		db.execSQL(TABLE_CREATION_CHECKIN);
		db.execSQL(TABLE_CREATION_PATIENT_MEDICATION);
		db.execSQL(TABLE_CREATION_CHECKIN_MEDICATION);
		
		Log.d(LOG_TAG, "Database " + DATABASE_NAME + " created.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
		
}
