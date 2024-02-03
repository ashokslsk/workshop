# Program 1 MediaPlayer

Creating a complete Android application involves multiple files and components. Below is a simple example using Java to create an Android app that plays MP3 from a URL, App resource, and the SD card. This example uses the `MediaPlayer` class for audio playback.

1. Create a new Android project in Android Studio.

2. Add the required permissions to the `AndroidManifest.xml` file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

3. Create the layout file (`activity_main.xml`) with buttons for each source:

```xml
<!-- activity_main.xml -->
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/btnPlayUrl"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Play from URL"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="50dp"/>

    <Button
        android:id="@+id/btnPlayResource"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Play from Resource"
        android:layout_below="@id/btnPlayUrl"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp"/>

    <Button
        android:id="@+id/btnPlaySDCard"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Play from SD Card"
        android:layout_below="@id/btnPlayResource"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp"/>
</RelativeLayout>
```

4. Create the `MainActivity.java` file:

```java
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();

        Button btnPlayUrl = findViewById(R.id.btnPlayUrl);
        btnPlayUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromUrl("YOUR_MP3_URL_HERE");
            }
        });

        Button btnPlayResource = findViewById(R.id.btnPlayResource);
        btnPlayResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromResource();
            }
        });

        Button btnPlaySDCard = findViewById(R.id.btnPlaySDCard);
        btnPlaySDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioFromSDCard();
            }
        });
    }

    private void playAudioFromUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playAudioFromResource() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, R.raw.sample); // Assumes 'sample.mp3' is in the 'res/raw/' folder
        mediaPlayer.start();
    }

    private void playAudioFromSDCard() {
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/sample.mp3";
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
```

Replace `"YOUR_MP3_URL_HERE"` with the actual URL of the MP3 file you want to play. Also, make sure to have an MP3 file named `sample.mp3` in the `res/raw/` folder for the resource playback example.

Note: This is a simple example, and in a real-world scenario, you may want to add error handling, user feedback, and other features to enhance the application. Additionally, handling network operations and file access on the main thread can cause performance issues; consider using background threads or AsyncTask for such operations.

# Program 2 : Content Provider
A Content Provider in Android is used to manage access to a structured set of data. It acts as an abstraction layer that allows data to be shared between different applications securely. Content Providers are commonly used for sharing data between applications and providing a consistent interface to data sources, such as databases or files.

Here is a simple example of a Java Content Provider along with a use case:

### ContentProviderExample.java:
* * *
* * *

```java
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class SimpleContentProvider extends ContentProvider {

    // Define the authority for the content provider
    public static final String AUTHORITY = "com.example.simplecontentprovider";

    // URI matcher code for the content URI
    private static final int DATA = 1;

    @Override
    public boolean onCreate() {
        // Initialization code, if needed
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Implement your query logic here
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Implement your insert logic here
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // Implement your update logic here
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement your delete logic here
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // Return the MIME type associated with the URI
        return null;
    }
}
```

### Explanation:

- **onCreate():** This method is called when the content provider is created. You can perform initialization tasks here.

- **query():** This method is called when a query is requested. You should implement the logic to retrieve data based on the provided parameters.

- **insert():** This method is called when data insertion is requested. Implement the logic to insert data into your data source.

- **update():** This method is called when data update is requested. Implement the logic to update existing data.

- **delete():** This method is called when data deletion is requested. Implement the logic to delete data from your data source.

- **getType():** This method should return the MIME type of the data associated with the URI.

### Use Case:

Let's say we want to use this Content Provider to store and retrieve a simple list of names.

1. Define a contract class (`SimpleContract.java`) that specifies the URI and column names:

```java
import android.net.Uri;
import android.provider.BaseColumns;

public class SimpleContract {

    // The authority for the content provider
    public static final String AUTHORITY = "com.example.simplecontentprovider";

    // The base content URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the path for the "names" directory
    public static final String PATH_NAMES = "names";

    // Inner class that defines the table contents
    public static class NameEntry implements BaseColumns {

        // The content URI for accessing the "names" table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NAMES).build();

        // Column names
        public static final String COLUMN_NAME = "name";
    }
}
```

2. Use the Content Provider in an activity (`MainActivity.java`) to insert and retrieve data:

```java
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Insert data
        ContentValues values = new ContentValues();
        values.put(SimpleContract.NameEntry.COLUMN_NAME, "John Doe");

        Uri insertUri = getContentResolver().insert(SimpleContract.NameEntry.CONTENT_URI, values);

        // Query data
        String[] projection = {SimpleContract.NameEntry.COLUMN_NAME};
        Cursor cursor = getContentResolver().query(
                SimpleContract.NameEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(SimpleContract.NameEntry.COLUMN_NAME));
                // Do something with the retrieved data
            }
            cursor.close();
        }
    }
}
```

