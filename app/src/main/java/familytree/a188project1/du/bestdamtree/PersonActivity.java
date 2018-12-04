//Tess Julien
//source: https://youtu.be/fn5OlqQuOCk

package familytree.a188project1.du.bestdamtree;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;

public class PersonActivity extends AppCompatActivity{

    private Person person;
    private Tree family;
    private ImageView imageView;
    private TextView nameView;
    private TextView birthdayView;
    private TextView cityView;
    private TextView jobView;
    private TextView employerView;
    private TextView interestsView;
    private Button editButton;
    private Button newPersonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person);

        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        refresh();
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    private void refresh(){
        imageView = (ImageView) findViewById(R.id.image_view);
        nameView = (TextView) findViewById(R.id.name_view);
        birthdayView = (TextView) findViewById(R.id.birthday_view);
        cityView = (TextView) findViewById(R.id.city_view);
        jobView = (TextView) findViewById(R.id.job_view);
        employerView = (TextView) findViewById(R.id.employer_view);
        interestsView = (TextView) findViewById(R.id.interests_view);

        Realm realm = Realm.getDefaultInstance();
        String personID = (String) getIntent().getStringExtra("person");
        String familyTree = (String) getIntent().getStringExtra("family");
        person = realm.where(Person.class).equalTo("RealmID", personID).findFirst();
        family = realm.where(Tree.class).equalTo("name", familyTree).findFirst();

        //ADD IF STATEMENTS
        //nameView.setText(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName() + " " + person.getOptionalSuffix());
        if(person.getImage()!=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(person.getImage(), 0, person.getImage().length);
            imageView.setImageBitmap(bmp);
        }
        nameView.setText(person.getFirstName() + " " + person.getLastName());
        birthdayView.setText(person.getBirthday());
        cityView.setText(person.getCity());
        jobView.setText(person.getJob());
        employerView.setText(person.getEmployer());
        interestsView.setText(person.getInterests());

        newPersonButton = (Button) findViewById(R.id.add_connection);
        newPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewPersonActivity.class);
                intent.putExtra("person",person.getRealmID());
                intent.putExtra("family", family.getName());
                startActivity(intent);
            }
        });

        editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditPersonActivity.class);
                intent.putExtra("person", person.getRealmID());
                startActivity(intent);
            }
        });
    }
}
