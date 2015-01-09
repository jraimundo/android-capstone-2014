package org.coursera.symptomsmanagement.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.coursera.symptomsmanagement.database.schema.Checkin;
import org.coursera.symptomsmanagement.database.schema.CheckinMedication;
import org.coursera.symptomsmanagement.database.schema.Doctor;
import org.coursera.symptomsmanagement.database.schema.Medication;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.database.schema.PatientMedication;
import org.coursera.symptomsmanagement.database.schema.SMData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SMDataSource {

	private static final String LOG_TAG = SMDataSource.class.getSimpleName();
	
	private SQLiteDatabase database;
	private SMSQLiteHelper dbHelper;
	
	public SMDataSource(Context context) {
		this.dbHelper = new SMSQLiteHelper(context);
	}
	
	public void open() {
		this.database = this.dbHelper.getWritableDatabase();
		this.insertSMData();
	}
	
	public void close() {
		this.database.close();
	}
	
	public List<Patient> getDoctorPatients(Integer doctorId) {
		List<Patient> patientList = new ArrayList<Patient>();
		
		String[] columns = {Patient.ID, Patient.MEDICAL_RECORD_NUMBER,
				Patient.FIRST_NAME,Patient.LAST_NAME, Patient.BIRTHDATE};
		String[] selectionArgs = {doctorId.toString()};
				
		Cursor cursor = this.database.query(Patient.TABLE_NAME,
				columns, Doctor.ID + "=?", selectionArgs, null, null, Patient.FIRST_NAME, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			patientList.add(this.getPatientFromCursor(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		
		return patientList;
	}
	
	public List<Patient> getDoctorPatients(Integer doctorId, String filter) {
		List<Patient> patientList = new ArrayList<Patient>();
		
		String[] columns = {Patient.ID, Patient.MEDICAL_RECORD_NUMBER,
				Patient.FIRST_NAME,Patient.LAST_NAME, Patient.BIRTHDATE};
		String selection = this.generateDoctorPatientsWhereClause(doctorId, filter);
		String[] selectionArgs = {doctorId.toString(), "%" + filter + "%", "%" +  filter + "%"};
				
		Cursor cursor = this.database.query(Patient.TABLE_NAME,
				columns, selection, selectionArgs, null, null, Patient.FIRST_NAME, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			patientList.add(this.getPatientFromCursor(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		
		return patientList;
	}
	
	public List<Medication> getPatientMedications(Integer patientId) {
		List<Medication> medicationList = new ArrayList<Medication>();
		
		String sql = "SELECT medication_id, medication_name FROM medication where medication_id IN (SELECT medication_id FROM patient_medication WHERE patient_id = ?);";
		String[] selectionArgs = {patientId.toString()};
		
		Cursor cursor = this.database.rawQuery(sql, selectionArgs);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Medication item = new Medication(cursor.getInt(0), cursor.getString(1));
			medicationList.add(item);
			cursor.moveToNext();
		}
		cursor.close();
		
		return medicationList;
	}
	
	public Integer getLatestTimestampByDoctor(Integer doctorId) {
		Integer timestamp = 1;
		
		String sql = "SELECT checkin_timestamp FROM checkin WHERE patient_id IN (SELECT patient_id FROM patient WHERE doctor_id = ?) ORDER BY checkin_timestamp DESC LIMIT 1";
		String[] selectionArgs = {doctorId.toString()};
		
		Cursor cursor = this.database.rawQuery(sql, selectionArgs);
		if(cursor.moveToFirst())
			timestamp = cursor.getInt(0);
		cursor.close();
		
		return timestamp;
	}
	
	public Patient getPatient(Integer patientId) {
		
		String[] columns = {Patient.MEDICAL_RECORD_NUMBER, Patient.FIRST_NAME,
				Patient.LAST_NAME, Patient.BIRTHDATE};
		String[] selectionArgs = {patientId.toString()};
		
		Cursor cursor = this.database.query(Patient.TABLE_NAME, columns, Patient.ID + "=?", selectionArgs,
				null, null, null, null);
		
		cursor.moveToFirst();
		Patient patient = new Patient(Integer.valueOf(patientId), cursor.getString(0),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		cursor.close();
		
		return patient;
	}
	
	public Medication getMedicationDescription(Integer medicationId) {
		String[] columns = {Medication.NAME, Medication.DESCRIPTION};
		String[] selectionArgs = {medicationId.toString()};
		
		Cursor cursor = this.database.query(Medication.TABLE_NAME, columns, Medication.ID + "=?", selectionArgs,
				null, null, null, null);
		
		cursor.moveToFirst();
		Medication medication = new Medication(cursor.getString(0), cursor.getString(1));
		cursor.close();
		
		return medication;
		
	}
	
	public List<Medication> getMedicationsPatientNotHave(Integer patientId) {
		List<Medication> medications = new ArrayList<Medication>();
		
		String sql = "SELECT medication_id, medication_name FROM medication WHERE medication_id NOT IN (SELECT medication_id FROM patient_medication WHERE patient_id = ?);";
		String[] selectionArgs = {patientId.toString()};
		
		Cursor cursor = this.database.rawQuery(sql, selectionArgs);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			medications.add(new Medication(cursor.getInt(0), cursor.getString(1)));
			cursor.moveToNext();
		}
		cursor.close();
		
		return medications;
	}
	
	public void insertPatientMedication(PatientMedication patientMedication) {
		ContentValues values = new ContentValues();
		values.put(Patient.ID, patientMedication.getPatientId());
		values.put(Medication.ID, patientMedication.getMedicationId());
		
		this.database.insert(PatientMedication.TABLE_NAME, null, values);
	}
	
	public void deletePatientMedication(PatientMedication patientMedication) {
		String whereClause = "patient_id=? AND medication_id=?";
		String[] whereArgs = {patientMedication.getPatientId().toString(),
				patientMedication.getMedicationId().toString()};
		
		this.database.delete(PatientMedication.TABLE_NAME, whereClause, whereArgs);
	}
	
	public String getPatientName(Integer patientId) {
		String[] columns = {Patient.FIRST_NAME, Patient.LAST_NAME};
		String[] selectionArgs = {patientId.toString()};
		
		Cursor cursor = this.database.query(Patient.TABLE_NAME, columns, Patient.ID + "=?", selectionArgs,
				null, null, null, null);
		
		cursor.moveToFirst();
		String patientName = cursor.getString(0) + " " + cursor.getString(1);
		cursor.close();
		
		return patientName;
	}
	
	public String getDoctorName(Integer doctorId) {
		String[] columns = {Doctor.FIRST_NAME, Doctor.LAST_NAME};
		String[] selectionArgs = {doctorId.toString()};
		
		Cursor cursor = this.database.query(Doctor.TABLE_NAME, columns, Doctor.ID + "=?", selectionArgs,
				null, null, null, null);
		
		cursor.moveToFirst();
		String doctorName = cursor.getString(0) + " " + cursor.getString(1);
		cursor.close();
		
		return doctorName;
	}
	
	public void inserSMData(SMData smData) {
		this.insertDoctors(smData.getDoctors());
		this.insertPatients(smData.getPatients());
		this.insertMedications(smData.getMedications());
		this.insertPatientMedications(smData.getPatientMedications());
		this.insertCheckins(smData.getCheckins());
	}
	
	public void insertCheckins(List<Checkin> checkins) {
		for (Checkin checkin : checkins) {
			this.insertCheckin(checkin);
		}
	}
	
	public void insertCheckin(Checkin checkin) {
		ContentValues values = new ContentValues();
		values.put(Checkin.ID, checkin.getCheckinId());
		values.put(Checkin.THROAT_PAIN_LEVEL, checkin.getThroatPainLevel());
		values.put(Checkin.EAT_DIFICULTY_LEVEL, checkin.getEatDificultyLevel());
		values.put(Checkin.CHECKIN_TIMESTAMP, checkin.getCheckinTimestamp() / 1000);
		values.put(Patient.ID, checkin.getPatientId());
		
		this.database.insert(Checkin.TABLE_NAME, null, values);
		
		for (CheckinMedication cm : checkin.getCheckinMedicationList()) {
			ContentValues cmValues = new ContentValues();
			cmValues.put(Checkin.ID, checkin.getCheckinId());
			cmValues.put(Medication.ID, cm.getMedicationId());
			cmValues.put(CheckinMedication.MEDICATION_TOKEN, cm.getMedicationToken());
			
			this.database.insert(CheckinMedication.TABLE_NAME, null, cmValues);
		}
		
	}
	
	public void insertDoctors(List<Doctor> doctors) {
		for (Doctor doctor : doctors) {
			ContentValues values = new ContentValues();
			values.put(Doctor.ID, doctor.getDoctorId());
			values.put(Doctor.FIRST_NAME, doctor.getFirstName());
			values.put(Doctor.LAST_NAME, doctor.getLastName());
			
			this.database.insert(Doctor.TABLE_NAME, null, values);
		}
	}
	
	public void insertPatients(List<Patient> patients) {
		for (Patient patient : patients) {
			ContentValues values = new ContentValues();
			values.put(Patient.ID, patient.getPatientId());
			values.put(Patient.MEDICAL_RECORD_NUMBER, patient.getMedicalRecordNumber());
			values.put(Patient.FIRST_NAME, patient.getFirstName());
			values.put(Patient.LAST_NAME, patient.getLastName());
			values.put(Patient.BIRTHDATE, patient.getBirthdate());
			values.put(Doctor.ID, patient.getDoctorId());
			
			this.database.insert(Patient.TABLE_NAME, null, values);
		}
	}
	
	public void insertMedications(List<Medication> medications) {
		for (Medication medication : medications) {
			ContentValues values = new ContentValues();
			values.put(Medication.ID, medication.getMedicationId());
			values.put(Medication.NAME, medication.getMedicationName());
			values.put(Medication.DESCRIPTION, medication.getDescription());
			
			this.database.insert(Medication.TABLE_NAME, null, values);
		}
	}
	
	public void insertPatientMedications(List<PatientMedication> patientMedications) {
		for (PatientMedication patientMedication : patientMedications) {
			this.insertPatientMedication(patientMedication);
		}
	}
	
	public List<String> getCheckinsByPatient(Integer patientId) {
		List<String> checkins = new ArrayList<String>();
		
		String[] columns = {Checkin.ID, Checkin.THROAT_PAIN_LEVEL, Checkin.EAT_DIFICULTY_LEVEL, Checkin.CHECKIN_TIMESTAMP};
		String selection = Patient.ID + "=?";
		String[] selectionArgs = {patientId.toString()};
		String orderBy = Checkin.CHECKIN_TIMESTAMP + " DESC";
		
		Cursor cursor = this.database.query(Checkin.TABLE_NAME, columns, selection, selectionArgs, null, null, orderBy);
		boolean moved =  cursor.moveToFirst();
		Log.d(LOG_TAG, String.valueOf(moved));
		while (!cursor.isAfterLast()) {
			StringBuffer sb = new StringBuffer();
			
			sb.append(new Date(cursor.getLong(3) * 1000L).toString());
			sb.append(" - ");
			
			Integer throatPainLevel = cursor.getInt(1);
			switch (throatPainLevel) {
			case 1:
				sb.append("Well-controlled - ");
				break;
			case 2:
				sb.append("Moderate - ");
				break;
			case 3:
				sb.append("Severe - ");
				break;
			default:
				sb.append("Well-controlled - ");
				break;
			}
			
			Integer eatDificulty = cursor.getInt(2);
			switch (eatDificulty) {
			case 1:
				sb.append("No dificulty");
				break;
			case 2:
				sb.append("Some Dificulty");
				break;
			case 3:
				sb.append("Can't eat");
				break;
			default:
				sb.append("No dificulty");
				break;
			}
			
			String sqlCM = "select med.medication_name, cm.medication_token from medication as med join checkin_medication as cm on med.medication_id = cm.medication_id where cm.checkin_id=?;";
			String[] selectionArgsCM = {String.valueOf(cursor.getInt(0))};
			Cursor cursorCM  = this.database.rawQuery(sqlCM, selectionArgsCM);
			
			cursorCM.moveToFirst();
			while (!cursorCM.isAfterLast()) {
				sb.append(" - ");
				sb.append(cursorCM.getString(0));
				sb.append(": ");
				int medicationToken = cursorCM.getInt(1);
				if (medicationToken == 1) {
					sb.append(" YES");
				} else {
					sb.append(" NO");
				}
				
				cursorCM.moveToNext();
			}
			
			checkins.add(sb.toString());
			cursor.moveToNext();
		}
		
		return checkins;
	}
	
	public void truncateDatabase() {
		this.database.execSQL("DELETE FROM " + CheckinMedication.TABLE_NAME + ";");
		this.database.execSQL("DELETE FROM " + Checkin.TABLE_NAME + ";");
		this.database.execSQL("DELETE FROM " + PatientMedication.TABLE_NAME + ";");
		this.database.execSQL("DELETE FROM " + Medication.TABLE_NAME + ";");
		this.database.execSQL("DELETE FROM " + Patient.TABLE_NAME + ";");
		this.database.execSQL("DELETE FROM " + Doctor.TABLE_NAME + ";");
	}
	
	
	private String generateDoctorPatientsWhereClause(Integer doctorId, String filter) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(Doctor.ID);
		sb.append("= ? AND (");
		sb.append(Patient.FIRST_NAME);
		sb.append(" LIKE ? OR ");
		sb.append(Patient.LAST_NAME);
		sb.append(" LIKE ?)");
		
		return sb.toString();
	}
	
	private Patient getPatientFromCursor(Cursor cursor) {
		
		return new Patient(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4));
	}
	
	private void insertSMData() {
		
//		// Doctor
//		this.database.execSQL("INSERT INTO doctor (doctor_id, first_name, last_name) VALUES (2, 'John', 'Raymond');");
//		this.database.execSQL("INSERT INTO doctor (doctor_id, first_name, last_name) VALUES (1, 'Marcos', 'Moreno');");
//		
//		// Patient
//		this.database.execSQL("INSERT INTO patient (medical_record_number, first_name, last_name, birthdate, doctor_id, patient_id) VALUES ('efgh5678', 'Thelma', 'Lyons', '1965-11-12', 2, 2);");
//		this.database.execSQL("INSERT INTO patient (medical_record_number, first_name, last_name, birthdate, doctor_id, patient_id) VALUES ('ijkl9012', 'Malcolm', 'Barnett', '1978-07-02', 1, 3);");
//		this.database.execSQL("INSERT INTO patient (medical_record_number, first_name, last_name, birthdate, doctor_id, patient_id) VALUES ('mnop3456', 'Kelli', 'Foster', '1992-02-22', 1, 4);");
//		this.database.execSQL("INSERT INTO patient (medical_record_number, first_name, last_name, birthdate, doctor_id, patient_id) VALUES ('abcd1234', 'Conraad', 'Gibson', '1950-05-15', 1, 1);");
//		this.database.execSQL("INSERT INTO patient (medical_record_number, first_name, last_name, birthdate, doctor_id, patient_id) VALUES ('qrst7890', 'Mario', 'Alexander', '1986-08-17', 2, 5);");
//		
//		// Medication
//		this.database.execSQL("INSERT INTO medication (medication_name, description, medication_id) VALUES ('amifostine', 'A drug used as a chemoprotective drug to control some of the side effects of chemotherapy and radiation therapy.', 1);");
//		this.database.execSQL("INSERT INTO medication (medication_name, description, medication_id) VALUES ('ibritumomab tiuxetan', 'A monoclonal antibody that is used to treat certain types of B-cell non-Hodgkin lymphoma and is being studied in the treatment and detection of other types of B-cell tumors. Monoclonal antibodies are made in the laboratory and can locate and bind to substances in the body, including cancer cells. Ibritumomab binds to the protein called CD20, which is found on B cells. It is linked to the compound tiuxetan. This allows certain radioisotopes to be attached before it is given to a patient. It is a type of monoclonal antibody-chelator conjugate. Also called Zevalin.', 2);");
//		this.database.execSQL("INSERT INTO medication (medication_name, description, medication_id) VALUES ('5-fluorouracil', 'A drug used to treat cancers of the breast, stomach, and pancreas, and certain types of colorectal and head and neck cancers. It is also used in a cream to treat basal cell skin cancer and actinic keratosis (a skin condition that may become cancer). It is being studied in the treatment of other conditions and types of cancer. 5-fluorouracil stops cells from making DNA and it may kill cancer cells. It is a type of antimetabolite. Also called 5-FU, Adrucil, Efudex, Fluoroplex, and fluorouracil.', 3);");
//		this.database.execSQL("INSERT INTO medication (medication_name, description, medication_id) VALUES ('cisplatin', 'A drug used to treat malignant mesothelioma, non-small cell lung cancer, squamous cell carcinoma of the head and neck, and cancers of the bladder, cervix, ovaries, and testes. It is used in patients whose cancer cannot be treated with or has not gotten better with other anticancer treatment. It is also being studied in the treatment of other types of cancer. Cisplatin contains the metal platinum. It kills cancer cells by damaging their DNA and stopping them from dividing. It is a type of DNA crosslinking agent. Also called Platinol and Platinol-AQ.', 4);");
//		this.database.execSQL("INSERT INTO medication (medication_name, description, medication_id) VALUES ('samarium 153', 'A radioactive substance used in the treatment of bone cancer and bone metastases (cancers that have spread from the original tumor to the bone). Samarium 153 is a radioactive form of the element samarium. It collects in bone, where it releases radiation that may kill cancer cells. It is a type of radioisotope.', 5);");
//		this.database.execSQL("INSERT INTO medication (medication_name, description, medication_id) VALUES ('strontium-89', 'A radioactive form of the metal strontium that is taken up by a part of growing bone. It is being studied in the treatment of bone pain caused by some types of cancer.', 6);");
//		
//		// Patient_Medication
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (1, 1);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (2, 2);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (3, 3);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (4, 4);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (5, 5);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (2, 3);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (3, 1);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (3, 5);");
//		this.database.execSQL("INSERT INTO patient_medication (patient_id, medication_id) VALUES (4, 2);");
		
//		this.database.execSQL("DROP TABLE 'checkin_medication'");
//		this.database.execSQL("DROP TABLE 'checkin'");
//		this.database.execSQL("CREATE TABLE 'checkin' ('checkin_id' INTEGER PRIMARY KEY,'throat_pain_level' INTEGER,'eat_dificulty_level' INTEGER,'checkin_timestamp' INTEGER,'patient_id' INTEGER,    FOREIGN KEY(patient_id) REFERENCES patient(patient_id));");
//		this.database.execSQL("CREATE TABLE 'checkin_medication' ('checkin_id' INTEGER,'medication_id' INTEGER,'medication_token' INTEGER,PRIMARY KEY(checkin_id, medication_id),FOREIGN KEY(checkin_id) REFERENCES checkin(checkin_id),FOREIGN KEY(medication_id) REFERENCES medication(medication_id));");
	}
}