### Dependencies:

In this example, we don't have external dependencies. However, in a real-world scenario, if you are using a database or other specific features, you may need to include relevant dependencies in your `build.gradle` file.

For example, if you are using a SQLite database, you might add:

```gradle
implementation 'androidx.sqlite:sqlite:2.1.0'
implementation 'androidx.sqlite:sqlite-framework:2.1.0'
```

Ensure you also have the necessary AndroidX dependencies if you're using AndroidX in your project.

Remember that this is a simplified example, and in a real-world scenario, you would need to handle exceptions, perform proper error checking, and manage your database or data source more effectively.


# Program 3: SharedPreferences
Below is an example of a simple Android application written in Java that saves text from an `EditText` to SharedPreferences. This example includes both the UI (layout file) and the Java code.

### `activity_main.xml` (layout file):

```xml
<!-- res/layout/activity_main.xml -->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/editText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter text"/>

    <Button
        android:id="@+id/saveButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="Save"/>

    <TextView
        android:id="@+id/displayText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text=""/>

</LinearLayout>
```

### `MainActivity.java`:

```java
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String TEXT_KEY = "savedText";

    private EditText editText;
    private TextView displayText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editText = findViewById(R.id.editText);
        displayText = findViewById(R.id.displayText);

        // Load SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedText = sharedPreferences.getString(TEXT_KEY, "");
        displayText.setText(savedText);

        // Save button click listener
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTextToSharedPreferences();
            }
        });
    }

    private void saveTextToSharedPreferences() {
        // Get text from EditText
        String inputText = editText.getText().toString();

        // Save text to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_KEY, inputText);
        editor.apply();

        // Display saved text
        displayText.setText(inputText);
    }
}
```

### Explanation:

- The `activity_main.xml` layout file defines a simple UI with an `EditText` for input, a `Button` to save, and a `TextView` to display the saved text.

- In the `MainActivity.java` file, `SharedPreferences` is used to save and retrieve the text entered in the `EditText`. The `saveTextToSharedPreferences` method is called when the "Save" button is clicked.

- The `PREFS_NAME` constant is used as the name for the SharedPreferences file, and `TEXT_KEY` is used as the key for storing and retrieving the text.

- The saved text is displayed in the `TextView` on both app creation and after saving.

### Note:

This is a basic example, and you may want to add additional features, error handling, and enhancements based on your specific requirements. Also, remember to handle security and privacy considerations appropriately when saving data using SharedPreferences.


# Program 4: Content Provider

Sure, let's create a simple Android app with a ContentProvider to save and retrieve a string. ContentProvider is typically used to share data between different apps or different parts of the same app. In this example, we'll use it for simplicity within the same app.

### MainActivity.java:

```java
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView displayText;

    // ContentProvider URI
    private static final Uri CONTENT_URI = Uri.parse("content://com.example.mycontentprovider/mydata");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        displayText = findViewById(R.id.displayText);

        Button saveButton = findViewById(R.id.saveButton);
        Button retrieveButton = findViewById(R.id.retrieveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(inputText.getText().toString());
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }

    private void saveData(String data) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("data", data);
        contentResolver.insert(CONTENT_URI, values);
    }

    private void retrieveData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex("data"));
            displayText.setText(data);
            cursor.close();
        } else {
            displayText.setText("No data found");
        }
    }
}
```

### MyContentProvider.java:

```java
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.mycontentprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/mydata");

    private static final int DATA = 1;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "mydata", DATA);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "data"});
        // In a real-world scenario, you might fetch data from a database here.
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == DATA) {
            // In a real-world scenario, you might insert data into a database here.
            return CONTENT_URI;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
```

### AndroidManifest.xml:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.contentproviderexample">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.ContentProviderExample">

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <provider
            android:name=".MyContentProvider"
            android:authorities="com.example.mycontentprovider"
            android:exported="false" />
    </application>

</manifest>
```

### res/layout/activity_main.xml:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/inputText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter text"/>

    <Button
        android:id="@+id/saveButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Save"
        android:layout_below="@id/inputText"
        android:layout_marginTop="20dp"/>

    <Button
        android:id="@+id/retrieveButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Retrieve"
        android:layout_below="@id/saveButton"
        android:layout_marginTop="20dp"/>

    <TextView
        android:id="@+id/displayText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text=""
        android:layout_below="@id/retrieveButton"
        android:layout_marginTop="20dp"/>
</RelativeLayout>
```

In the above example, a simple ContentProvider (`MyContentProvider`) is created, which has basic implementations for the necessary methods. The `MainActivity` uses this ContentProvider to save and retrieve a string.

