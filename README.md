# Hotel-sApp-Android

RecyclerView: 

To create a RecyclerView in Android Studio, follow these steps:

1. Create an Empty Activity as the template.
2. Add the RecyclerView dependency to your app module's build.gradle file.

     dependencies {
           implementation 'androidx.recyclerview:recyclerview:1.2.1'
     }

3. Design the layout for the RecyclerView item. Create a new layout file for the item and design it as per your requirements. You can add any number of views to the item layout.

4. Create the RecyclerView adapter. In the adapter class, define a ViewHolder that holds a reference to the views in the item layout, and implements the RecyclerView.Adapter and RecyclerView.ViewHolder interfaces. Override the onCreateViewHolder(), onBindViewHolder(), and getItemCount() methods to inflate the item layout, bind the data to the views, and return the item count, respectively.
      
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

         private final ArrayList<HotelData.regionNames> hotelList;


        public RecyclerAdapter(ArrayList<HotelData.regionNames> hotelList) {
              this.hotelList = hotelList;
        
        }

       @NonNull
       @Override
       public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            @SuppressLint("InflateParams") View inflate = layoutInflater.inflate(R.layout.hotel_lists, null);
            return new ViewHolder(inflate);
       }

      @SuppressLint("SetTextI18n")
      @Override
      public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         HotelData.regionNames hotelData = hotelList.get(position);

        holder.fullName.setText("Full Name : " + hotelData.fullName);
        holder.shortName.setText("Short Name : "+ hotelData.shortName
      }

     @Override
     public int getItemCount() {
        return hotelList.size();
     }

       public static class  ViewHolder extends RecyclerView.ViewHolder{

        TextView fullName, shortName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.full_name);
            shortName = itemView.findViewById(R.id.short_name);
            

        }
     }

   }
 
 5. Initialize the RecyclerView in the main activity. In the onCreate() method of MainActivity.java, initialize the RecyclerView, set its layout manager, and set the adapter to the RecyclerView.
     
     
        for (HotelData.sr data1 : data) {
              hotelData.add(data1.regionNames);
        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(hotelData);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TableView.this));
        
     
  API Fetch Using Retrofit : 
  
 1. Create an Empty Activity as the template.
 2. Add the Retrofit and Gson dependencies to your app module's build.gradle file.
 
      dependencies {
         implementation 'com.squareup.retrofit2:retrofit:2.9.0'
         implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
      }
      
3. Create a Java class that represents the hotel data model. This class should have fields that match the hotel data in the API response.

   
        public class HotelData {

            String q;
            String rid;
            ArrayList<sr> sr;
            
            // Getter and setter methods for the fields
            
            public class sr {
            
               regionNames regionNames;
               
                // Getter and setter methods for the fields
                
            }
            
            public class regionNames {
            
                String fullName;
                String shortName;
                String displayName;
                String primaryDisplayName;
                String secondaryDisplayName;
                String lastSearchName;
                
                // Getter and setter methods for the fields
                
           }     
            
       }
       
  4. Create a Retrofit interface that defines the API endpoint and the HTTP request method to fetch the hotel data. In the interface, define a method that returns a Call object that wraps the API response.
  
   
   
        public interface myAPI {
           @GET("locations/v3/search")
           Call<HotelData> getAllData(
                @Query("q") String query,
                @Query("locale") String locale,
                @Query("langid") int langid,
                @Query("siteid") String siteid,
                @Header("x-rapidapi-host") String host,
                @Header("x-rapidapi-key") String apiKey
           );

        }
        
   5. Create a Retrofit instance in the main activity. In the onCreate() method of MainActivity.java, create a Retrofit instance and specify the API base URL. Then, create an instance of the HotelApi interface using the Retrofit instance.
   
   
          public class RetrofitClient {

                private static Retrofit retrofit;
                private static String BASE_URL = "https://hotels4.p.rapidapi.com/";

                public static Retrofit getRetrofitInstance() {
                    if(retrofit == null) {
                        retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    }
                    return retrofit;
                }

          }
          
  6. Fetch the hotel data from the API using Retrofit. In the main activity, call the getHotels() method on the HotelApi instance to fetch the hotel data. Then, enqueue the API call to run asynchronously and handle the response in a callback.
  
  
            myAPI API = RetrofitClient.getRetrofitInstance().create(myAPI.class);



            Call<HotelData> call = API.getAllData(loc,
                    "en_US",
                    1033,
                    "300000001",
                    "hotels4.p.rapidapi.com",
                    "b7f0c098d1msh8000a209160b699p160cb6jsnbff91d2e5955");

            call.enqueue(new Callback<HotelData>() {
                @Override
                public void onResponse(@NonNull Call<HotelData> call, @NonNull Response<HotelData> response) {

                    if(response.isSuccessful()) {
                        assert response.body() != null;
                        ArrayList<HotelData.sr> data = response.body().getSr();

                     
                        for (HotelData.sr data1 : data) {
                            hotelData.add(data1.regionNames);
             
                        }

                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(hotelData);

                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(TableView.this));
                    }
                    else {
                        setToastCustom(false, "Location doesn't Exists");
                    }

                    loadingAlert.closeAlertDialog();
                    location.setText("");

                }

                @Override
                public void onFailure(@NonNull Call<HotelData> call, @NonNull Throwable t) {
                    loadingAlert.closeAlertDialog();
                    setToastCustom(false, t.getMessage());
                }
            });
        });
        
       
  Authentication through Firebase :
    
    
Step 1: Create a Firebase project
   To create a Firebase project, follow these steps:

      1. Go to the Firebase Console.
      2. Click Create Project and follow the instructions to create a new project.
      3. Once your project is created, click Add Firebase to your Android app.
      4. Follow the instructions to add your Android app to the Firebase project.
  
Step 2: Add the Firebase SDK to your Android Studio project
    To add the Firebase SDK to your Android Studio project, follow these steps:

      1. Open your Android Studio project.
      2. In the Project view, select the module you want to add Firebase to.
      3. Click Tools > Firebase to open the Firebase Assistant.
      4. In the Firebase Assistant, click Authentication and follow the instructions to add Firebase Authentication to your app. 
       
 Step 3: Implement Firebase Authentication in your app
     To implement Firebase Authentication in your app, follow these steps:   
        
        1. Create a new Firebase Authentication instance:
         
             FirebaseAuth auth = FirebaseAuth.getInstance();
             
        2. Use the Firebase Authentication instance to sign in, sign up, or sign out users.
           For example, to sign in a user with email and password, use the following code:     
           
           
              auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // User signed in successfully
                            } else {
                                // Sign in failed
                            }
                        }
               });

         3. SignUp : 
          
                  auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Signup successful
                                        // You can now retrieve the user's information using mAuth.getCurrentUser()
                                    } else {
                                        // Signup failed
                                        Toast.makeText(getApplicationContext(), "Signup failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                   });


          4. SignOut: 
               
            
               auth.signOut();
       
 

               
               
