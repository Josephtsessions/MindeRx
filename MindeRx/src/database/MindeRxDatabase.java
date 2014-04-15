	package database;

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
			+ "username TEXT"
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
			+ "diastolic INTEGER"
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
		
		
		ContentValues bob = new ContentValues();
		bob.put("essn", "123-45-6789");
		bob.put("first", "Bob");
		bob.put("last", "Sanders");
		bob.put("username", "bs194");
		bob.put("password", "RXDOGE");
		
		ContentValues rick = new ContentValues();
		rick.put("essn", "842-41-2523");
		rick.put("first", "Rick");
		rick.put("last", "Brown");
		rick.put("username", "rbrown");
		rick.put("password", "mayo");
		
		db.insert("employee", null, bob);
		db.insert("employee", null, rick);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {
		
	}
	
	public String getPasswordFromUsername(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {"password"};
		Cursor cursor = db.query("EMPLOYEE", columns, "'" + username + "' = username", null, null, null, null);
		
		String password = cursor.getString(0);
		cursor.close();
		
		System.out.println("Got pwd - it was: " + password);
		
		return password;
		
	}
	
}