- `query()`: Retrieves data based on the provided parameters.
- `getType()`: Returns the MIME type of the data associated with the given URI.
- `insert()`: Adds a new row of data to the provider.
- `delete()`: Deletes data based on the provided parameters.
- `update()`: Updates existing data based on the provided parameters.

ContentProviders are useful in scenarios where you want to share data between different components of an app, between different apps, or even expose data to other apps. They offer a standardized way to interact with data, making it easy to switch underlying data storage mechanisms without changing the code that uses the data.

# Program 5 : Firebase RDB 
Certainly! Below is a simple Android app example that uses Firebase Realtime Database to save and retrieve notes. Please follow the steps to set up Firebase in your Android app:

### Step 1: Set up Firebase Project

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Click on "Add Project" and follow the instructions to create a new Firebase project.
3. After creating the project, click on "Add app" to add an Android app to your project.
4. Follow the setup instructions, including downloading the `google-services.json` file.
5. Place the `google-services.json` file in the `app` directory of your Android project.

### Step 2: Configure Dependencies

In your app's `build.gradle` file, add the following dependencies:

```gradle
implementation 'com.google.firebase:firebase-database:22.0.0'
implementation 'com.google.firebase:firebase-auth:22.0.0'
```

Make sure to replace the version numbers with the latest version available.

### Step 3: Initialize Firebase in your Application

In your `Application` class or the `onCreate` method of your main `Activity`, initialize Firebase:

```java
// Add this at the top of your file
import com.google.firebase.FirebaseApp;

// ...

// Add this in your onCreate method
FirebaseApp.initializeApp(this);
```

### Step 4: Create a Note class

Create a simple Java class for representing a note. In this example, let's call it `Note`:

```java
public class Note {
    public String title;
    public String content;

    public Note() {
        // Default constructor required for Firebase
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
```

### Step 5: Create XML Layout

Create the layout for your main activity (`res/layout/activity_main.xml`):

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/titleEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Title"/>

    <EditText
        android:id="@+id/contentEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/titleEditText"
        android:layout_marginTop="10dp"
        android:hint="Content"/>

    <Button
        android:id="@+id/saveButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Save"
        android:layout_below="@id/contentEditText"
        android:layout_marginTop="20dp"/>
</RelativeLayout>
```

### Step 6: MainActivity.java

Now, let's create the `MainActivity` to handle Firebase operations:

```java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Use the "notes" child as the reference
        databaseReference = database.getReference("notes");

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            // Create a unique key for the note
            String noteId = databaseReference.push().getKey();

            // Create a Note object
            Note note = new Note(title, content);

            // Save the note to Firebase Realtime Database
            databaseReference.child(noteId).setValue(note);

            // Clear the input fields
            titleEditText.setText("");
            contentEditText.setText("");

            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
```

### Step 7: Rules in Firebase Console

In the Firebase Console, go to the "Database" section and make sure your rules allow read and write access:

```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

These rules allow read and write access only to authenticated users. In a production app, you would implement proper authentication.

Now, you have a simple Android app that uses Firebase Realtime Database to save and retrieve notes. Make sure to handle authentication and security appropriately in a production environment.

Let's modify the `MainActivity` to fetch the notes from Firebase Realtime Database and display them in a `TextView`.

### Updated MainActivity.java

```java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private TextView notesTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        notesTextView = findViewById(R.id.notesTextView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Use the "notes" child as the reference
        databaseReference = database.getReference("notes");

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        Button fetchButton = findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNotes();
            }
        });
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            // Create a unique key for the note
            String noteId = databaseReference.push().getKey();

            // Create a Note object
            Note note = new Note(title, content);

            // Save the note to Firebase Realtime Database
            databaseReference.child(noteId).setValue(note);

            // Clear the input fields
            titleEditText.setText("");
            contentEditText.setText("");

            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchNotes() {
        // Read data from Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear previous notes
                notesTextView.setText("");

                // Iterate through all notes in the database
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve each note and append to the TextView
                    Note note = noteSnapshot.getValue(Note.class);
                    if (note != null) {
                        String noteText = "Title: " + note.title + "\nContent: " + note.content + "\n\n";
                        notesTextView.append(noteText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error fetching notes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

### Updated activity_main.xml

Add a new `Button` and a `TextView` to display the notes:

```xml
<Button
    android:id="@+id/fetchButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Fetch Notes"
    android:layout_below="@id/contentEditText"
    android:layout_marginTop="20dp"/>

<TextView
    android:id="@+id/notesTextView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_below="@id/fetchButton"
    android:layout_marginTop="20dp"/>
```

With these changes, clicking the "Fetch Notes" button will retrieve the notes from Firebase Realtime Database and display them in the `TextView`. Note that this is a simple example, and in a production app, you may want to handle UI updates more efficiently and consider implementing proper authentication and security rules.
