package pe.farmaciasperuanas.legall.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FirebaseConfig {

    Firestore firestoreConnection;

    public FirebaseConfig() throws IOException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().
                        getResource("firebase-sdk.json")).getFile());
        FileInputStream fis = new FileInputStream(file);
        GoogleCredentials credentials = GoogleCredentials.fromStream(fis);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials).build();
        FirebaseApp.initializeApp(options);
        firestoreConnection = FirestoreClient.getFirestore();
    }

    public Firestore getFirestoreConnection() {
        return firestoreConnection;
    }

    public void closeConection(){
        FirebaseApp.getInstance().delete();
    }
}
