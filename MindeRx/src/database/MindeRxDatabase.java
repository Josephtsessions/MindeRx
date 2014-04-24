	package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MindeRxDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "minderxDatabase";
	
	private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE "
			+ "employee("
			+ "essn TEXT PRIMARY KEY,"
			+ "first TEXT,"
			+ "last TEXT,"
			+ "username TEXT,"
			+ "password TEXT)";
	
	private static final String CREATE_TABLE_PATIENT = "CREATE TABLE "
			+ "patient("
			+ "pssn TEXT PRIMARY KEY,"
			+ "first TEXT,"
			+ "last TEXT)";

	private static final String CREATE_TABLE_ASSIGNMENT = "CREATE TABLE "
			+ "assignment("
			+ "pssn TEXT,"
			+ "essn TEXT,"
			+ "floor_nurse_essn TEXT,"
			+ "FOREIGN KEY(pssn) REFERENCES patient(pssn),"
			+ "FOREIGN KEY(essn) REFERENCES employee(essn),"
			+ "FOREIGN KEY(floor_nurse_essn) REFERENCES employee(essn))";
	
	private static final String CREATE_TABLE_VITAL_DATA_POINTS = "CREATE TABLE "
			+ "vital_data_point("
			+ "vital_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "vital_name TEXT,"
			+ "pssn TEXT,"
			+ "essn TEXT,"
			+ "date TEXT,"
			+ "FOREIGN KEY(pssn) REFERENCES patient(pssn),"
			+ "FOREIGN KEY(essn) REFERENCES employee(essn))";
	
	private static final String CREATE_TABLE_HEART_RATE = "CREATE TABLE "
			+ "heart_rate("
			+ "vital_id INTEGER,"
			+ "beats_per_minute INTEGER,"
			+ "FOREIGN KEY(vital_id) REFERENCES vital_data_point(vital_id))";
	
	private static final String CREATE_TABLE_TEMPERATURE = "CREATE TABLE "
			+ "temperature("
			+ "vital_id INTEGER,"
			+ "value INTEGER,"
			+ "FOREIGN KEY(vital_id) REFERENCES vital_data_point(vital_id))";
	
	private static final String CREATE_TABLE_SA_LEVEL = "CREATE TABLE "
			+ "sa_level("
			+ "vital_id INTEGER,"
			+ "percentage_level REAL,"
			+ "FOREIGN KEY(vital_id) REFERENCES vital_data_point(vital_id))";
	
	private static final String CREATE_TABLE_BLOOD_PRESSURE = "CREATE TABLE "
			+ "blood_pressure("
			+ "vital_id INTEGER,"
			+ "systolic INTEGER,"
			+ "diastolic INTEGER,"
			+ "FOREIGN KEY(vital_id) REFERENCES vital_data_point(vital_id))";
	
	public MindeRxDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_TABLE_EMPLOYEE);
		db.execSQL(CREATE_TABLE_PATIENT);
		db.execSQL(CREATE_TABLE_ASSIGNMENT);
		db.execSQL(CREATE_TABLE_VITAL_DATA_POINTS);
		db.execSQL(CREATE_TABLE_HEART_RATE);
		db.execSQL(CREATE_TABLE_TEMPERATURE);
		db.execSQL(CREATE_TABLE_SA_LEVEL);
		db.execSQL(CREATE_TABLE_BLOOD_PRESSURE);
		
		setupSampleEmployees(db);
		setupSamplePatients(db);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {
		
	}
	
	public String getPasswordFromUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"password"};
		Cursor cursor = db.query("EMPLOYEE", columns, "'" + username + "' = username", null, null, null, null);
		
		
		String password = "";
		if (cursor.getCount() >= 1) {
			cursor.moveToNext();
			password = cursor.getString(0);			
		}
		cursor.close();
		db.close();
		
		return password;
		
	}
	
	public String getESSNFromUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"essn"};
		Cursor cursor = db.query("EMPLOYEE", columns, "'" + username + "' = username", null, null, null, null);
		
		cursor.moveToNext();
		String essn;
		essn = cursor.getString(0);
		
		cursor.close();
		db.close();
		
		return essn;
	}
	
	public ArrayList<String> getPssnsFromEssn(String essn) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"pssn"};
		Cursor cursor = db.query("ASSIGNMENT", columns, "'" + essn + "' = essn", null, null, null, null);

		ArrayList<String> pssns = new ArrayList<String>();
		
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String pssn = cursor.getString(0);
			
				pssns.add(pssn);
			}
		
			cursor.close();
			db.close();
		}
		
		return pssns;
	}
	
	public ArrayList<String> getPatientNamesFromPssns(ArrayList<String> pssns) {
		
		ArrayList<String> names = new ArrayList<String>();
		
		for (int i = 0; i < pssns.size(); i++) {

			System.out.println( pssns.get(i) );
			
			names.add(getPatientNameFromPssn( pssns.get(i) ) );
			
		}
			
		return names;		
	}
	
	public String getPatientNameFromPssn(String pssn) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"first", "last"};
		
		Cursor cursor = db.query("PATIENT", columns, "'" + pssn + "' = pssn", null, null, null, null);
		
		String name = "";
		
		if (cursor != null) {
			cursor.moveToNext();
			String firstName = cursor.getString(0);
			String lastName = cursor.getString(1);
			
			name = firstName + " " + lastName;
			
		}

		cursor.close();
		db.close();
		
		return name;
		
	}
	
	public ArrayList<String> getEssns() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<String> essns = new ArrayList<String>();
		String query = "SELECT ESSN FROM EMPLOYEE";
		
		Cursor cursor = db.rawQuery(query, null);
		
		while ( cursor.moveToNext() ) {
			String essn = cursor.getString(0);
			
			essns.add(essn);
		}
		
		cursor.close();
		db.close();
		
		return essns;
		
	}
	
	public ArrayList<String> getStaffNames(ArrayList<String> essns) {
		
		ArrayList<String> names = new ArrayList<String>();
		
		for (int i = 0; i < essns.size(); i++) {
			
			String name = getStaffNameFromEssn( essns.get(i) );
			
			names.add(name);
			
		}
		
		return names;
		
	}
	
	public String getStaffNameFromEssn(String essn) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"first", "last"};
		
		Cursor cursor = db.query("EMPLOYEE", columns, "'" + essn + "' = essn", null, null, null, null);
		
		String name = "";
		
		if (cursor != null) {
			cursor.moveToNext();
			String firstName = cursor.getString(0);
			String lastName = cursor.getString(1);
			
			name = firstName + " " + lastName;
			
		}
		
		cursor.close();
		db.close();
		
		return name;
		
	}
	
	public void recordBloodPressure(Integer systolic, Integer diastolic, String essn, String pssn) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(new Date());
		
		ContentValues vitalRecording = new ContentValues();
		vitalRecording.put("vital_name", "Blood Pressure");
		vitalRecording.put("pssn", pssn);
		vitalRecording.put("essn", essn);
		vitalRecording.put("date", strDate);
		
		db.insert("vital_data_point", null, vitalRecording);
		
		Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
		
		Integer lastId = 0;
		
		if (cursor != null) {
			cursor.moveToNext();
			lastId = cursor.getInt(0);
		}
		
		cursor.close();
		
		ContentValues bloodPressureVitalRecording = new ContentValues();
		bloodPressureVitalRecording.put("vital_id", lastId);
		bloodPressureVitalRecording.put("systolic", systolic);
		bloodPressureVitalRecording.put("diastolic", diastolic);
		
		db.insert("blood_pressure", null, bloodPressureVitalRecording);
		
		db.close();
	}
	
	public void recordHeartRate(Integer bpm, String essn, String pssn) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(new Date());
		
		ContentValues vitalRecording = new ContentValues();
		vitalRecording.put("vital_name", "Heart Rate");
		vitalRecording.put("pssn", pssn);
		vitalRecording.put("essn", essn);
		vitalRecording.put("date", strDate);
		
		db.insert("vital_data_point", null, vitalRecording);
		
		Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
		
		Integer lastId = 0;
		
		if (cursor != null) {
			cursor.moveToNext();
			lastId = cursor.getInt(0);
		}
		
		cursor.close();
		
		System.out.println("Last ID was: " + String.valueOf(lastId) + " and BPM was: " + String.valueOf(bpm));
		
		ContentValues heartRateVitalRecording = new ContentValues();
		heartRateVitalRecording.put("vital_id", lastId);
		heartRateVitalRecording.put("beats_per_minute", bpm);
		
		db.insert("heart_rate", null, heartRateVitalRecording);
		
		db.close();
	}
	
	public void recordSaLevel(Double saLevel, String essn, String pssn) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(new Date());

		ContentValues vitalRecording = new ContentValues();
		vitalRecording.put("vital_name", "Sa Level");
		vitalRecording.put("pssn", pssn);
		vitalRecording.put("essn", essn);
		vitalRecording.put("date", strDate);
		
		db.insert("vital_data_point", null, vitalRecording);
		
		Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
		
		Integer lastId = 0;
		
		if (cursor != null) {
			cursor.moveToNext();
			lastId = cursor.getInt(0);
		}
		
		cursor.close();
				
		ContentValues saVitalRecording = new ContentValues();
		saVitalRecording.put("vital_id", lastId);
		saVitalRecording.put("beats_per_minute", saLevel);
		
		db.insert("sa_level", null, saVitalRecording);
		
		db.close();
	}
	
	public void recordTemperature(Integer temperature, String essn, String pssn) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(new Date());

		ContentValues vitalRecording = new ContentValues();
		vitalRecording.put("vital_name", "Temperature");
		vitalRecording.put("pssn", pssn);
		vitalRecording.put("essn", essn);
		vitalRecording.put("date", strDate);
		
		db.insert("vital_data_point", null, vitalRecording);
		
		Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
		
		Integer lastId = 0;
		
		if (cursor != null) {
			cursor.moveToNext();
			lastId = cursor.getInt(0);
		}
		
		cursor.close();
				
		ContentValues temperatureVitalRecording = new ContentValues();
		temperatureVitalRecording.put("vital_id", lastId);
		temperatureVitalRecording.put("value", temperature);
		
		db.insert("sa_level", null, temperatureVitalRecording);
		
		db.close();
	}
	
	private void setupSampleEmployees(SQLiteDatabase db) {		
		ContentValues bob = new ContentValues();
		bob.put("essn", "123-45-6789");
		bob.put("first", "Bob");
		bob.put("last", "Sanders");
		bob.put("username", "Bs1");
		bob.put("password", "doge");
		
		ContentValues rick = new ContentValues();
		rick.put("essn", "842-41-2523");
		rick.put("first", "Rick");
		rick.put("last", "Brown");
		rick.put("username", "Rbrown");
		rick.put("password", "mayo");
		
		ContentValues a = new ContentValues();
		a.put("essn", "832-23-5531");
		a.put("first", "Dr.");
		a.put("last", "Evil");
		a.put("username", "a");
		a.put("password", "a");
		
		db.insert("employee", null, bob);
		db.insert("employee", null, rick);
		db.insert("employee", null, a);
	}
	
	private void setupSamplePatients(SQLiteDatabase db) {
		ContentValues sara = new ContentValues();
		sara.put("pssn", "492-15-2132");
		sara.put("first", "Sara");
		sara.put("last", "North");
		
		ContentValues saraAssign = new ContentValues();
		saraAssign.put("pssn", "492-15-2132");
		saraAssign.put("essn", "842-41-2523");
		saraAssign.put("floor_nurse_essn", "123-45-6789");
		
		ContentValues jeff = new ContentValues();
		jeff.put("pssn", "940-42-1294");
		jeff.put("first", "Jeff");
		jeff.put("last", "Tan");
		
		ContentValues jeffAssign = new ContentValues();
		jeffAssign.put("pssn", "940-42-1294");
		jeffAssign.put("essn", "842-41-2523");
		jeffAssign.put("floor_nurse_essn", "123-45-6789");
	
		ContentValues tom = new ContentValues();
		tom.put("pssn", "720-43-1214");
		tom.put("first", "Tom");
		tom.put("last", "Booker");;
		
		ContentValues tomAssign = new ContentValues();
		tomAssign.put("pssn", "720-43-1214");
		tomAssign.put("essn", "123-45-6789");
		tomAssign.put("floor_nurse_essn", "123-45-6789");

		ContentValues cory = new ContentValues();
		cory.put("pssn", "540-12-1294");
		cory.put("first", "Cory");
		cory.put("last", "Green");
		
		ContentValues coryAssign = new ContentValues();
		coryAssign.put("pssn", "540-12-1294");
		coryAssign.put("essn", "123-45-6789");
		coryAssign.put("floor_nurse_essn", "123-45-6789");
		
		db.insert("patient", null, sara);
		db.insert("patient", null, jeff);
		db.insert("patient", null, tom);
		db.insert("patient", null, cory);
		
		db.insert("assignment", null, saraAssign);
		db.insert("assignment", null, jeffAssign);
		db.insert("assignment", null, tomAssign);
		db.insert("assignment", null, coryAssign);
	}
	
}
