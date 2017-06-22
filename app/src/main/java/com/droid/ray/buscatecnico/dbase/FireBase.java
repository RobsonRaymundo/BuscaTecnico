package com.droid.ray.buscatecnico.dbase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by Robson on 19/06/2017.
 */

public class FireBase {

    private static DatabaseReference databaseReferenceUsers;
    private static DatabaseReference databaseReferencePedidos;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFireBaseUsers()
    {
        if (databaseReferenceUsers == null)
        {
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            databaseReferenceUsers = FirebaseDatabase.getInstance().getReference("Usuarios/");
         //   databaseReference.keepSynced(true);

        }
        return databaseReferenceUsers;
    }

    public static DatabaseReference getFireBasePedido()
    {
        if (databaseReferencePedidos == null)
        {
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            databaseReferencePedidos = FirebaseDatabase.getInstance().getReference("Pedidos/");
            //   databaseReference.keepSynced(true);
        }
        return databaseReferencePedidos;
    }

    public static FirebaseAuth getFirebaseAuth()
    {
        if (firebaseAuth == null)
        {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

}
