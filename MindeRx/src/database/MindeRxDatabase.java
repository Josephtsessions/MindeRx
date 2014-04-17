	package database;

import java.util.ArrayList;

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
			+ "last TEXT,"
			+ "essn TEXT,"
			+ "floor_nurse_essn TEXT,"
			+ "FOREIGN KEY(essn) REFERENCES employee(essn),"
			+ "FOREIGN KEY(floor_nurse_essn) REFERENCES employee(essn))";
	
	private static final String CREATE_TABLE_VITAL_DATA_POINTS = "CREATE TABLE "
			+ "vital_data_point("
			+ "vital_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "vital_name TEXT,"
			+ "pssn TEXT,"
			+ "essn TEXT,"
			+ "date DATETIME,"
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
		
		return essn;
	}
	
	public ArrayList<String> getPssnsFromEssn(String essn) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"pssn"};
		Cursor cursor = db.query("PATIENT", columns, "'" + essn + "' = essn", null, null, null, null);

		ArrayList<String> pssns = new ArrayList<String>();
		
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String pssn = cursor.getString(0);
			
				pssns.add(pssn);
			}
		
			cursor.close();
		}
		
		return pssns;
	}
	
	public ArrayList<String> getPatientsFromEssn(String essn) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"first", "last"};
		Cursor cursor = db.query("PATIENT", columns, "'" + essn + "' = essn", null, null, null, null);

		ArrayList<String> names = new ArrayList<String>();
		
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String firstName = cursor.getString(0);
				String lastName = cursor.getString(1);
			
				String name = firstName + " " + lastName;
			
				names.add(name);
			}
		
			cursor.close();
		}
			
		return names;		
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
		
		db.insert("employee", null, bob);
		db.insert("employee", null, rick);
	}
	
	private void setupSamplePatients(SQLiteDatabase db) {
		ContentValues sara = new ContentValues();
		sara.put("pssn", "492-15-2132");
		sara.put("first", "Sara");
		sara.put("last", "North");
		sara.put("essn", "842-41-2522");
		sara.put("floor_nurse_essn", "123-45-6789");
		
		ContentValues jeff = new ContentValues();
		jeff.put("pssn", "940-42-1294");
		jeff.put("first", "Jeff");
		jeff.put("last", "Tan");
		jeff.put("essn",  "842-41-2523"); // Rick
		jeff.put("floor_nurse_essn",  "123-45-6789");
	
		ContentValues tom = new ContentValues();
		tom.put("pssn", "720-43-1214");
		tom.put("first", "Tom");
		tom.put("last", "Booker");
		tom.put("essn",  "842-41-2523"); // Rick
		tom.put("floor_nurse_essn",  "123-45-6789");

		ContentValues cory = new ContentValues();
		cory.put("pssn", "540-12-1294");
		cory.put("first", "Cory");
		cory.put("last", "Green");
		cory.put("essn",  "123-45-6789"); // Bob
		cory.put("floor_nurse_essn",  "123-45-6789");
		
		db.insert("patient", null, sara);
		db.insert("patient", null, jeff);
		db.insert("patient", null, tom);
		db.insert("patient", null, cory);
	}
	
}
