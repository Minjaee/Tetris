package src.kr.ac.jbnu.se.tetris;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseUtil {

    private static Firestore db;

    public static void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("tetris-8565c-firebase-adminsdk-ip0mc-025475196a.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Firestore getDb() {
        return db;
    }

    public static void addUser(User user) throws ExecutionException, InterruptedException {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("password", user.getPassword());
        userMap.put("score", user.getScore());


        db.collection("users").document(user.getId()).set(userMap).get();
    }
    public static void addScore(String userId, int newScore) throws ExecutionException, InterruptedException {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("score", newScore);

        db.collection("users").document(userId).update(updateData).get();
    }

    public static String validateUserPassword(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = db.collection("users").document(id).get().get();
        if(documentSnapshot.exists()){
            return documentSnapshot.getString("password");
        }
        else{
            return null;
        }
    }
    public static String validateUserId(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = db.collection("users").document(id).get().get();
        if(documentSnapshot.exists()){
            return documentSnapshot.getString("id");
        }
        else{
            return null;
        }
    }
    public static int getUserScore(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = db.collection("users").document(id).get().get();
        if(documentSnapshot.exists()){
            Integer score = documentSnapshot.getLong("score").intValue();
            return score != null ? score : 0;
        } else {
            return 0;
        }
    }
}
